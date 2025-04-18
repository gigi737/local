package com.example.web.dto;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
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
 * 库存流水记录类
 */
@Data
public class InventoryRuningRecordDto extends BaseDto
{

    
     
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
     * 货主
     */ 
    @JsonProperty("CustomerId")
    private Integer CustomerId;


    /**
     * 库位
     */
    @JsonProperty("WarehouseBinId")
    private Integer WarehouseBinId;

    /**
     * 库位
     */
    @JsonProperty("WarehouseBinDto")
    private WarehouseBinDto WarehouseBinDto;

    /**
     * 类型
     */ 
    @JsonProperty("Type")
    private String Type;
    
     
    /**
     * 关联单号
     */ 
    @JsonProperty("RelativeNo")
    private String RelativeNo;
    
     
    /**
     * 变动数量
     */ 
    @JsonProperty("Qty")
    private Double Qty;      

     @JsonProperty("WarehouseDto") 
    private WarehouseDto WarehouseDto;                        
   
     @JsonProperty("CargoDto") 
    private CargoDto CargoDto;                        
   
     @JsonProperty("CustomerDto") 
    private CustomerDto CustomerDto;                        
   
 	 /**
     * 把库存流水记录传输模型转换成库存流水记录实体
     */
    public InventoryRuningRecord MapToEntity(){
        InventoryRuningRecord InventoryRuningRecord= new InventoryRuningRecord();
        try {
         BeanUtils.copyProperties(InventoryRuningRecord,this);
          }
        catch (Exception e) {
         }
        return InventoryRuningRecord;
    }

}
