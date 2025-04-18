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
 * 货主类
 */
@Data
public class CustomerDto extends BaseDto
{

    
     
    /**
     * 货主名称
     */ 
    @JsonProperty("OwnerName")
    private String OwnerName;
    
     
    /**
     * 联系人
     */ 
    @JsonProperty("ContactPerson")
    private String ContactPerson;
    
     
    /**
     * 联系电话
     */ 
    @JsonProperty("Phone")
    private String Phone;
    
     
    /**
     * 电子邮箱
     */ 
    @JsonProperty("Email")
    private String Email;
    
     
    /**
     * 地址
     */ 
    @JsonProperty("Address")
    private String Address;
    
     
    /**
     * 仓库
     */ 
    @JsonProperty("WarehouseId")
    private Integer WarehouseId;          

     @JsonProperty("WarehouseDto") 
    private WarehouseDto WarehouseDto;                        
   
 	 /**
     * 把货主传输模型转换成货主实体
     */
    public Customer MapToEntity(){
        Customer Customer= new Customer();
        try {
         BeanUtils.copyProperties(Customer,this);
          }
        catch (Exception e) {
         }
        return Customer;
    }

}
