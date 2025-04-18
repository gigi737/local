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
import java.util.HashMap;

import jakarta.servlet.http.HttpServletResponse;
/**
 * 库存记录功能的Service接口的定义清单
 */
public interface InventoryRecordService extends IService<InventoryRecord> {

    /**
     * 库存记录的分页查询方法接口定义
     */
    public PagedResult<InventoryRecordDto> List(InventoryRecordPagedInput input) ;
    /**
     * 库存记录的新增或者修改方法接口定义
     */
    public InventoryRecordDto CreateOrEdit(InventoryRecordDto input);
    /**
     * 缺货列表
     */
    @SneakyThrows
    PagedResult<CargoReplenishmentDto> GetCargoReplenishmentDtoList(InventoryRecordPagedInput input);

    /**
     * 获取库存记录信息
     */
    public InventoryRecordDto Get(InventoryRecordPagedInput input);
 	 /**
     * 库存记录删除
     */
    public void Delete(IdInput input);

    /**
     * 库存记录批量删除
     */
    public void BatchDelete(IdsInput input);

    /**
     * 数据统计
     *
     */
    HashMap<String, Object> GetDateCollect(InventoryRecordPagedInput input);
}
