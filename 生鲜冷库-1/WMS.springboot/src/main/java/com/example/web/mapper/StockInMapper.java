package com.example.web.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.web.entity.*;
import org.apache.ibatis.annotations.Mapper;

/**
 * 入库单表对应的Mapper
 */
@Mapper
public interface StockInMapper  extends BaseMapper<StockIn> {

}
