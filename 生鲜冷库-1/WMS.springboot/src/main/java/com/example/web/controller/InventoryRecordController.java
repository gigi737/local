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
 * 库存记录控制器
 */
@RestController()
@RequestMapping("/InventoryRecord")
public class InventoryRecordController {
    @Autowired()
    private InventoryRecordService InventoryRecordService;
    @Autowired()
    private InventoryRecordMapper InventoryRecordMapper;

    /**
     * 库存记录分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<InventoryRecordDto> List(@RequestBody InventoryRecordPagedInput input) {
        return InventoryRecordService.List(input);
    }

    /**
     * 单个库存记录查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public InventoryRecordDto Get(@RequestBody InventoryRecordPagedInput input) {

        return InventoryRecordService.Get(input);
    }

    /**
     * 库存记录创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public InventoryRecordDto CreateOrEdit(@RequestBody InventoryRecordDto input) throws Exception {
        return InventoryRecordService.CreateOrEdit(input);
    }

    /**
     * 库存记录删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input) {
        InventoryRecordService.Delete(input);
    }

    /**
     * 库存记录批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input) {
        InventoryRecordService.BatchDelete(input);
    }


    /**
     * 缺货列表
     */
    @RequestMapping(value = "/GetCargoReplenishmentDtoList", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<CargoReplenishmentDto> GetCargoReplenishmentDtoList(@RequestBody InventoryRecordPagedInput input) {
        return InventoryRecordService.GetCargoReplenishmentDtoList(input);
    }

    /**
     * 数据统计
     */
    @RequestMapping(value = "/GetDateCollect", method = RequestMethod.POST)
    @SneakyThrows
    public HashMap<String, Object> GetDateCollect(@RequestBody InventoryRecordPagedInput input) {
        return InventoryRecordService.GetDateCollect(input);
    }

}
