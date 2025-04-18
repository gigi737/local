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
 * 商品类
 */
@Data
public class CargoDto extends BaseDto
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
     * 商品名称
     */ 
    @JsonProperty("Name")
    private String Name;
    
     
    /**
     * 条码
     */ 
    @JsonProperty("SKU")
    private String SKU;
    
     
    /**
     * 品牌
     */ 
    @JsonProperty("Brand")
    private String Brand;
    
     
    /**
     * 规格型号
     */ 
    @JsonProperty("Specification")
    private String Specification;
    
     
    /**
     * 单位
     */ 
    @JsonProperty("Unit")
    private String Unit;
    
     
    /**
     * 最低库存预警
     */ 
    @JsonProperty("MinStockAlert")
    private Double MinStockAlert;
    
     
    /**
     * 最高库存限制
     */ 
    @JsonProperty("MaxStockLimit")
    private Double MaxStockLimit;
    
     
    /**
     * 商品分类
     */ 
    @JsonProperty("CargoTypeId")
    private Integer CargoTypeId;          

     @JsonProperty("CargoTypeDto") 
    private CargoTypeDto CargoTypeDto;                        
   
     @JsonProperty("CustomerDto") 
    private CustomerDto CustomerDto;                        
   
     @JsonProperty("WarehouseDto") 
    private WarehouseDto WarehouseDto;                        
   
 	 /**
     * 把商品传输模型转换成商品实体
     */
    public Cargo MapToEntity(){
        Cargo Cargo= new Cargo();
        try {
         BeanUtils.copyProperties(Cargo,this);
          }
        catch (Exception e) {
         }
        return Cargo;
    }

}
