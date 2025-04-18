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
 * 仓库类
 */
@Data
public class WarehouseDto extends BaseDto
{

    
     
    /**
     * 仓库名称
     */ 
    @JsonProperty("Name")
    private String Name;
    
     
    /**
     * 地址
     */ 
    @JsonProperty("Address")
    private String Address;
    
     
    /**
     * 城市
     */ 
    @JsonProperty("City")
    private String City;
    
     
    /**
     * 国家
     */ 
    @JsonProperty("Country")
    private String Country;
    
     
    /**
     * 联系人
     */ 
    @JsonProperty("ContactPerson")
    private String ContactPerson;
    
     
    /**
     * 面积
     */ 
    @JsonProperty("Area")
    private Double Area;      
    
     
    /**
     * 仓库类型
     */ 
    @JsonProperty("Type")
    private String Type;
    
     
    /**
     * 环境条件
     */ 
    @JsonProperty("EnvironmentalConditions")
    private String EnvironmentalConditions;
    
     
    /**
     * 联系电话
     */ 
    @JsonProperty("Phone")
    private String Phone;

 	 /**
     * 把仓库传输模型转换成仓库实体
     */
    public Warehouse MapToEntity(){
        Warehouse Warehouse= new Warehouse();
        try {
         BeanUtils.copyProperties(Warehouse,this);
          }
        catch (Exception e) {
         }
        return Warehouse;
    }

}
