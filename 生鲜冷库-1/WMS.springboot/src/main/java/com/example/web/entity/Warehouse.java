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
 * 仓库表
 */
@Data
@TableName("`Warehouse`")
public class Warehouse extends BaseEntity {

      
  	  /**
     * 仓库名称
     */  
    @JsonProperty("Name")
    @TableField(value="Name",updateStrategy = FieldStrategy.IGNORED)
    private String Name;
      
  	  /**
     * 地址
     */  
    @JsonProperty("Address")
    @TableField(value="Address",updateStrategy = FieldStrategy.IGNORED)
    private String Address;
      
  	  /**
     * 城市
     */  
    @JsonProperty("City")
    @TableField(value="City",updateStrategy = FieldStrategy.IGNORED)
    private String City;
      
  	  /**
     * 国家
     */  
    @JsonProperty("Country")
    @TableField(value="Country",updateStrategy = FieldStrategy.IGNORED)
    private String Country;
      
  	  /**
     * 联系人
     */  
    @JsonProperty("ContactPerson")
    @TableField(value="ContactPerson",updateStrategy = FieldStrategy.IGNORED)
    private String ContactPerson;
      
    /**
     * 面积
     */  
    @JsonProperty("Area")
    @TableField(value="Area",updateStrategy = FieldStrategy.IGNORED)
    private Double Area;      
      
  	  /**
     * 仓库类型
     */  
    @JsonProperty("Type")
    @TableField(value="Type",updateStrategy = FieldStrategy.IGNORED)
    private String Type;
      
  	  /**
     * 环境条件
     */  
    @JsonProperty("EnvironmentalConditions")
    @TableField(value="EnvironmentalConditions",updateStrategy = FieldStrategy.IGNORED)
    private String EnvironmentalConditions;
      
  	  /**
     * 联系电话
     */  
    @JsonProperty("Phone")
    @TableField(value="Phone",updateStrategy = FieldStrategy.IGNORED)
    private String Phone;
  
    /**
     * 把仓库实体转换成仓库传输模型
     */
    public WarehouseDto MapToDto() {
        WarehouseDto WarehouseDto = new WarehouseDto();
         try {
        BeanUtils.copyProperties(WarehouseDto,this);
          }
        catch (Exception e) {}
        return WarehouseDto;
    }

}
