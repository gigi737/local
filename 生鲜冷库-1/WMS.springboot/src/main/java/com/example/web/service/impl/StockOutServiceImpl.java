package com.example.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.*;
import com.example.web.dto.query.*;
import com.example.web.entity.*;
import com.example.web.mapper.*;
import com.example.web.enums.*;
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
 * 出库单功能实现类
 */
@Service
public class StockOutServiceImpl extends ServiceImpl<StockOutMapper, StockOut> implements StockOutService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的StockOut表mapper对象
     */
    @Autowired
    private StockOutMapper StockOutMapper;
    @Autowired
    private WarehouseMapper WarehouseMapper;
    @Autowired
    private CustomerMapper CustomerMapper;

    @Autowired
    private StockOutDetMapper StockOutDetMapper;


    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<StockOut> BuilderQuery(StockOutPagedInput input) {
        //声明一个支持出库单查询的(拉姆达)表达式
        LambdaQueryWrapper<StockOut> queryWrapper = Wrappers.<StockOut>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, StockOut::getId, input.getId())
                .eq(input.getCreatorId() != null, StockOut::getCreatorId, input.getCreatorId());
        //如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getNo())) {
            queryWrapper = queryWrapper.like(StockOut::getNo, input.getNo());
        }
        if (Extension.isNotNullOrEmpty(input.getRemark())) {
            queryWrapper = queryWrapper.like(StockOut::getRemark, input.getRemark());
        }

        if (input.getCustomerId() != null) {
            queryWrapper = queryWrapper.eq(StockOut::getCustomerId, input.getCustomerId());
        }

        if (input.getWarehouseId() != null) {
            queryWrapper = queryWrapper.eq(StockOut::getWarehouseId, input.getWarehouseId());
        }

        if (input.getStockOutType() != null) {
            queryWrapper = queryWrapper.eq(StockOut::getStockOutType, input.getStockOutType());
        }

        if (input.getStockOutStatus() != null) {
            queryWrapper = queryWrapper.eq(StockOut::getStockOutStatus, input.getStockOutStatus());
        }
        if (input.getOutTimeRange() != null && !input.getOutTimeRange().isEmpty()) {
            queryWrapper = queryWrapper.lt(StockOut::getOutTime, input.getOutTimeRange().get(1));
            queryWrapper = queryWrapper.gt(StockOut::getOutTime, input.getOutTimeRange().get(0));
        }
        if (input.getPlanOutTimeRange() != null && !input.getPlanOutTimeRange().isEmpty()) {
            queryWrapper = queryWrapper.lt(StockOut::getPlanOutTime, input.getPlanOutTimeRange().get(1));
            queryWrapper = queryWrapper.gt(StockOut::getPlanOutTime, input.getPlanOutTimeRange().get(0));
        }
        if (input.getIsNotComplted() != null && input.getIsNotComplted() == true) {
            queryWrapper = queryWrapper.notIn(StockOut::getStockOutStatus, StockOutStatusEnum.出库完成.index())
                    .le(StockOut::getPlanOutTime, LocalDateTime.now().plusDays(7));
        }
        return queryWrapper;
    }

    /**
     * 处理出库单对于的外键数据
     */
    private StockOutDto DispatchItem(StockOutDto item) throws InvocationTargetException, IllegalAccessException {

        //查询出关联的Warehouse表信息
        Warehouse WarehouseEntity = WarehouseMapper.selectById(item.getWarehouseId());
        item.setWarehouseDto(WarehouseEntity.MapToDto());


        //查询出关联的Customer表信息
        Customer CustomerEntity = CustomerMapper.selectById(item.getCustomerId());
        item.setCustomerDto(CustomerEntity.MapToDto());


        //计算计划时间和当前时间的总分钟
        long seconds = Duration.between(LocalDateTime.now(), item.getPlanOutTime()).toSeconds();
        double hour = (double) seconds / 3600;
        if (hour >= 7 * 24) {
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
     * 出库单分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<StockOutDto> List(StockOutPagedInput input) {
        //构建where条件+排序
        LambdaQueryWrapper<StockOut> queryWrapper = BuilderQuery(input);

        //按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(StockOut::getCreationTime);
        //构建一个分页查询的model
        Page<StockOut> page = new Page<>(input.getPage(), input.getLimit());
        //从数据库进行分页查询获取出库单数据
        IPage<StockOut> pageRecords = StockOutMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount = StockOutMapper.selectCount(queryWrapper);
        //把StockOut实体转换成StockOut传输模型
        List<StockOutDto> items = Extension.copyBeanList(pageRecords.getRecords(), StockOutDto.class);

        for (StockOutDto item : items) {
            DispatchItem(item);
        }
        //返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个出库单查询
     */
    @SneakyThrows
    @Override
    public StockOutDto Get(StockOutPagedInput input) {
        if (input.getId() == null) {
            return new StockOutDto();
        }
        StockOut entity = StockOutMapper.selectById(input.getId());
        StockOutDto dto = entity.MapToDto();
        DispatchItem(dto);
        return dto;
    }

    /**
     * 出库单创建或者修改
     */
    @SneakyThrows
    @Override
    public StockOutDto CreateOrEdit(StockOutDto input) {

        Long sameCount = StockOutMapper.selectCount(Wrappers.<StockOut>lambdaQuery()
                .ne(input.getId() != null, StockOut::getId, input.getId())
                .eq(StockOut::getWarehouseId, input.getWarehouseId())
                .eq(StockOut::getNo, input.getNo()));
        if (sameCount > 0) {
            throw new CustomException("当前仓库下的存在相同的出库单号了,请检查是否正确");
        }
        //声明一个出库单实体
        StockOut StockOut = input.MapToEntity();
        //调用数据库的增加或者修改方法
        saveOrUpdate(StockOut);
        //把传输模型返回给前端
        return StockOut.MapToDto();
    }

    /**
     * 出库完成
     */
    @SneakyThrows
    @Override
    public StockOutDto Complete(StockOutDto input) {

        List<StockOutDet> stockOutDets = StockOutDetMapper.selectList(Wrappers.<StockOutDet>lambdaQuery().eq(StockOutDet::getStockOutId, input.getId()));
        if (stockOutDets.size() == 0) {
            throw new CustomException("当前出库单没有明细数据了");
        }
        input.setOutTime(LocalDateTime.now());
        input.setStockOutStatus(StockOutStatusEnum.出库完成.index());
        //声明一个出库单实体
        StockOut StockOut = input.MapToEntity();
        //调用数据库的增加或者修改方法
        saveOrUpdate(StockOut);
        //把传输模型返回给前端
        return StockOut.MapToDto();
    }

    /**
     * 出库单删除
     */
    @Override
    public void Delete(IdInput input) {
        StockOut entity = StockOutMapper.selectById(input.getId());
        StockOutMapper.deleteById(entity);
    }

    /**
     * 出库单批量删除
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
     * 最近一年的出库单量
     */
    @SneakyThrows
    @Override
    public List<Object> GetStockOutByDayEchart(StockOutPagedInput input) {


        ArrayList<Object> objectArrayList = new ArrayList<>();
        List<StockOut> StockOuts = StockOutMapper.selectList(Wrappers.<StockOut>lambdaQuery().eq(StockOut::getWarehouseId, input.getWarehouseId()));

        for (int index = 0; index < 365; index++) {
            HashMap<String, Object> stringObjectHashMap = new HashMap<>();
            int day = index;
            LocalDateTime dateTime = LocalDate.now().atStartOfDay().minusDays(day);
            LocalDateTime dateTime2 = dateTime.plusDays(1);

            stringObjectHashMap.put("date", dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));


            List<StockOut> dayStockOuts = StockOuts.stream().filter(x -> x.getCreationTime().plusSeconds(1).isAfter(dateTime) && x.getCreationTime().plusSeconds(1).isBefore(dateTime2)).toList();

            //按出库状态统计
            long waitOutStatusCount = dayStockOuts.stream().filter(x -> x.getStockOutStatus() == StockOutStatusEnum.待出库.index()).count();
            stringObjectHashMap.put("waitOutStatusCount", waitOutStatusCount);

            long completeAssignStatusCount = dayStockOuts.stream().filter(x -> x.getStockOutStatus() == StockOutStatusEnum.完成分配.index()).count();
            stringObjectHashMap.put("completeAssignStatusCount", completeAssignStatusCount);

            long outCompleteStatusCount = dayStockOuts.stream().filter(x -> x.getStockOutStatus() == StockOutStatusEnum.出库完成.index()).count();
            stringObjectHashMap.put("outCompleteStatusCount", outCompleteStatusCount);


            objectArrayList.add(stringObjectHashMap);
        }

        return objectArrayList;
    }
}
