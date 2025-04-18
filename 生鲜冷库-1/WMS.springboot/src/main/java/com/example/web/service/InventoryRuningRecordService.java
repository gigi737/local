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
 * 库存流水记录功能的Service接口的定义清单
 */
public interface InventoryRuningRecordService extends IService<InventoryRuningRecord> {

    /**
     * 库存流水记录的分页查询方法接口定义
     */
    public PagedResult<InventoryRuningRecordDto> List(InventoryRuningRecordPagedInput input) ;
    /**
     * 库存流水记录的新增或者修改方法接口定义
     */
    public InventoryRuningRecordDto CreateOrEdit(InventoryRuningRecordDto input);

     /**
     * 获取库存流水记录信息
     */
    public InventoryRuningRecordDto Get(InventoryRuningRecordPagedInput input);
 	 /**
     * 库存流水记录删除
     */
    public void Delete(IdInput input);

    /**
     * 库存流水记录批量删除
     */
    public void BatchDelete(IdsInput input);

    /**
     * 库存入库单完成
     */
    void StockInCompleted(Integer stockInId);
    /**
     * 检查是否有库存可以出库
     *
     * @param stockOutId
     */
    void CheckIsCanStockOut(Integer stockOutId);
    /**
     * 库存出库单完成
     */
    void StockOutCompleted(Integer stockOutId);
    /**
     * 盘点执行库存变动
     */
    void InventoryCheckCompleted(Integer inventoryCheckId);
}
