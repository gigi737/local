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
 * 商品分类功能的Service接口的定义清单
 */
public interface CargoTypeService extends IService<CargoType> {

    /**
     * 商品分类的分页查询方法接口定义
     */
    public PagedResult<CargoTypeDto> List(CargoTypePagedInput input) ;
    /**
     * 商品分类的新增或者修改方法接口定义
     */
    public CargoTypeDto CreateOrEdit(CargoTypeDto input);

     /**
     * 获取商品分类信息
     */
    public CargoTypeDto Get(CargoTypePagedInput input);
 	 /**
     * 商品分类删除
     */
    public void Delete(IdInput input);

    /**
     * 商品分类批量删除
     */
    public void BatchDelete(IdsInput input);
  

}
