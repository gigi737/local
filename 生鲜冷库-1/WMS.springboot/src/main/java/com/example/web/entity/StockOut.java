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
 * 出库单表
 */
@Data
@TableName("`StockOut`")
public class StockOut extends BaseEntity {

      
  	  /**
     * 单号
     */  
    @JsonProperty("No")
    @TableField(value="No",updateStrategy = FieldStrategy.IGNORED)
    private String No;
      
    /**
     * 货主
     */  
    @JsonProperty("CustomerId")
    @TableField(value="CustomerId",updateStrategy = FieldStrategy.IGNORED)
    private Integer CustomerId;          
      
    /**
     * 仓库
     */  
    @JsonProperty("WarehouseId")
    @TableField(value="WarehouseId",updateStrategy = FieldStrategy.IGNORED)
    private Integer WarehouseId;          
      
    /**
     * 出库时间
     */  
    @JsonProperty("OutTime")
    @TableField(value="OutTime",updateStrategy = FieldStrategy.IGNORED)
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime OutTime;             
      
    /**
     * 计划出库时间
     */  
    @JsonProperty("PlanOutTime")
    @TableField(value="PlanOutTime",updateStrategy = FieldStrategy.IGNORED)
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime PlanOutTime;             
      
  	  /**
     * 备注
     */  
    @JsonProperty("Remark")
    @TableField(value="Remark",updateStrategy = FieldStrategy.IGNORED)
    private String Remark;
      
    /**
     * 出库类型
     */  
    @JsonProperty("StockOutType")
    @TableField(value="StockOutType",updateStrategy = FieldStrategy.IGNORED)
    private Integer StockOutType;          
      
    /**
     * 出库状态
     */  
    @JsonProperty("StockOutStatus")
    @TableField(value="StockOutStatus",updateStrategy = FieldStrategy.IGNORED)
    private Integer StockOutStatus;          
  
    /**
     * 把出库单实体转换成出库单传输模型
     */
    public StockOutDto MapToDto() {
        StockOutDto StockOutDto = new StockOutDto();
         try {
        BeanUtils.copyProperties(StockOutDto,this);
          }
        catch (Exception e) {}
        return StockOutDto;
    }

}
