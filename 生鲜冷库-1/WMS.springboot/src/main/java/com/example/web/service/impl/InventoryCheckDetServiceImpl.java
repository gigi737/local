package com.example.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.SysConst;
import com.example.web.dto.*;
import com.example.web.dto.query.*;
import com.example.web.entity.*;
import com.example.web.mapper.*;
import com.example.web.enums.*;
import com.example.web.service.*;
import com.example.web.tools.dto.*;
import com.example.web.tools.exception.CustomException;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;

import java.io.IOException;

import com.example.web.tools.*;

import java.text.DecimalFormat;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 盘点明细功能实现类
 */
@Service
public class InventoryCheckDetServiceImpl extends ServiceImpl<InventoryCheckDetMapper, InventoryCheckDet> implements InventoryCheckDetService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的InventoryCheckDet表mapper对象
     */
    @Autowired
    private InventoryCheckDetMapper InventoryCheckDetMapper;
    @Autowired
    private CargoMapper CargoMapper;
    @Autowired
    private WarehouseBinMapper WarehouseBinMapper;
    @Autowired
    private InventoryCheckMapper InventoryCheckMapper;
    @Autowired
    private WarehouseMapper WarehouseMapper;


    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<InventoryCheckDet> BuilderQuery(InventoryCheckDetPagedInput input) {
        //声明一个支持盘点明细查询的(拉姆达)表达式
        LambdaQueryWrapper<InventoryCheckDet> queryWrapper = Wrappers.<InventoryCheckDet>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, InventoryCheckDet::getId, input.getId())
                .eq(input.getCreatorId() != null, InventoryCheckDet::getCreatorId, input.getCreatorId());
        //如果前端搜索传入查询条件则拼接查询条件

        if (input.getInventoryCheckId() != null) {
            queryWrapper = queryWrapper.eq(InventoryCheckDet::getInventoryCheckId, input.getInventoryCheckId());
        }

        if (input.getWarehouseBinId() != null) {
            queryWrapper = queryWrapper.eq(InventoryCheckDet::getWarehouseBinId, input.getWarehouseBinId());
        }

        if (input.getWarehouseId() != null) {
            queryWrapper = queryWrapper.eq(InventoryCheckDet::getWarehouseId, input.getWarehouseId());
        }

        if (input.getCargoId() != null) {
            queryWrapper = queryWrapper.eq(InventoryCheckDet::getCargoId, input.getCargoId());
        }
        return queryWrapper;
    }

    /**
     * 处理盘点明细对于的外键数据
     */
    private InventoryCheckDetDto DispatchItem(InventoryCheckDetDto item) throws InvocationTargetException, IllegalAccessException {

        //查询出关联的Cargo表信息
        Cargo CargoEntity = CargoMapper.selectById(item.getCargoId());
        item.setCargoDto(CargoEntity.MapToDto());


        //查询出关联的WarehouseBin表信息
        WarehouseBin WarehouseBinEntity = WarehouseBinMapper.selectById(item.getWarehouseBinId());
        item.setWarehouseBinDto(WarehouseBinEntity.MapToDto());


        //查询出关联的InventoryCheck表信息
        InventoryCheck InventoryCheckEntity = InventoryCheckMapper.selectById(item.getInventoryCheckId());
        item.setInventoryCheckDto(InventoryCheckEntity.MapToDto());


        //查询出关联的Warehouse表信息
        Warehouse WarehouseEntity = WarehouseMapper.selectById(item.getWarehouseId());
        item.setWarehouseDto(WarehouseEntity.MapToDto());

        return item;
    }

    /**
     * 盘点明细分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<InventoryCheckDetDto> List(InventoryCheckDetPagedInput input) {
        //构建where条件+排序
        LambdaQueryWrapper<InventoryCheckDet> queryWrapper = BuilderQuery(input);

        //按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(InventoryCheckDet::getCreationTime);
        //构建一个分页查询的model
        Page<InventoryCheckDet> page = new Page<>(input.getPage(), input.getLimit());
        //从数据库进行分页查询获取盘点明细数据
        IPage<InventoryCheckDet> pageRecords = InventoryCheckDetMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount = InventoryCheckDetMapper.selectCount(queryWrapper);
        //把InventoryCheckDet实体转换成InventoryCheckDet传输模型
        List<InventoryCheckDetDto> items = Extension.copyBeanList(pageRecords.getRecords(), InventoryCheckDetDto.class);

        for (InventoryCheckDetDto item : items) {
            DispatchItem(item);
        }
        //返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个盘点明细查询
     */
    @SneakyThrows
    @Override
    public InventoryCheckDetDto Get(InventoryCheckDetPagedInput input) {
        if (input.getId() == null) {
            return new InventoryCheckDetDto();
        }
        InventoryCheckDet entity = InventoryCheckDetMapper.selectById(input.getId());
        InventoryCheckDetDto dto = entity.MapToDto();
        DispatchItem(dto);
        return dto;
    }

    /**
     * 盘点明细创建或者修改
     */
    @SneakyThrows
    @Override
    public InventoryCheckDetDto CreateOrEdit(InventoryCheckDetDto input) {

        Long sameCount = InventoryCheckDetMapper.selectCount(Wrappers.<InventoryCheckDet>lambdaQuery().ne(input.getId() != null, InventoryCheckDet::getId, input.getId())
                .eq(InventoryCheckDet::getWarehouseBinId, input.getWarehouseBinId())
                .eq(InventoryCheckDet::getCargoId, input.getCargoId())
                .eq(InventoryCheckDet::getInventoryCheckId, input.getInventoryCheckId()));
        if (sameCount > 0) {
            throw new CustomException("当前盘点单存在相同商品相同库位的明细数据了,请检查数据是否正确");
        }
        //声明一个盘点明细实体
        InventoryCheckDet InventoryCheckDet = input.MapToEntity();
        //调用数据库的增加或者修改方法
        saveOrUpdate(InventoryCheckDet);
        BeginInventoryCheck(InventoryCheckDet.getInventoryCheckId());
        //把传输模型返回给前端
        return InventoryCheckDet.MapToDto();
    }


    /**
     * 开始盘点
     */
    public void BeginInventoryCheck(Integer inventoryCheckId) {
        InventoryCheck inventoryCheck = InventoryCheckMapper.selectById(inventoryCheckId);
        if (inventoryCheck.getInventoryCheckStatus() == InventoryCheckStatusEnum.待盘点.index()) {
            inventoryCheck.setInventoryCheckStatus(InventoryCheckStatusEnum.盘点中.index());
            InventoryCheckMapper.updateById(inventoryCheck);
        }
    }

    /**
     * 盘点明细删除
     */
    @Override
    public void Delete(IdInput input) {
        InventoryCheckDet entity = InventoryCheckDetMapper.selectById(input.getId());
        InventoryCheckDetMapper.deleteById(entity);
    }

    /**
     * 盘点明细批量删除
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
