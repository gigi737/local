package com.example.web.dto;

import com.example.web.enums.*;
import com.example.web.tools.dto.BaseDto;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.sql.Date;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.example.web.entity.*;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 入库单类
 */
@Data
public class StockInDto extends BaseDto {


    /**
     * 入库单号
     */
    @JsonProperty("No")
    private String No;


    /**
     * 仓库
     */
    @JsonProperty("WarehouseId")
    private Integer WarehouseId;


    /**
     * 货主
     */
    @JsonProperty("CustomerId")
    private Integer CustomerId;


    /**
     * 入库类型
     */
    @JsonProperty("StockInType")
    private Integer StockInType;

    public String getStockInTypeFormat() {
        return StockInTypeEnum.GetEnum(StockInType).toString();
    }

    private String StockInTypeFormat;


    /**
     * 入库状态
     */
    @JsonProperty("StockInStatus")
    private Integer StockInStatus;

    public String getStockInStatusFormat() {
        return StockInStatusEnum.GetEnum(StockInStatus).toString();
    }

    private String StockInStatusFormat;


    /**
     * 入库时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("InTime")
    private LocalDateTime InTime;


    /**
     * 备注
     */
    @JsonProperty("Remark")
    private String Remark;


    /**
     * 计划入库时间
     */
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("PlanInTime")
    private LocalDateTime PlanInTime;

    @JsonProperty("WarehouseDto")
    private WarehouseDto WarehouseDto;

    @JsonProperty("CustomerDto")
    private CustomerDto CustomerDto;

    @JsonProperty("EmergencyRank")
    private Integer EmergencyRank;


    @JsonProperty("EmergencyRankFormat")
    public String getEmergencyRankFormat() {
        return EmergencyRankEnum.GetEnum(EmergencyRank).toString();
    }

    /**
     * 剩余秒
     * @return
     */
    @JsonProperty("RemindSecond")
    private int RemindSecond;

    /**
     * 把入库单传输模型转换成入库单实体
     */
    public StockIn MapToEntity() {
        StockIn StockIn = new StockIn();
        try {
            BeanUtils.copyProperties(StockIn, this);
        } catch (Exception e) {
        }
        return StockIn;
    }

}
