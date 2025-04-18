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
 * 商品分类类
 */
@Data
public class CargoTypeDto extends BaseDto
{

    
     
    /**
     * 名称
     */ 
    @JsonProperty("Name")
    private String Name;
    
     
    /**
     * 仓库
     */ 
    @JsonProperty("WarehouseId")
    private Integer WarehouseId;          

     @JsonProperty("WarehouseDto") 
    private WarehouseDto WarehouseDto;                        
   
 	 /**
     * 把商品分类传输模型转换成商品分类实体
     */
    public CargoType MapToEntity(){
        CargoType CargoType= new CargoType();
        try {
         BeanUtils.copyProperties(CargoType,this);
          }
        catch (Exception e) {
         }
        return CargoType;
    }

}
