package com.example.web.entity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.poi.hpsf.Decimal;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.BeanUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.sql.Date;
import java.sql.Timestamp;
import lombok.Data;
import java.time.LocalDateTime;
import com.example.web.dto.*;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
/**
 * 货主表
 */
@Data
@TableName("`Customer`")
public class Customer extends BaseEntity {

      
  	  /**
     * 货主名称
     */  
    @JsonProperty("OwnerName")
    @TableField(value="OwnerName",updateStrategy = FieldStrategy.IGNORED)
    private String OwnerName;
      
  	  /**
     * 联系人
     */  
    @JsonProperty("ContactPerson")
    @TableField(value="ContactPerson",updateStrategy = FieldStrategy.IGNORED)
    private String ContactPerson;
      
  	  /**
     * 联系电话
     */  
    @JsonProperty("Phone")
    @TableField(value="Phone",updateStrategy = FieldStrategy.IGNORED)
    private String Phone;
      
  	  /**
     * 电子邮箱
     */  
    @JsonProperty("Email")
    @TableField(value="Email",updateStrategy = FieldStrategy.IGNORED)
    private String Email;
      
  	  /**
     * 地址
     */  
    @JsonProperty("Address")
    @TableField(value="Address",updateStrategy = FieldStrategy.IGNORED)
    private String Address;
      
    /**
     * 仓库
     */  
    @JsonProperty("WarehouseId")
    @TableField(value="WarehouseId",updateStrategy = FieldStrategy.IGNORED)
    private Integer WarehouseId;          
  
    /**
     * 把货主实体转换成货主传输模型
     */
    public CustomerDto MapToDto() {
        CustomerDto CustomerDto = new CustomerDto();
         try {
        BeanUtils.copyProperties(CustomerDto,this);
          }
        catch (Exception e) {}
        return CustomerDto;
    }

}
