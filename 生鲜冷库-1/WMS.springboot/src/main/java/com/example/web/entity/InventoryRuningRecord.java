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
 * 库存流水记录表
 */
@Data
@TableName("`InventoryRuningRecord`")
public class InventoryRuningRecord extends BaseEntity {

      
    /**
     * 仓库
     */  
    @JsonProperty("WarehouseId")
    @TableField(value="WarehouseId",updateStrategy = FieldStrategy.IGNORED)
    private Integer WarehouseId;


      
    /**
     * 商品
     */  
    @JsonProperty("CargoId")
    @TableField(value="CargoId",updateStrategy = FieldStrategy.IGNORED)
    private Integer CargoId;          
      
    /**
     * 货主
     */  
    @JsonProperty("CustomerId")
    @TableField(value="CustomerId",updateStrategy = FieldStrategy.IGNORED)
    private Integer CustomerId;


    /**
     * 库位
     */
    @JsonProperty("WarehouseBinId")
    @TableField(value="WarehouseBinId",updateStrategy = FieldStrategy.IGNORED)
    private Integer WarehouseBinId;


    /**
     * 类型
     */  
    @JsonProperty("Type")
    @TableField(value="Type",updateStrategy = FieldStrategy.IGNORED)
    private String Type;
      
  	  /**
     * 关联单号
     */  
    @JsonProperty("RelativeNo")
    @TableField(value="RelativeNo",updateStrategy = FieldStrategy.IGNORED)
    private String RelativeNo;
      
    /**
     * 变动数量
     */  
    @JsonProperty("Qty")
    @TableField(value="Qty",updateStrategy = FieldStrategy.IGNORED)
    private Double Qty;      
  
    /**
     * 把库存流水记录实体转换成库存流水记录传输模型
     */
    public InventoryRuningRecordDto MapToDto() {
        InventoryRuningRecordDto InventoryRuningRecordDto = new InventoryRuningRecordDto();
         try {
        BeanUtils.copyProperties(InventoryRuningRecordDto,this);
          }
        catch (Exception e) {}
        return InventoryRuningRecordDto;
    }

}
