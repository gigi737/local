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
 * 仓库功能实现类
 */
@Service
public class WarehouseServiceImpl extends ServiceImpl<WarehouseMapper, Warehouse> implements WarehouseService {

	 /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的Warehouse表mapper对象
     */
    @Autowired
    private WarehouseMapper WarehouseMapper;

  
   /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<Warehouse> BuilderQuery(WarehousePagedInput input) {
       //声明一个支持仓库查询的(拉姆达)表达式
        LambdaQueryWrapper<Warehouse> queryWrapper = Wrappers.<Warehouse>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, Warehouse::getId, input.getId())
                .eq(input.getCreatorId() != null, Warehouse::getCreatorId, input.getCreatorId());
   //如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getName())) {
             queryWrapper = queryWrapper.like(Warehouse::getName, input.getName());
       	 }
        if (Extension.isNotNullOrEmpty(input.getAddress())) {
             queryWrapper = queryWrapper.like(Warehouse::getAddress, input.getAddress());
       	 }
        if (Extension.isNotNullOrEmpty(input.getCity())) {
             queryWrapper = queryWrapper.like(Warehouse::getCity, input.getCity());
       	 }
        if (Extension.isNotNullOrEmpty(input.getCountry())) {
             queryWrapper = queryWrapper.like(Warehouse::getCountry, input.getCountry());
       	 }
        if (Extension.isNotNullOrEmpty(input.getContactPerson())) {
             queryWrapper = queryWrapper.like(Warehouse::getContactPerson, input.getContactPerson());
       	 }
        if (Extension.isNotNullOrEmpty(input.getType())) {
             queryWrapper = queryWrapper.like(Warehouse::getType, input.getType());
       	 }
        if (Extension.isNotNullOrEmpty(input.getEnvironmentalConditions())) {
             queryWrapper = queryWrapper.like(Warehouse::getEnvironmentalConditions, input.getEnvironmentalConditions());
       	 }
        if (Extension.isNotNullOrEmpty(input.getPhone())) {
             queryWrapper = queryWrapper.like(Warehouse::getPhone, input.getPhone());
       	 }
      return queryWrapper;
    }
  
    /**
     * 处理仓库对于的外键数据
     */
   private WarehouseDto DispatchItem(WarehouseDto item) throws InvocationTargetException, IllegalAccessException {       
     return item; 
   }
  
    /**
     * 仓库分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<WarehouseDto> List(WarehousePagedInput input) {
			//构建where条件+排序
        LambdaQueryWrapper<Warehouse> queryWrapper = BuilderQuery(input);
       
        //按创建时间从大到小排序 最新的显示在最前面
        queryWrapper=queryWrapper.orderByDesc(Warehouse::getCreationTime);
        //构建一个分页查询的model
        Page<Warehouse> page = new Page<>(input.getPage(), input.getLimit());
         //从数据库进行分页查询获取仓库数据
        IPage<Warehouse> pageRecords= WarehouseMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount= WarehouseMapper.selectCount(queryWrapper);
        //把Warehouse实体转换成Warehouse传输模型
        List<WarehouseDto> items= Extension.copyBeanList(pageRecords.getRecords(),WarehouseDto.class);

		   for (WarehouseDto item : items) {  
           DispatchItem(item);
         }
        //返回一个分页结构给前端
        return PagedResult.GetInstance(items,totalCount);

    }
  
    /**
     * 单个仓库查询
     */
    @SneakyThrows
    @Override
    public WarehouseDto Get(WarehousePagedInput input) {
       if(input.getId()==null)
        {
         return new WarehouseDto();
        }
        Warehouse entity = WarehouseMapper.selectById(input.getId());
        WarehouseDto dto= entity.MapToDto();
        DispatchItem(dto);
        return dto;   
    }

    /**
     *仓库创建或者修改
     */
    @SneakyThrows
    @Override
    public WarehouseDto CreateOrEdit(WarehouseDto input) {
        //声明一个仓库实体
        Warehouse Warehouse=input.MapToEntity();  
        //调用数据库的增加或者修改方法
        saveOrUpdate(Warehouse);
        //把传输模型返回给前端
        return Warehouse.MapToDto();
    }
    /**
     * 仓库删除
     */
    @Override
    public void Delete(IdInput input) {
        Warehouse entity = WarehouseMapper.selectById(input.getId());
        WarehouseMapper.deleteById(entity);
    }

    /**
     * 仓库批量删除
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
