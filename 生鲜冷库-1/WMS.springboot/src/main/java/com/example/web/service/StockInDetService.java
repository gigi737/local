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
 * 入库明细功能的Service接口的定义清单
 */
public interface StockInDetService extends IService<StockInDet> {

    /**
     * 入库明细的分页查询方法接口定义
     */
    public PagedResult<StockInDetDto> List(StockInDetPagedInput input) ;
    /**
     * 入库明细的新增或者修改方法接口定义
     */
    public StockInDetDto CreateOrEdit(StockInDetDto input);

     /**
     * 获取入库明细信息
     */
    public StockInDetDto Get(StockInDetPagedInput input);
 	 /**
     * 入库明细删除
     */
    public void Delete(IdInput input);

    /**
     * 入库明细批量删除
     */
    public void BatchDelete(IdsInput input);
  

}
