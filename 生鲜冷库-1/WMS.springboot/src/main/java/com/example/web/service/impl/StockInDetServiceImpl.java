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
 * 入库明细功能实现类
 */
@Service
public class StockInDetServiceImpl extends ServiceImpl<StockInDetMapper, StockInDet> implements StockInDetService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的StockInDet表mapper对象
     */
    @Autowired
    private StockInDetMapper StockInDetMapper;
    @Autowired
    private WarehouseMapper WarehouseMapper;
    @Autowired
    private StockInMapper StockInMapper;
    @Autowired
    private WarehouseBinMapper WarehouseBinMapper;
    @Autowired
    private CargoMapper CargoMapper;


    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<StockInDet> BuilderQuery(StockInDetPagedInput input) {
        //声明一个支持入库明细查询的(拉姆达)表达式
        LambdaQueryWrapper<StockInDet> queryWrapper = Wrappers.<StockInDet>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, StockInDet::getId, input.getId())
                .eq(input.getCreatorId() != null, StockInDet::getCreatorId, input.getCreatorId());
        //如果前端搜索传入查询条件则拼接查询条件

        if (input.getStockInId() != null) {
            queryWrapper = queryWrapper.eq(StockInDet::getStockInId, input.getStockInId());
        }

        if (input.getWarehouseBinId() != null) {
            queryWrapper = queryWrapper.eq(StockInDet::getWarehouseBinId, input.getWarehouseBinId());
        }

        if (input.getCargoId() != null) {
            queryWrapper = queryWrapper.eq(StockInDet::getCargoId, input.getCargoId());
        }

        if (input.getWarehouseId() != null) {
            queryWrapper = queryWrapper.eq(StockInDet::getWarehouseId, input.getWarehouseId());
        }
        return queryWrapper;
    }

    /**
     * 处理入库明细对于的外键数据
     */
    private StockInDetDto DispatchItem(StockInDetDto item) throws InvocationTargetException, IllegalAccessException {

        //查询出关联的Warehouse表信息
        Warehouse WarehouseEntity = WarehouseMapper.selectById(item.getWarehouseId());
        item.setWarehouseDto(WarehouseEntity.MapToDto());


        //查询出关联的StockIn表信息
        StockIn StockInEntity = StockInMapper.selectById(item.getStockInId());
        item.setStockInDto(StockInEntity.MapToDto());


        //查询出关联的WarehouseBin表信息
        WarehouseBin WarehouseBinEntity = WarehouseBinMapper.selectById(item.getWarehouseBinId());
        item.setWarehouseBinDto(WarehouseBinEntity.MapToDto());


        //查询出关联的Cargo表信息
        Cargo CargoEntity = CargoMapper.selectById(item.getCargoId());
        item.setCargoDto(CargoEntity.MapToDto());

        return item;
    }

    /**
     * 入库明细分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<StockInDetDto> List(StockInDetPagedInput input) {
        //构建where条件+排序
        LambdaQueryWrapper<StockInDet> queryWrapper = BuilderQuery(input);

        //按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(StockInDet::getCreationTime);
        //构建一个分页查询的model
        Page<StockInDet> page = new Page<>(input.getPage(), input.getLimit());
        //从数据库进行分页查询获取入库明细数据
        IPage<StockInDet> pageRecords = StockInDetMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount = StockInDetMapper.selectCount(queryWrapper);
        //把StockInDet实体转换成StockInDet传输模型
        List<StockInDetDto> items = Extension.copyBeanList(pageRecords.getRecords(), StockInDetDto.class);

        for (StockInDetDto item : items) {
            DispatchItem(item);
        }
        //返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个入库明细查询
     */
    @SneakyThrows
    @Override
    public StockInDetDto Get(StockInDetPagedInput input) {
        if (input.getId() == null) {
            return new StockInDetDto();
        }
        StockInDet entity = StockInDetMapper.selectById(input.getId());
        StockInDetDto dto = entity.MapToDto();
        DispatchItem(dto);
        return dto;
    }

    /**
     * 入库明细创建或者修改
     */
    @SneakyThrows
    @Override
    public StockInDetDto CreateOrEdit(StockInDetDto input) {

        Long sameCount = StockInDetMapper.selectCount(Wrappers.<StockInDet>lambdaQuery().ne(input.getId() != null, StockInDet::getId, input.getId())
                .eq(StockInDet::getWarehouseBinId, input.getWarehouseBinId())
                .eq(StockInDet::getCargoId, input.getCargoId())
                .eq(StockInDet::getStockInId, input.getStockInId()));
        if (sameCount > 0) {
            throw new CustomException("当前入库单存在相同商品相同库位的明细数据了,请检查数据是否正确");
        }

        //声明一个入库明细实体
        StockInDet StockInDet = input.MapToEntity();
        //调用数据库的增加或者修改方法
        saveOrUpdate(StockInDet);

        BeginStockIn(input.getStockInId());
        //把传输模型返回给前端
        return StockInDet.MapToDto();
    }



    /**
     * 修改订单的状态变成开始
     * @param StockInId
     */
    private void BeginStockIn(Integer StockInId) {
        StockIn stockIn = StockInMapper.selectById(StockInId);
        if (stockIn.getStockInStatus() == StockInStatusEnum.待入库.index()) {
            stockIn.setStockInStatus(StockInStatusEnum.入库中.index());
            StockInMapper.updateById(stockIn);
        }

    }

    /**
     * 入库明细删除
     */
    @Override
    public void Delete(IdInput input) {
        StockInDet entity = StockInDetMapper.selectById(input.getId());
        StockInDetMapper.deleteById(entity);
    }

    /**
     * 入库明细批量删除
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
