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
 * 盘点单类
 */
@Data
public class InventoryCheckDto extends BaseDto
{

    
     
    /**
     * 单号
     */ 
    @JsonProperty("No")
    private String No;
    
     
    /**
     * 盘点人
     */ 
    @JsonProperty("CheckUserId")
    private Integer CheckUserId;          
    
     
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
     * 盘点状态
     */ 
    @JsonProperty("InventoryCheckStatus")
    private Integer InventoryCheckStatus;

    public String getInventoryCheckStatusFormat() {
        return InventoryCheckStatusEnum.GetEnum(InventoryCheckStatus).toString();
    }
     
    /**
     * 是否执行库存调整
     */ 
    @JsonProperty("IsExcuteInventoryChange")
    private Boolean IsExcuteInventoryChange;          
    
     
    /**
     * 起始盘点时间
     */  
    @JsonProperty("BeginCheckTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime BeginCheckTime;  
    /**
     * 截至盘点时间
     */  
    @JsonProperty("EndCheckTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    private LocalDateTime EndCheckTime; 

     @JsonProperty("CheckUserDto") 
    private AppUserDto CheckUserDto;                        
   
     @JsonProperty("WarehouseDto") 
    private WarehouseDto WarehouseDto;                        
   
     @JsonProperty("CustomerDto") 
    private CustomerDto CustomerDto;                        
   
 	 /**
     * 把盘点单传输模型转换成盘点单实体
     */
    public InventoryCheck MapToEntity(){
        InventoryCheck InventoryCheck= new InventoryCheck();
        try {
         BeanUtils.copyProperties(InventoryCheck,this);
          }
        catch (Exception e) {
         }
        return InventoryCheck;
    }

}
