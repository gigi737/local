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
 * 盘点单表
 */
@Data
@TableName("`InventoryCheck`")
public class InventoryCheck extends BaseEntity {

      
  	  /**
     * 单号
     */  
    @JsonProperty("No")
    @TableField(value="No",updateStrategy = FieldStrategy.IGNORED)
    private String No;
      
    /**
     * 盘点人
     */  
    @JsonProperty("CheckUserId")
    @TableField(value="CheckUserId",updateStrategy = FieldStrategy.IGNORED)
    private Integer CheckUserId;          
      
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
     * 盘点状态
     */  
    @JsonProperty("InventoryCheckStatus")
    @TableField(value="InventoryCheckStatus",updateStrategy = FieldStrategy.IGNORED)
    private Integer InventoryCheckStatus;          
      
    /**
     * 是否执行库存调整
     */  
    @JsonProperty("IsExcuteInventoryChange")
    @TableField(value="IsExcuteInventoryChange",updateStrategy = FieldStrategy.IGNORED)
    private Boolean IsExcuteInventoryChange;          
      
    /**
     * 起始盘点时间
     */  
    @JsonProperty("BeginCheckTime")
    @TableField(value="BeginCheckTime",updateStrategy = FieldStrategy.IGNORED)
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime BeginCheckTime;  
    /**
     * 截至盘点时间
     */  
    @JsonProperty("EndCheckTime")
    @TableField(value="EndCheckTime",updateStrategy = FieldStrategy.IGNORED)
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime EndCheckTime; 
  
    /**
     * 把盘点单实体转换成盘点单传输模型
     */
    public InventoryCheckDto MapToDto() {
        InventoryCheckDto InventoryCheckDto = new InventoryCheckDto();
         try {
        BeanUtils.copyProperties(InventoryCheckDto,this);
          }
        catch (Exception e) {}
        return InventoryCheckDto;
    }

}
