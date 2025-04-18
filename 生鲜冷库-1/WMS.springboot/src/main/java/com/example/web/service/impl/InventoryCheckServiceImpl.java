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
import java.util.List;

import lombok.SneakyThrows;

import com.example.web.tools.*;

/**
 * 盘点单功能实现类
 */
@Service
public class InventoryCheckServiceImpl extends ServiceImpl<InventoryCheckMapper, InventoryCheck> implements InventoryCheckService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的InventoryCheck表mapper对象
     */
    @Autowired
    private InventoryCheckMapper InventoryCheckMapper;
    @Autowired
    private WarehouseMapper WarehouseMapper;
    @Autowired
    private CustomerMapper CustomerMapper;


    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<InventoryCheck> BuilderQuery(InventoryCheckPagedInput input) {
        //声明一个支持盘点单查询的(拉姆达)表达式
        LambdaQueryWrapper<InventoryCheck> queryWrapper = Wrappers.<InventoryCheck>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, InventoryCheck::getId, input.getId())
                .eq(input.getCreatorId() != null, InventoryCheck::getCreatorId, input.getCreatorId());
        //如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getNo())) {
            queryWrapper = queryWrapper.like(InventoryCheck::getNo, input.getNo());
        }

        if (input.getCheckUserId() != null) {
            queryWrapper = queryWrapper.eq(InventoryCheck::getCheckUserId, input.getCheckUserId());
        }

        if (input.getWarehouseId() != null) {
            queryWrapper = queryWrapper.eq(InventoryCheck::getWarehouseId, input.getWarehouseId());
        }

        if (input.getCustomerId() != null) {
            queryWrapper = queryWrapper.eq(InventoryCheck::getCustomerId, input.getCustomerId());
        }
        if (input.getIsExcuteInventoryChange() != null) {
            queryWrapper = queryWrapper.eq(InventoryCheck::getIsExcuteInventoryChange, input.getIsExcuteInventoryChange());
        }
        if (input.getCheckTimeRange() != null && !input.getCheckTimeRange().isEmpty()) {
            queryWrapper = queryWrapper.ge(InventoryCheck::getBeginCheckTime, input.getCheckTimeRange().get(0));
            queryWrapper = queryWrapper.le(InventoryCheck::getEndCheckTime, input.getCheckTimeRange().get(1));
        }
        return queryWrapper;
    }

    /**
     * 处理盘点单对于的外键数据
     */
    private InventoryCheckDto DispatchItem(InventoryCheckDto item) throws InvocationTargetException, IllegalAccessException {

        //查询出关联的AppUser表信息
        AppUser CheckUserEntity = AppUserMapper.selectById(item.getCheckUserId());
        item.setCheckUserDto(CheckUserEntity.MapToDto());


        //查询出关联的Warehouse表信息
        Warehouse WarehouseEntity = WarehouseMapper.selectById(item.getWarehouseId());
        item.setWarehouseDto(WarehouseEntity.MapToDto());


        //查询出关联的Customer表信息
        Customer CustomerEntity = CustomerMapper.selectById(item.getCustomerId());
        item.setCustomerDto(CustomerEntity.MapToDto());

        return item;
    }

    /**
     * 盘点单分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<InventoryCheckDto> List(InventoryCheckPagedInput input) {
        //构建where条件+排序
        LambdaQueryWrapper<InventoryCheck> queryWrapper = BuilderQuery(input);

        //按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(InventoryCheck::getCreationTime);
        //构建一个分页查询的model
        Page<InventoryCheck> page = new Page<>(input.getPage(), input.getLimit());
        //从数据库进行分页查询获取盘点单数据
        IPage<InventoryCheck> pageRecords = InventoryCheckMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount = InventoryCheckMapper.selectCount(queryWrapper);
        //把InventoryCheck实体转换成InventoryCheck传输模型
        List<InventoryCheckDto> items = Extension.copyBeanList(pageRecords.getRecords(), InventoryCheckDto.class);

        for (InventoryCheckDto item : items) {
            DispatchItem(item);
        }
        //返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个盘点单查询
     */
    @SneakyThrows
    @Override
    public InventoryCheckDto Get(InventoryCheckPagedInput input) {
        if (input.getId() == null) {
            return new InventoryCheckDto();
        }
        InventoryCheck entity = InventoryCheckMapper.selectById(input.getId());
        InventoryCheckDto dto = entity.MapToDto();
        DispatchItem(dto);
        return dto;
    }

    /**
     * 盘点单创建或者修改
     */
    @SneakyThrows
    @Override
    public InventoryCheckDto CreateOrEdit(InventoryCheckDto input) {

        Long sameCount = InventoryCheckMapper.selectCount(Wrappers.<InventoryCheck>lambdaQuery()
                .ne(input.getId() != null, InventoryCheck::getId, input.getId())
                .eq(InventoryCheck::getWarehouseId, input.getWarehouseId())
                .eq(InventoryCheck::getNo, input.getNo()));
        if (sameCount > 0) {
            throw new CustomException("当前仓库下的存在相同的盘点单号了,请检查是否正确");
        }
        //声明一个盘点单实体
        InventoryCheck InventoryCheck = input.MapToEntity();
        //调用数据库的增加或者修改方法
        saveOrUpdate(InventoryCheck);
        //把传输模型返回给前端
        return InventoryCheck.MapToDto();
    }

    /**
     * 盘点完成
     */
    @SneakyThrows
    @Override
    public void Completed(InventoryCheckDto input) {

        input.setInventoryCheckStatus(InventoryCheckStatusEnum.盘点完成.index());
        //声明一个盘点单实体
        InventoryCheck InventoryCheck = input.MapToEntity();
        //调用数据库的增加或者修改方法
        saveOrUpdate(InventoryCheck);

    }

    /**
     * 盘点完成执行库存变动
     */
    @SneakyThrows
    @Override
    public void CompletedStockChange(InventoryCheckDto input) {

        input.setIsExcuteInventoryChange(true);
        //声明一个盘点单实体
        InventoryCheck InventoryCheck = input.MapToEntity();
        //调用数据库的增加或者修改方法
        saveOrUpdate(InventoryCheck);
    }


    /**
     * 盘点单删除
     */
    @Override
    public void Delete(IdInput input) {
        InventoryCheck entity = InventoryCheckMapper.selectById(input.getId());
        InventoryCheckMapper.deleteById(entity);
    }

    /**
     * 盘点单批量删除
     */
    @Override
    public void BatchDelete(IdsInput input) {
        for (Integer id : input.getIds()) {
            IdInput idInput = new IdInput();
            idInput.setId(id);
            Delete(idInput);
        }
    }
}
