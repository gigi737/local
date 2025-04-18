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
 * 仓库用户类
 */
@Data
public class WarehouseRelativeUserDto extends BaseDto
{

    
     
    /**
     * 用户
     */ 
    @JsonProperty("UserId")
    private Integer UserId;          
    
     
    /**
     * 仓库
     */ 
    @JsonProperty("WarehouseId")
    private Integer WarehouseId;          

     @JsonProperty("UserDto") 
    private AppUserDto UserDto;                        
   
     @JsonProperty("WarehouseDto") 
    private WarehouseDto WarehouseDto;                        
   
 	 /**
     * 把仓库用户传输模型转换成仓库用户实体
     */
    public WarehouseRelativeUser MapToEntity(){
        WarehouseRelativeUser WarehouseRelativeUser= new WarehouseRelativeUser();
        try {
         BeanUtils.copyProperties(WarehouseRelativeUser,this);
          }
        catch (Exception e) {
         }
        return WarehouseRelativeUser;
    }

}
