package com.example.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.*;
import com.example.web.dto.query.*;
import com.example.web.entity.*;
import com.example.web.enums.EmergencyRankEnum;
import com.example.web.enums.StockInStatusEnum;
import com.example.web.enums.StockInTypeEnum;
import com.example.web.mapper.*;
import com.example.web.service.*;
import com.example.web.tools.dto.*;
import com.example.web.tools.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lombok.SneakyThrows;

import com.example.web.tools.*;

/**
 * 入库单功能实现类
 */
@Service
public class StockInServiceImpl extends ServiceImpl<StockInMapper, StockIn> implements StockInService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的StockIn表mapper对象
     */
    @Autowired
    private StockInMapper StockInMapper;
    @Autowired
    private WarehouseMapper WarehouseMapper;
    @Autowired
    private CustomerMapper CustomerMapper;

    @Autowired
    private StockInDetMapper StockInDetMapper;


    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<StockIn> BuilderQuery(StockInPagedInput input) {
        //声明一个支持入库单查询的(拉姆达)表达式
        LambdaQueryWrapper<StockIn> queryWrapper = Wrappers.<StockIn>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, StockIn::getId, input.getId())
                .eq(input.getCreatorId() != null, StockIn::getCreatorId, input.getCreatorId());
        //如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getNo())) {
            queryWrapper = queryWrapper.like(StockIn::getNo, input.getNo());
        }
        if (Extension.isNotNullOrEmpty(input.getRemark())) {
            queryWrapper = queryWrapper.like(StockIn::getRemark, input.getRemark());
        }

        if (input.getWarehouseId() != null) {
            queryWrapper = queryWrapper.eq(StockIn::getWarehouseId, input.getWarehouseId());
        }

        if (input.getCustomerId() != null) {
            queryWrapper = queryWrapper.eq(StockIn::getCustomerId, input.getCustomerId());
        }

        if (input.getStockInType() != null) {
            queryWrapper = queryWrapper.eq(StockIn::getStockInType, input.getStockInType());
        }

        if (input.getStockInStatus() != null) {
            queryWrapper = queryWrapper.eq(StockIn::getStockInStatus, input.getStockInStatus());
        }
        if (input.getInTimeRange() != null && !input.getInTimeRange().isEmpty()) {
            queryWrapper = queryWrapper.lt(StockIn::getInTime, input.getInTimeRange().get(1));
            queryWrapper = queryWrapper.gt(StockIn::getInTime, input.getInTimeRange().get(0));
        }
        if (input.getPlanInTimeRange() != null && !input.getPlanInTimeRange().isEmpty()) {
            queryWrapper = queryWrapper.lt(StockIn::getPlanInTime, input.getPlanInTimeRange().get(1));
            queryWrapper = queryWrapper.gt(StockIn::getPlanInTime, input.getPlanInTimeRange().get(0));
        }
        if (input.getIsNotComplted() != null && input.getIsNotComplted() == true) {
            queryWrapper = queryWrapper.notIn(StockIn::getStockInStatus, StockInStatusEnum.入库完成.index())
                    .le(StockIn::getPlanInTime, LocalDateTime.now().plusDays(7));
        }
        return queryWrapper;
    }

    /**
     * 处理入库单对于的外键数据
     */
    private StockInDto DispatchItem(StockInDto item) throws InvocationTargetException, IllegalAccessException {

        //查询出关联的Warehouse表信息
        Warehouse WarehouseEntity = WarehouseMapper.selectById(item.getWarehouseId());
        item.setWarehouseDto(WarehouseEntity.MapToDto());


        //查询出关联的Customer表信息
        Customer CustomerEntity = CustomerMapper.selectById(item.getCustomerId());
        item.setCustomerDto(CustomerEntity.MapToDto());

        //计算计划时间和当前时间的总分钟
        long seconds = Duration.between(LocalDateTime.now(), item.getPlanInTime()).toSeconds();
        double hour = (double) seconds / 3600;
        if (hour >= 5 * 24) {
            item.setEmergencyRank(EmergencyRankEnum.正常.index());
        } else if (hour > 3 * 24) {
            item.setEmergencyRank(EmergencyRankEnum.一般.index());
        } else if (hour > 2 * 24) {
            item.setEmergencyRank(EmergencyRankEnum.警告.index());
        } else {
            item.setEmergencyRank(EmergencyRankEnum.紧急.index());
        }
        item.setRemindSecond((int) seconds);

        return item;
    }

    /**
     * 入库单分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<StockInDto> List(StockInPagedInput input) {
        //构建where条件+排序
        LambdaQueryWrapper<StockIn> queryWrapper = BuilderQuery(input);

        //按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(StockIn::getCreationTime);
        //构建一个分页查询的model
        Page<StockIn> page = new Page<>(input.getPage(), input.getLimit());
        //从数据库进行分页查询获取入库单数据
        IPage<StockIn> pageRecords = StockInMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount = StockInMapper.selectCount(queryWrapper);
        //把StockIn实体转换成StockIn传输模型
        List<StockInDto> items = Extension.copyBeanList(pageRecords.getRecords(), StockInDto.class);

        for (StockInDto item : items) {
            DispatchItem(item);
        }
        //返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个入库单查询
     */
    @SneakyThrows
    @Override
    public StockInDto Get(StockInPagedInput input) {
        if (input.getId() == null) {
            return new StockInDto();
        }
        StockIn entity = StockInMapper.selectById(input.getId());
        StockInDto dto = entity.MapToDto();
        DispatchItem(dto);
        return dto;
    }

    /**
     * 入库单创建或者修改
     */
    @SneakyThrows
    @Override
    public StockInDto CreateOrEdit(StockInDto input) {

        Long sameCount = StockInMapper.selectCount(Wrappers.<StockIn>lambdaQuery().ne(input.getId() != null, StockIn::getId, input.getId()).eq(StockIn::getWarehouseId, input.getWarehouseId()).eq(StockIn::getNo, input.getNo()));
        if (sameCount > 0) {
            throw new CustomException("当前仓库下的存在相同的入库单号了,请检查是否正确");
        }

        //声明一个入库单实体
        StockIn StockIn = input.MapToEntity();
        //调用数据库的增加或者修改方法
        saveOrUpdate(StockIn);
        //把传输模型返回给前端
        return StockIn.MapToDto();
    }


    /**
     * 入库单的完成
     */
    @SneakyThrows
    @Override
    public StockInDto Complete(StockInDto input) {

        //查询入库的明细
        List<StockInDet> stockInDetList = StockInDetMapper.selectList(Wrappers.<StockInDet>lambdaQuery().eq(StockInDet::getStockInId, input.getId()));
        if (stockInDetList.size() == 0) {
            throw new CustomException("入库单明细为空");
        }

        input.setStockInStatus(StockInStatusEnum.入库完成.index());
        input.setInTime(LocalDateTime.now());
        //声明一个入库单实体
        StockIn StockIn = input.MapToEntity();
        //调用数据库的增加或者修改方法
        saveOrUpdate(StockIn);
        //把传输模型返回给前端
        return StockIn.MapToDto();
    }

    /**
     * 入库单删除
     */
    @Override
    public void Delete(IdInput input) {
        StockIn entity = StockInMapper.selectById(input.getId());
        StockInMapper.deleteById(entity);
    }

    /**
     * 入库单批量删除
     */
    @Override
    public void BatchDelete(IdsInput input) {
        for (Integer id : input.getIds()) {
            IdInput idInput = new IdInput();
            idInput.setId(id);
            Delete(idInput);
        }
    }

    /**
     * 最近一年的入库单量
     */
    @SneakyThrows
    @Override
    public List<Object> GetStockInByDayEchart(StockInPagedInput input) {


        ArrayList<Object> objectArrayList = new ArrayList<>();
        List<StockIn> stockIns = StockInMapper.selectList(Wrappers.<StockIn>lambdaQuery().eq(StockIn::getWarehouseId, input.getWarehouseId()));

        for (int index = 0; index < 365; index++) {
            HashMap<String, Object> stringObjectHashMap = new HashMap<>();
            int day = index;
            LocalDateTime dateTime = LocalDate.now().atStartOfDay().minusDays(day);
            LocalDateTime dateTime2 = dateTime.plusDays(1);

            stringObjectHashMap.put("date", dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));


            List<StockIn> dayStockIns = stockIns.stream().filter(x -> x.getCreationTime().plusSeconds(1).isAfter(dateTime) && x.getCreationTime().plusSeconds(1).isBefore(dateTime2)).toList();

            //按入库状态统计
            long waitInStatusCount = dayStockIns.stream().filter(x -> x.getStockInStatus() == StockInStatusEnum.待入库.index()).count();
            stringObjectHashMap.put("waitInStatusCount", waitInStatusCount);

            long inStatusCount = dayStockIns.stream().filter(x -> x.getStockInStatus() == StockInStatusEnum.入库中.index()).count();
            stringObjectHashMap.put("inStatusCount", inStatusCount);

            long inCompleteStatusCount = dayStockIns.stream().filter(x -> x.getStockInStatus() == StockInStatusEnum.入库完成.index()).count();
            stringObjectHashMap.put("inCompleteStatusCount", inCompleteStatusCount);

            long cancelStatusCount = dayStockIns.stream().filter(x -> x.getStockInStatus() == StockInStatusEnum.取消.index()).count();
            stringObjectHashMap.put("cancelStatusCount", cancelStatusCount);


            objectArrayList.add(stringObjectHashMap);
        }

        return objectArrayList;
    }
    /**
     * 模拟数据
     */
    public void MockData(StockInPagedInput input){
        for (int index = 0; index < 30; index++) {
            HashMap<String, Object> stringObjectHashMap = new HashMap<>();
            int day = index;
            LocalDateTime dateTime = LocalDate.now().atStartOfDay().minusDays(day);

            List<Customer> customers = CustomerMapper.selectList(Wrappers.<Customer>lambdaQuery().eq(Customer::getWarehouseId, input.getWarehouseId()));
            for (Customer customer : customers) {


                StockIn stockIn = new StockIn();
                stockIn.setCreationTime(dateTime);
                stockIn.setStockInType(StockInTypeEnum.正常入库.index());
                stockIn.setStockInStatus(StockInStatusEnum.待入库.index());
                stockIn.setPlanInTime(LocalDateTime.now().plusDays(30));
                stockIn.setRemark("正常入库");
                stockIn.setCustomerId(customer.getId());
                stockIn.setWarehouseId(input.getWarehouseId());
                stockIn.setNo(Extension.GenerateOrderNumber());
                StockInMapper.insert(stockIn);
            }



        }


    }

}
