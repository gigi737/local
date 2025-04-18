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
 * 出库明细类
 */
@Data
public class StockOutDetDto extends BaseDto
{

    
     
    /**
     * 出库单
     */ 
    @JsonProperty("StockOutId")
    private Integer StockOutId;          
    
     
    /**
     * 商品
     */ 
    @JsonProperty("CargoId")
    private Integer CargoId;          
    
     
    /**
     * 仓库
     */ 
    @JsonProperty("WarehouseId")
    private Integer WarehouseId;          
    
     
    /**
     * 数量
     */ 
    @JsonProperty("Qty")
    private Double Qty;      
    
     
    /**
     * 库位
     */ 
    @JsonProperty("WarehouseBinId")
    private Integer WarehouseBinId;          

     @JsonProperty("CargoDto") 
    private CargoDto CargoDto;                        
   
     @JsonProperty("WarehouseDto") 
    private WarehouseDto WarehouseDto;                        
   
     @JsonProperty("WarehouseBinDto") 
    private WarehouseBinDto WarehouseBinDto;                        
   
     @JsonProperty("StockOutDto") 
    private StockOutDto StockOutDto;                        
   
 	 /**
     * 把出库明细传输模型转换成出库明细实体
     */
    public StockOutDet MapToEntity(){
        StockOutDet StockOutDet= new StockOutDet();
        try {
         BeanUtils.copyProperties(StockOutDet,this);
          }
        catch (Exception e) {
         }
        return StockOutDet;
    }

}
