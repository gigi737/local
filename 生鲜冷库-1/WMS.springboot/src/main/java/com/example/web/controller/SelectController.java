package com.example.web.controller;
import com.example.web.enums.*;
import com.example.web.tools.dto.PagedResult;
import com.example.web.tools.dto.SelectResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/Select")
public class SelectController {

   @RequestMapping(value = "/RoleType",method = RequestMethod.POST)
    public PagedResult<SelectResult> RoleType(){

        List<SelectResult> rs = Arrays.stream(RoleTypeEnum.values()).map(n -> new SelectResult(n.toString(), n.name(), n.index() + "", "")).toList();
        return PagedResult.GetInstance(rs,rs.stream().count());
    }
     /**
     *盘点状态枚举接口
     */
    @RequestMapping(value = "/InventoryCheckStatusEnum", method = RequestMethod.POST)
    public PagedResult<SelectResult> InventoryCheckStatusEnum() {

       var rs=Arrays.stream(InventoryCheckStatusEnum.values()).map(n->new SelectResult(n.toString(),n.name(),Integer.toString(n.index()),"")).toList();
       return PagedResult.GetInstance(rs,rs.stream().count());
    }

     /**
     *入库类型枚举接口
     */
    @RequestMapping(value = "/StockInTypeEnum", method = RequestMethod.POST)
    public PagedResult<SelectResult> StockInTypeEnum() {

       var rs=Arrays.stream(StockInTypeEnum.values()).map(n->new SelectResult(n.toString(),n.name(),Integer.toString(n.index()),"")).toList();
       return PagedResult.GetInstance(rs,rs.stream().count());
    }

     /**
     *出库状态枚举接口
     */
    @RequestMapping(value = "/StockOutStatusEnum", method = RequestMethod.POST)
    public PagedResult<SelectResult> StockOutStatusEnum() {

       var rs=Arrays.stream(StockOutStatusEnum.values()).map(n->new SelectResult(n.toString(),n.name(),Integer.toString(n.index()),"")).toList();
       return PagedResult.GetInstance(rs,rs.stream().count());
    }

     /**
     *出库类型枚举接口
     */
    @RequestMapping(value = "/StockOutTypeEnum", method = RequestMethod.POST)
    public PagedResult<SelectResult> StockOutTypeEnum() {

       var rs=Arrays.stream(StockOutTypeEnum.values()).map(n->new SelectResult(n.toString(),n.name(),Integer.toString(n.index()),"")).toList();
       return PagedResult.GetInstance(rs,rs.stream().count());
    }

     /**
     *入库状态枚举接口
     */
    @RequestMapping(value = "/StockInStatusEnum", method = RequestMethod.POST)
    public PagedResult<SelectResult> StockInStatusEnum() {

       var rs=Arrays.stream(StockInStatusEnum.values()).map(n->new SelectResult(n.toString(),n.name(),Integer.toString(n.index()),"")).toList();
       return PagedResult.GetInstance(rs,rs.stream().count());
    }



}
