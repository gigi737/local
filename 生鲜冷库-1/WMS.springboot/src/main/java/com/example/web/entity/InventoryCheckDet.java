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
 * 盘点明细表
 */
@Data
@TableName("`InventoryCheckDet`")
public class InventoryCheckDet extends BaseEntity {

      
    /**
     * 盘点单
     */  
    @JsonProperty("InventoryCheckId")
    @TableField(value="InventoryCheckId",updateStrategy = FieldStrategy.IGNORED)
    private Integer InventoryCheckId;          
      
    /**
     * 库位
     */  
    @JsonProperty("WarehouseBinId")
    @TableField(value="WarehouseBinId",updateStrategy = FieldStrategy.IGNORED)
    private Integer WarehouseBinId;          
      
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
     * 数量
     */  
    @JsonProperty("Qty")
    @TableField(value="Qty",updateStrategy = FieldStrategy.IGNORED)
    private Double Qty;      
  
    /**
     * 把盘点明细实体转换成盘点明细传输模型
     */
    public InventoryCheckDetDto MapToDto() {
        InventoryCheckDetDto InventoryCheckDetDto = new InventoryCheckDetDto();
         try {
        BeanUtils.copyProperties(InventoryCheckDetDto,this);
          }
        catch (Exception e) {}
        return InventoryCheckDetDto;
    }

}
