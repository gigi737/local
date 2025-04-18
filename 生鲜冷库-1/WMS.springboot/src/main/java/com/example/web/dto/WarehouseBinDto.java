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
 * 库位类
 */
@Data
public class WarehouseBinDto extends BaseDto
{

    
     
    /**
     * 所属仓库
     */ 
    @JsonProperty("WarehouseId")
    private Integer WarehouseId;          
    
     
    /**
     * 库位编码
     */ 
    @JsonProperty("BinCode")
    private String BinCode;
    
     
    /**
     * 类型
     */ 
    @JsonProperty("Type")
    private String Type;
    
     
    /**
     * 层
     */ 
    @JsonProperty("Level")
    private String Level;

     @JsonProperty("WarehouseDto") 
    private WarehouseDto WarehouseDto;                        
   
 	 /**
     * 把库位传输模型转换成库位实体
     */
    public WarehouseBin MapToEntity(){
        WarehouseBin WarehouseBin= new WarehouseBin();
        try {
         BeanUtils.copyProperties(WarehouseBin,this);
          }
        catch (Exception e) {
         }
        return WarehouseBin;
    }

}
