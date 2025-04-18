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
 * 库存记录类
 */
@Data
public class InventoryRecordDto extends BaseDto
{

    
     
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
     * 商品
     */ 
    @JsonProperty("CargoId")
    private Integer CargoId;          
    
     
    /**
     * 库位
     */ 
    @JsonProperty("WarehouseBinId")
    private Integer WarehouseBinId;          
    
     
    /**
     * 库存数量
     */ 
    @JsonProperty("StockQty")
    private Double StockQty;      

     @JsonProperty("WarehouseBinDto") 
    private WarehouseBinDto WarehouseBinDto;                        
   
     @JsonProperty("WarehouseDto") 
    private WarehouseDto WarehouseDto;                        
   
     @JsonProperty("CustomerDto") 
    private CustomerDto CustomerDto;                        
   
     @JsonProperty("CargoDto") 
    private CargoDto CargoDto;                        
   
 	 /**
     * 把库存记录传输模型转换成库存记录实体
     */
    public InventoryRecord MapToEntity(){
        InventoryRecord InventoryRecord= new InventoryRecord();
        try {
         BeanUtils.copyProperties(InventoryRecord,this);
          }
        catch (Exception e) {
         }
        return InventoryRecord;
    }

}
