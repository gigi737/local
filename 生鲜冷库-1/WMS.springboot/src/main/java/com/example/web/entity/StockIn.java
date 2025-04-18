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
 * 入库单表
 */
@Data
@TableName("`StockIn`")
public class StockIn extends BaseEntity {

      
  	  /**
     * 入库单号
     */  
    @JsonProperty("No")
    @TableField(value="No",updateStrategy = FieldStrategy.IGNORED)
    private String No;
      
    /**
     * 仓库
     */  
    @JsonProperty("WarehouseId")
    @TableField(value="WarehouseId",updateStrategy = FieldStrategy.IGNORED)
    private Integer WarehouseId;          
      
    /**
     * 货主
     */  
    @JsonProperty("CustomerId")
    @TableField(value="CustomerId",updateStrategy = FieldStrategy.IGNORED)
    private Integer CustomerId;          
      
    /**
     * 入库类型
     */  
    @JsonProperty("StockInType")
    @TableField(value="StockInType",updateStrategy = FieldStrategy.IGNORED)
    private Integer StockInType;          
      
    /**
     * 入库状态
     */  
    @JsonProperty("StockInStatus")
    @TableField(value="StockInStatus",updateStrategy = FieldStrategy.IGNORED)
    private Integer StockInStatus;          
      
    /**
     * 入库时间
     */  
    @JsonProperty("InTime")
    @TableField(value="InTime",updateStrategy = FieldStrategy.IGNORED)
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime InTime;             
      
  	  /**
     * 备注
     */  
    @JsonProperty("Remark")
    @TableField(value="Remark",updateStrategy = FieldStrategy.IGNORED)
    private String Remark;
      
    /**
     * 计划入库时间
     */  
    @JsonProperty("PlanInTime")
    @TableField(value="PlanInTime",updateStrategy = FieldStrategy.IGNORED)
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime PlanInTime;             
  
    /**
     * 把入库单实体转换成入库单传输模型
     */
    public StockInDto MapToDto() {
        StockInDto StockInDto = new StockInDto();
         try {
        BeanUtils.copyProperties(StockInDto,this);
          }
        catch (Exception e) {}
        return StockInDto;
    }

}
