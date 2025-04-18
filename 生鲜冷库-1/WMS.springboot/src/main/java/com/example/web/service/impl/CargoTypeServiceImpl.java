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
 * 商品分类功能实现类
 */
@Service
public class CargoTypeServiceImpl extends ServiceImpl<CargoTypeMapper, CargoType> implements CargoTypeService {

	 /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的CargoType表mapper对象
     */
    @Autowired
    private CargoTypeMapper CargoTypeMapper;
    @Autowired
    private WarehouseMapper  WarehouseMapper;                        

  
   /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<CargoType> BuilderQuery(CargoTypePagedInput input) {
       //声明一个支持商品分类查询的(拉姆达)表达式
        LambdaQueryWrapper<CargoType> queryWrapper = Wrappers.<CargoType>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, CargoType::getId, input.getId())
                .eq(input.getCreatorId() != null, CargoType::getCreatorId, input.getCreatorId());
   //如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getName())) {
             queryWrapper = queryWrapper.like(CargoType::getName, input.getName());
       	 }

        if (input.getWarehouseId() != null) {
            queryWrapper = queryWrapper.eq(CargoType::getWarehouseId, input.getWarehouseId());
       	 }
      return queryWrapper;
    }
  
    /**
     * 处理商品分类对于的外键数据
     */
   private CargoTypeDto DispatchItem(CargoTypeDto item) throws InvocationTargetException, IllegalAccessException {           
          	            
           //查询出关联的Warehouse表信息           
            Warehouse  WarehouseEntity= WarehouseMapper.selectById(item.getWarehouseId());
            item.setWarehouseDto(WarehouseEntity.MapToDto());              
       
     return item; 
   }
  
    /**
     * 商品分类分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<CargoTypeDto> List(CargoTypePagedInput input) {
			//构建where条件+排序
        LambdaQueryWrapper<CargoType> queryWrapper = BuilderQuery(input);
       
        //按创建时间从大到小排序 最新的显示在最前面
        queryWrapper=queryWrapper.orderByDesc(CargoType::getCreationTime);
        //构建一个分页查询的model
        Page<CargoType> page = new Page<>(input.getPage(), input.getLimit());
         //从数据库进行分页查询获取商品分类数据
        IPage<CargoType> pageRecords= CargoTypeMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount= CargoTypeMapper.selectCount(queryWrapper);
        //把CargoType实体转换成CargoType传输模型
        List<CargoTypeDto> items= Extension.copyBeanList(pageRecords.getRecords(),CargoTypeDto.class);

		   for (CargoTypeDto item : items) {  
           DispatchItem(item);
         }
        //返回一个分页结构给前端
        return PagedResult.GetInstance(items,totalCount);

    }
  
    /**
     * 单个商品分类查询
     */
    @SneakyThrows
    @Override
    public CargoTypeDto Get(CargoTypePagedInput input) {
       if(input.getId()==null)
        {
         return new CargoTypeDto();
        }
        CargoType entity = CargoTypeMapper.selectById(input.getId());
        CargoTypeDto dto= entity.MapToDto();
        DispatchItem(dto);
        return dto;   
    }

    /**
     *商品分类创建或者修改
     */
    @SneakyThrows
    @Override
    public CargoTypeDto CreateOrEdit(CargoTypeDto input) {
        //声明一个商品分类实体
        CargoType CargoType=input.MapToEntity();  
        //调用数据库的增加或者修改方法
        saveOrUpdate(CargoType);
        //把传输模型返回给前端
        return CargoType.MapToDto();
    }
    /**
     * 商品分类删除
     */
    @Override
    public void Delete(IdInput input) {
        CargoType entity = CargoTypeMapper.selectById(input.getId());
        CargoTypeMapper.deleteById(entity);
    }

    /**
     * 商品分类批量删除
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
