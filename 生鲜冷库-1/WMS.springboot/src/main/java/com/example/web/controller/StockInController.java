package com.example.web.controller;

import com.example.web.SysConst;
import com.example.web.dto.*;
import com.example.web.dto.query.*;
import com.example.web.entity.*;
import com.example.web.mapper.*;
import com.example.web.service.*;
import com.example.web.tools.dto.*;
import com.example.web.tools.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import lombok.SneakyThrows;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletResponse;

/**
 * 入库单控制器
 */
@RestController()
@RequestMapping("/StockIn")
public class StockInController {
    @Autowired()
    private StockInService StockInService;
    @Autowired()
    private StockInMapper StockInMapper;

    @Autowired()
    private InventoryRuningRecordService InventoryRuningRecordService;

    /**
     * 入库单分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<StockInDto> List(@RequestBody StockInPagedInput input) {
        return StockInService.List(input);
    }

    /**
     * 单个入库单查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public StockInDto Get(@RequestBody StockInPagedInput input) {

        return StockInService.Get(input);
    }

    /**
     * 入库单创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public StockInDto CreateOrEdit(@RequestBody StockInDto input) throws Exception {
        return StockInService.CreateOrEdit(input);
    }

    /**
     * 入库单删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input) {
        StockInService.Delete(input);
    }

    /**
     * 入库单批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input) {
        StockInService.BatchDelete(input);
    }


    /**
     * 入库单的完成
     */
    @RequestMapping(value = "/Complete", method = RequestMethod.POST)
    public StockInDto Complete(@RequestBody StockInDto input) throws Exception {
        var rs = StockInService.Complete(input);
        InventoryRuningRecordService.StockInCompleted(rs.getId());
        return rs;
    }

    /**
     * 最近一年的入库单量
     */
    @RequestMapping(value = "/GetStockInByDayEchart", method = RequestMethod.POST)
    @SneakyThrows
    public List<Object> GetStockInByDayEchart(@RequestBody StockInPagedInput input) {
        return StockInService.GetStockInByDayEchart(input);
    }
}
