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
 * 入库明细控制器
 */
@RestController()
@RequestMapping("/StockInDet")
public class StockInDetController {
    @Autowired()
    private  StockInDetService StockInDetService;
    @Autowired()
    private StockInDetMapper StockInDetMapper;
    /**
     * 入库明细分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<StockInDetDto> List(@RequestBody StockInDetPagedInput input)  {
        return StockInDetService.List(input);
    }
     /**
     * 单个入库明细查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public StockInDetDto Get(@RequestBody StockInDetPagedInput input) {

        return StockInDetService.Get(input);
    }
  
    /**
     * 入库明细创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public StockInDetDto CreateOrEdit(@RequestBody StockInDetDto input) throws Exception {
        return StockInDetService.CreateOrEdit(input);
    }
    /**
     * 入库明细删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input)
    {
        StockInDetService.Delete(input);
    }

    /**
     * 入库明细批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input)
    {
        StockInDetService.BatchDelete(input);
    }
  

 
}
