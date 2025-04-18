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
 * 仓库功能的Service接口的定义清单
 */
public interface WarehouseService extends IService<Warehouse> {

    /**
     * 仓库的分页查询方法接口定义
     */
    public PagedResult<WarehouseDto> List(WarehousePagedInput input) ;
    /**
     * 仓库的新增或者修改方法接口定义
     */
    public WarehouseDto CreateOrEdit(WarehouseDto input);

     /**
     * 获取仓库信息
     */
    public WarehouseDto Get(WarehousePagedInput input);
 	 /**
     * 仓库删除
     */
    public void Delete(IdInput input);

    /**
     * 仓库批量删除
     */
    public void BatchDelete(IdsInput input);
  

}
