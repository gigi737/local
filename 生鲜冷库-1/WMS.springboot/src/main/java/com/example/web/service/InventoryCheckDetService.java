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
 * 盘点明细功能的Service接口的定义清单
 */
public interface InventoryCheckDetService extends IService<InventoryCheckDet> {

    /**
     * 盘点明细的分页查询方法接口定义
     */
    public PagedResult<InventoryCheckDetDto> List(InventoryCheckDetPagedInput input) ;
    /**
     * 盘点明细的新增或者修改方法接口定义
     */
    public InventoryCheckDetDto CreateOrEdit(InventoryCheckDetDto input);

     /**
     * 获取盘点明细信息
     */
    public InventoryCheckDetDto Get(InventoryCheckDetPagedInput input);
 	 /**
     * 盘点明细删除
     */
    public void Delete(IdInput input);

    /**
     * 盘点明细批量删除
     */
    public void BatchDelete(IdsInput input);
  

}
