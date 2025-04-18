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
 * 出库单控制器
 */
@RestController()
@RequestMapping("/StockOut")
public class StockOutController {
    @Autowired()
    private StockOutService StockOutService;
    @Autowired()
    private StockOutMapper StockOutMapper;

    @Autowired
    private InventoryRuningRecordService InventoryRuningRecordService;

    /**
     * 出库单分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<StockOutDto> List(@RequestBody StockOutPagedInput input) {
        return StockOutService.List(input);
    }

    /**
     * 单个出库单查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public StockOutDto Get(@RequestBody StockOutPagedInput input) {

        return StockOutService.Get(input);
    }

    /**
     * 出库单创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public StockOutDto CreateOrEdit(@RequestBody StockOutDto input) throws Exception {
        return StockOutService.CreateOrEdit(input);
    }

    /**
     * 出库单删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input) {
        StockOutService.Delete(input);
    }

    /**
     * 出库单批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input) {
        StockOutService.BatchDelete(input);
    }

    /**
     * 出库完成
     */
    @SneakyThrows
    @RequestMapping(value = "/Complete", method = RequestMethod.POST)
    public StockOutDto Complete(@RequestBody StockOutDto input) {

        InventoryRuningRecordService.CheckIsCanStockOut(input.getId());
        var rs = StockOutService.Complete(input);
        InventoryRuningRecordService.StockOutCompleted(input.getId());

        return rs;
    }

    /**
     * 最近一年的出库单量
     */
    @RequestMapping(value = "/GetStockOutByDayEchart", method = RequestMethod.POST)
    @SneakyThrows
    public List<Object> GetStockOutByDayEchart(@RequestBody StockOutPagedInput input) {
        return StockOutService.GetStockOutByDayEchart(input);
    }

}
