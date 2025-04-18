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
 * 商品功能实现类
 */
@Service
public class CargoServiceImpl extends ServiceImpl<CargoMapper, Cargo> implements CargoService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的Cargo表mapper对象
     */
    @Autowired
    private CargoMapper CargoMapper;
    @Autowired
    private CargoTypeMapper CargoTypeMapper;
    @Autowired
    private CustomerMapper CustomerMapper;
    @Autowired
    private WarehouseMapper WarehouseMapper;


    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<Cargo> BuilderQuery(CargoPagedInput input) {
        //声明一个支持商品查询的(拉姆达)表达式
        LambdaQueryWrapper<Cargo> queryWrapper = Wrappers.<Cargo>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, Cargo::getId, input.getId())
                .eq(input.getCreatorId() != null, Cargo::getCreatorId, input.getCreatorId());
        //如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getName())) {
            queryWrapper = queryWrapper.like(Cargo::getName, input.getName());
        }
        if (Extension.isNotNullOrEmpty(input.getSKU())) {
            queryWrapper = queryWrapper.like(Cargo::getSKU, input.getSKU());
        }
        if (Extension.isNotNullOrEmpty(input.getBrand())) {
            queryWrapper = queryWrapper.like(Cargo::getBrand, input.getBrand());
        }
        if (Extension.isNotNullOrEmpty(input.getSpecification())) {
            queryWrapper = queryWrapper.like(Cargo::getSpecification, input.getSpecification());
        }
        if (Extension.isNotNullOrEmpty(input.getUnit())) {
            queryWrapper = queryWrapper.like(Cargo::getUnit, input.getUnit());
        }


        if (input.getWarehouseId() != null) {
            queryWrapper = queryWrapper.eq(Cargo::getWarehouseId, input.getWarehouseId());
        }

        if (input.getCustomerId() != null) {
            queryWrapper = queryWrapper.eq(Cargo::getCustomerId, input.getCustomerId());
        }

        if (input.getCargoTypeId() != null) {
            queryWrapper = queryWrapper.eq(Cargo::getCargoTypeId, input.getCargoTypeId());
        }
        return queryWrapper;
    }

    /**
     * 处理商品对于的外键数据
     */
    private CargoDto DispatchItem(CargoDto item) throws InvocationTargetException, IllegalAccessException {

        //查询出关联的CargoType表信息
        CargoType CargoTypeEntity = CargoTypeMapper.selectById(item.getCargoTypeId());
        item.setCargoTypeDto(CargoTypeEntity.MapToDto());


        //查询出关联的Customer表信息
        Customer CustomerEntity = CustomerMapper.selectById(item.getCustomerId());
        item.setCustomerDto(CustomerEntity.MapToDto());


        //查询出关联的Warehouse表信息
        Warehouse WarehouseEntity = WarehouseMapper.selectById(item.getWarehouseId());
        item.setWarehouseDto(WarehouseEntity.MapToDto());

        return item;
    }

    /**
     * 商品分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<CargoDto> List(CargoPagedInput input) {
        //构建where条件+排序
        LambdaQueryWrapper<Cargo> queryWrapper = BuilderQuery(input);

        //按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(Cargo::getCreationTime);
        //构建一个分页查询的model
        Page<Cargo> page = new Page<>(input.getPage(), input.getLimit());
        //从数据库进行分页查询获取商品数据
        IPage<Cargo> pageRecords = CargoMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount = CargoMapper.selectCount(queryWrapper);
        //把Cargo实体转换成Cargo传输模型
        List<CargoDto> items = Extension.copyBeanList(pageRecords.getRecords(), CargoDto.class);

        for (CargoDto item : items) {
            DispatchItem(item);
        }
        //返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个商品查询
     */
    @SneakyThrows
    @Override
    public CargoDto Get(CargoPagedInput input) {
        if (input.getId() == null) {
            return new CargoDto();
        }
        Cargo entity = CargoMapper.selectById(input.getId());
        CargoDto dto = entity.MapToDto();
        DispatchItem(dto);
        return dto;
    }

    /**
     * 商品创建或者修改
     */
    @SneakyThrows
    @Override
    public CargoDto CreateOrEdit(CargoDto input) {
        Long sameCount = CargoMapper.selectCount(Wrappers.<Cargo>lambdaQuery().ne(input.getId() != null, Cargo::getId, input.getId())
                .eq(Cargo::getSKU, input.getSKU())
                .eq(Cargo::getWarehouseId, input.getWarehouseId())
                .eq(Cargo::getCustomerId, input.getCustomerId()));
        if(sameCount>0){
            throw  new CustomException("当前仓库下的当前货主存在相同的SKU了,请检查是否正确");
        }
        //声明一个商品实体
        Cargo Cargo = input.MapToEntity();
        //调用数据库的增加或者修改方法
        saveOrUpdate(Cargo);
        //把传输模型返回给前端
        return Cargo.MapToDto();
    }

    /**
     * 商品删除
     */
    @Override
    public void Delete(IdInput input) {
        Cargo entity = CargoMapper.selectById(input.getId());
        CargoMapper.deleteById(entity);
    }

    /**
     * 商品批量删除
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
