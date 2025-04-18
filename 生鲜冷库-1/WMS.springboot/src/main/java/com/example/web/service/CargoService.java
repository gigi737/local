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
 * 商品功能的Service接口的定义清单
 */
public interface CargoService extends IService<Cargo> {

    /**
     * 商品的分页查询方法接口定义
     */
    public PagedResult<CargoDto> List(CargoPagedInput input) ;
    /**
     * 商品的新增或者修改方法接口定义
     */
    public CargoDto CreateOrEdit(CargoDto input);

     /**
     * 获取商品信息
     */
    public CargoDto Get(CargoPagedInput input);
 	 /**
     * 商品删除
     */
    public void Delete(IdInput input);

    /**
     * 商品批量删除
     */
    public void BatchDelete(IdsInput input);
  

}
