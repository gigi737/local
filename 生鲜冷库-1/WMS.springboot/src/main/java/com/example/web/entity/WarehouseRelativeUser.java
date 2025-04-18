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
 * 仓库用户表
 */
@Data
@TableName("`WarehouseRelativeUser`")
public class WarehouseRelativeUser extends BaseEntity {

      
    /**
     * 用户
     */  
    @JsonProperty("UserId")
    @TableField(value="UserId",updateStrategy = FieldStrategy.IGNORED)
    private Integer UserId;          
      
    /**
     * 仓库
     */  
    @JsonProperty("WarehouseId")
    @TableField(value="WarehouseId",updateStrategy = FieldStrategy.IGNORED)
    private Integer WarehouseId;          
  
    /**
     * 把仓库用户实体转换成仓库用户传输模型
     */
    public WarehouseRelativeUserDto MapToDto() {
        WarehouseRelativeUserDto WarehouseRelativeUserDto = new WarehouseRelativeUserDto();
         try {
        BeanUtils.copyProperties(WarehouseRelativeUserDto,this);
          }
        catch (Exception e) {}
        return WarehouseRelativeUserDto;
    }

}
