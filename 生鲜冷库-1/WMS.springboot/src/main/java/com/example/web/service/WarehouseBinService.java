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
 * 库位功能的Service接口的定义清单
 */
public interface WarehouseBinService extends IService<WarehouseBin> {

    /**
     * 库位的分页查询方法接口定义
     */
    public PagedResult<WarehouseBinDto> List(WarehouseBinPagedInput input) ;
    /**
     * 库位的新增或者修改方法接口定义
     */
    public WarehouseBinDto CreateOrEdit(WarehouseBinDto input);

     /**
     * 获取库位信息
     */
    public WarehouseBinDto Get(WarehouseBinPagedInput input);
 	 /**
     * 库位删除
     */
    public void Delete(IdInput input);

    /**
     * 库位批量删除
     */
    public void BatchDelete(IdsInput input);
  

}
