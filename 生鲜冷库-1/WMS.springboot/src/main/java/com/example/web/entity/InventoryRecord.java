package com.example.web.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.poi.hpsf.Decimal;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.Data;

import java.time.LocalDateTime;

import com.example.web.dto.*;
import com.baomidou.mybatisplus.annotation.FieldStrategy;

/**
 * 库存记录表
 */
@Data
@TableName("`InventoryRecord`")
public class InventoryRecord extends BaseEntity {


    /**
     * 仓库
     */
    @JsonProperty("WarehouseId")
    @TableField(value = "WarehouseId", updateStrategy = FieldStrategy.IGNORED)
    private Integer WarehouseId;

    /**
     * 货主
     */
    @JsonProperty("CustomerId")
    @TableField(value = "CustomerId", updateStrategy = FieldStrategy.IGNORED)
    private Integer CustomerId;

    /**
     * 商品
     */
    @JsonProperty("CargoId")
    @TableField(value = "CargoId", updateStrategy = FieldStrategy.IGNORED)
    private Integer CargoId;

    /**
     * 库位
     */
    @JsonProperty("WarehouseBinId")
    @TableField(value = "WarehouseBinId", updateStrategy = FieldStrategy.IGNORED)
    private Integer WarehouseBinId;

    /**
     * 库存数量
     */
    @JsonProperty("StockQty")
    @TableField(value = "StockQty", updateStrategy = FieldStrategy.IGNORED)
    private Double StockQty;

    /**
     * 把库存记录实体转换成库存记录传输模型
     */
    public InventoryRecordDto MapToDto() {
        InventoryRecordDto InventoryRecordDto = new InventoryRecordDto();
        try {
            BeanUtils.copyProperties(InventoryRecordDto, this);
        } catch (Exception e) {

        }
        return InventoryRecordDto;
    }

}
