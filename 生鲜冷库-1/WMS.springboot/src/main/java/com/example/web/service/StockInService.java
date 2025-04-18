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
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;
/**
 * 入库单功能的Service接口的定义清单
 */
public interface StockInService extends IService<StockIn> {

    /**
     * 入库单的分页查询方法接口定义
     */
    public PagedResult<StockInDto> List(StockInPagedInput input) ;
    /**
     * 入库单的新增或者修改方法接口定义
     */
    public StockInDto CreateOrEdit(StockInDto input);

     /**
     * 获取入库单信息
     */
    public StockInDto Get(StockInPagedInput input);
    /**
     * 入库单的完成
     */
    @SneakyThrows
    StockInDto Complete(StockInDto input);

    /**
     * 入库单删除
     */
    public void Delete(IdInput input);

    /**
     * 入库单批量删除
     */
    public void BatchDelete(IdsInput input);

    /**
     * 最近一年的入库单量
     *
     */
    @SneakyThrows
    List<Object> GetStockInByDayEchart(StockInPagedInput input);
}
