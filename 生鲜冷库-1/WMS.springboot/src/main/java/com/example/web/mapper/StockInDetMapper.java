package com.example.web.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.web.entity.*;
import org.apache.ibatis.annotations.Mapper;

/**
 * 入库明细表对应的Mapper
 */
@Mapper
public interface StockInDetMapper  extends BaseMapper<StockInDet> {

}
