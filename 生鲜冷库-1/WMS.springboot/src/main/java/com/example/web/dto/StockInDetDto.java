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
 * 入库明细类
 */
@Data
public class StockInDetDto extends BaseDto
{

    
     
    /**
     * 入库单
     */ 
    @JsonProperty("StockInId")
    private Integer StockInId;          
    
     
    /**
     * 库位
     */ 
    @JsonProperty("WarehouseBinId")
    private Integer WarehouseBinId;          
    
     
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
    
     
    /**
     * 仓库
     */ 
    @JsonProperty("WarehouseId")
    private Integer WarehouseId;          

     @JsonProperty("WarehouseDto") 
    private WarehouseDto WarehouseDto;                        
   
     @JsonProperty("StockInDto") 
    private StockInDto StockInDto;                        
   
     @JsonProperty("WarehouseBinDto") 
    private WarehouseBinDto WarehouseBinDto;                        
   
     @JsonProperty("CargoDto") 
    private CargoDto CargoDto;                        
   
 	 /**
     * 把入库明细传输模型转换成入库明细实体
     */
    public StockInDet MapToEntity(){
        StockInDet StockInDet= new StockInDet();
        try {
         BeanUtils.copyProperties(StockInDet,this);
          }
        catch (Exception e) {
         }
        return StockInDet;
    }

}
