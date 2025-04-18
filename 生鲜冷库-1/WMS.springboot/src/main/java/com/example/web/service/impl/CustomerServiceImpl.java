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
 * 货主功能实现类
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

	 /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的Customer表mapper对象
     */
    @Autowired
    private CustomerMapper CustomerMapper;
    @Autowired
    private WarehouseMapper  WarehouseMapper;                        

  
   /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<Customer> BuilderQuery(CustomerPagedInput input) {
       //声明一个支持货主查询的(拉姆达)表达式
        LambdaQueryWrapper<Customer> queryWrapper = Wrappers.<Customer>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, Customer::getId, input.getId())
                .eq(input.getCreatorId() != null, Customer::getCreatorId, input.getCreatorId());
   //如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getOwnerName())) {
             queryWrapper = queryWrapper.like(Customer::getOwnerName, input.getOwnerName());
       	 }
        if (Extension.isNotNullOrEmpty(input.getContactPerson())) {
             queryWrapper = queryWrapper.like(Customer::getContactPerson, input.getContactPerson());
       	 }
        if (Extension.isNotNullOrEmpty(input.getPhone())) {
             queryWrapper = queryWrapper.like(Customer::getPhone, input.getPhone());
       	 }
        if (Extension.isNotNullOrEmpty(input.getEmail())) {
             queryWrapper = queryWrapper.like(Customer::getEmail, input.getEmail());
       	 }
        if (Extension.isNotNullOrEmpty(input.getAddress())) {
             queryWrapper = queryWrapper.like(Customer::getAddress, input.getAddress());
       	 }

        if (input.getWarehouseId() != null) {
            queryWrapper = queryWrapper.eq(Customer::getWarehouseId, input.getWarehouseId());
       	 }
      return queryWrapper;
    }
  
    /**
     * 处理货主对于的外键数据
     */
   private CustomerDto DispatchItem(CustomerDto item) throws InvocationTargetException, IllegalAccessException {           
          	            
           //查询出关联的Warehouse表信息           
            Warehouse  WarehouseEntity= WarehouseMapper.selectById(item.getWarehouseId());
            item.setWarehouseDto(WarehouseEntity.MapToDto());              
       
     return item; 
   }
  
    /**
     * 货主分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<CustomerDto> List(CustomerPagedInput input) {
			//构建where条件+排序
        LambdaQueryWrapper<Customer> queryWrapper = BuilderQuery(input);
       
        //按创建时间从大到小排序 最新的显示在最前面
        queryWrapper=queryWrapper.orderByDesc(Customer::getCreationTime);
        //构建一个分页查询的model
        Page<Customer> page = new Page<>(input.getPage(), input.getLimit());
         //从数据库进行分页查询获取货主数据
        IPage<Customer> pageRecords= CustomerMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount= CustomerMapper.selectCount(queryWrapper);
        //把Customer实体转换成Customer传输模型
        List<CustomerDto> items= Extension.copyBeanList(pageRecords.getRecords(),CustomerDto.class);

		   for (CustomerDto item : items) {  
           DispatchItem(item);
         }
        //返回一个分页结构给前端
        return PagedResult.GetInstance(items,totalCount);

    }
  
    /**
     * 单个货主查询
     */
    @SneakyThrows
    @Override
    public CustomerDto Get(CustomerPagedInput input) {
       if(input.getId()==null)
        {
         return new CustomerDto();
        }
        Customer entity = CustomerMapper.selectById(input.getId());
        CustomerDto dto= entity.MapToDto();
        DispatchItem(dto);
        return dto;   
    }

    /**
     *货主创建或者修改
     */
    @SneakyThrows
    @Override
    public CustomerDto CreateOrEdit(CustomerDto input) {
        //声明一个货主实体
        Customer Customer=input.MapToEntity();  
        //调用数据库的增加或者修改方法
        saveOrUpdate(Customer);
        //把传输模型返回给前端
        return Customer.MapToDto();
    }
    /**
     * 货主删除
     */
    @Override
    public void Delete(IdInput input) {
        Customer entity = CustomerMapper.selectById(input.getId());
        CustomerMapper.deleteById(entity);
    }

    /**
     * 货主批量删除
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
