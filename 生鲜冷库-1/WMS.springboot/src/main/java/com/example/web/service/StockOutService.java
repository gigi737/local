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
 * 出库单功能的Service接口的定义清单
 */
public interface StockOutService extends IService<StockOut> {

    /**
     * 出库单的分页查询方法接口定义
     */
    public PagedResult<StockOutDto> List(StockOutPagedInput input) ;
    /**
     * 出库单的新增或者修改方法接口定义
     */
    public StockOutDto CreateOrEdit(StockOutDto input);

     /**
     * 获取出库单信息
     */
    public StockOutDto Get(StockOutPagedInput input);
    /**
     * 出库完成
     *
     */
    @SneakyThrows
    StockOutDto Complete(StockOutDto input);

    /**
     * 出库单删除
     */
    public void Delete(IdInput input);

    /**
     * 出库单批量删除
     */
    public void BatchDelete(IdsInput input);

    /**
     * 最近一年的出库单量
     */
    @SneakyThrows
    List<Object> GetStockOutByDayEchart(StockOutPagedInput input);
}
