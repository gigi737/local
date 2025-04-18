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
 * 盘点单控制器
 */
@RestController()
@RequestMapping("/InventoryCheck")
public class InventoryCheckController {
    @Autowired()
    private InventoryCheckService InventoryCheckService;
    @Autowired()
    private InventoryCheckMapper InventoryCheckMapper;
    @Autowired
    private InventoryRuningRecordService InventoryRuningRecordService;

    /**
     * 盘点单分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<InventoryCheckDto> List(@RequestBody InventoryCheckPagedInput input) {
        return InventoryCheckService.List(input);
    }

    /**
     * 单个盘点单查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public InventoryCheckDto Get(@RequestBody InventoryCheckPagedInput input) {

        return InventoryCheckService.Get(input);
    }

    /**
     * 盘点单创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public InventoryCheckDto CreateOrEdit(@RequestBody InventoryCheckDto input) throws Exception {
        return InventoryCheckService.CreateOrEdit(input);
    }

    /**
     * 盘点单删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input) {
        InventoryCheckService.Delete(input);
    }

    /**
     * 盘点单批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input) {
        InventoryCheckService.BatchDelete(input);
    }

    /**
     * 盘点完成
     */
    @SneakyThrows
    @RequestMapping(value = "/Completed", method = RequestMethod.POST)
    public void Completed(@RequestBody InventoryCheckDto input) {
        InventoryCheckService.Completed(input);
    }

    /**
     * 盘点完成执行库存变动
     */
    @SneakyThrows
    @RequestMapping(value = "/CompletedStockChange", method = RequestMethod.POST)
    public void CompletedStockChange(@RequestBody InventoryCheckDto input) {
        InventoryCheckService.CompletedStockChange(input);
        InventoryRuningRecordService.InventoryCheckCompleted(input.getId());
    }

}
