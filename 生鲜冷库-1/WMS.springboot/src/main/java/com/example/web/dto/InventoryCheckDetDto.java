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
 * 盘点明细类
 */
@Data
public class InventoryCheckDetDto extends BaseDto
{

    
     
    /**
     * 盘点单
     */ 
    @JsonProperty("InventoryCheckId")
    private Integer InventoryCheckId;          
    
     
    /**
     * 库位
     */ 
    @JsonProperty("WarehouseBinId")
    private Integer WarehouseBinId;          
    
     
    /**
     * 仓库
     */ 
    @JsonProperty("WarehouseId")
    private Integer WarehouseId;          
    
     
    /**
     * 商品
     */ 
    @JsonProperty("CargoId")
    private Integer CargoId;          
    
     
    /**
     * 数量
     */ 
    @JsonProperty("Qty")
    private Double Qty;      

     @JsonProperty("CargoDto") 
    private CargoDto CargoDto;                        
   
     @JsonProperty("WarehouseBinDto") 
    private WarehouseBinDto WarehouseBinDto;                        
   
     @JsonProperty("InventoryCheckDto") 
    private InventoryCheckDto InventoryCheckDto;                        
   
     @JsonProperty("WarehouseDto") 
    private WarehouseDto WarehouseDto;                        
   
 	 /**
     * 把盘点明细传输模型转换成盘点明细实体
     */
    public InventoryCheckDet MapToEntity(){
        InventoryCheckDet InventoryCheckDet= new InventoryCheckDet();
        try {
         BeanUtils.copyProperties(InventoryCheckDet,this);
          }
        catch (Exception e) {
         }
        return InventoryCheckDet;
    }

}
