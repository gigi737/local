package com.example.web.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.*;
import com.example.web.dto.query.*;
import com.example.web.entity.*;
import com.example.web.tools.dto.*;
import com.example.web.enums.*;
import java.lang.reflect.InvocationTargetException;

import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.IOException;
import jakarta.servlet.http.HttpServletResponse;
/**
 * 盘点单功能的Service接口的定义清单
 */
public interface InventoryCheckService extends IService<InventoryCheck> {

    /**
     * 盘点单的分页查询方法接口定义
     */
    public PagedResult<InventoryCheckDto> List(InventoryCheckPagedInput input) ;
    /**
     * 盘点单的新增或者修改方法接口定义
     */
    public InventoryCheckDto CreateOrEdit(InventoryCheckDto input);

     /**
     * 获取盘点单信息
     */
    public InventoryCheckDto Get(InventoryCheckPagedInput input);
    /**
     * 盘点完成
     */
    @SneakyThrows
    void Completed(InventoryCheckDto input);
    /**
     * 盘点完成执行库存变动
     */
    @SneakyThrows
    void CompletedStockChange(InventoryCheckDto input);

    /**
     * 盘点单删除
     */
    public void Delete(IdInput input);

    /**
     * 盘点单批量删除
     */
    public void BatchDelete(IdsInput input);
  

}
