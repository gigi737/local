package com.example.web.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.*;
import com.example.web.dto.query.*;
import com.example.web.entity.*;
import com.example.web.tools.dto.*;
import com.example.web.enums.*;
import java.lang.reflect.InvocationTargetException;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.IOException;
import jakarta.servlet.http.HttpServletResponse;
/**
 * 货主功能的Service接口的定义清单
 */
public interface CustomerService extends IService<Customer> {

    /**
     * 货主的分页查询方法接口定义
     */
    public PagedResult<CustomerDto> List(CustomerPagedInput input) ;
    /**
     * 货主的新增或者修改方法接口定义
     */
    public CustomerDto CreateOrEdit(CustomerDto input);

     /**
     * 获取货主信息
     */
    public CustomerDto Get(CustomerPagedInput input);
 	 /**
     * 货主删除
     */
    public void Delete(IdInput input);

    /**
     * 货主批量删除
     */
    public void BatchDelete(IdsInput input);
  

}
