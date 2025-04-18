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
 * 库位表
 */
@Data
@TableName("`WarehouseBin`")
public class WarehouseBin extends BaseEntity {

      
    /**
     * 所属仓库
     */  
    @JsonProperty("WarehouseId")
    @TableField(value="WarehouseId",updateStrategy = FieldStrategy.IGNORED)
    private Integer WarehouseId;          
      
  	  /**
     * 库位编码
     */  
    @JsonProperty("BinCode")
    @TableField(value="BinCode",updateStrategy = FieldStrategy.IGNORED)
    private String BinCode;
      
  	  /**
     * 类型
     */  
    @JsonProperty("Type")
    @TableField(value="Type",updateStrategy = FieldStrategy.IGNORED)
    private String Type;
      
  	  /**
     * 层
     */  
    @JsonProperty("Level")
    @TableField(value="Level",updateStrategy = FieldStrategy.IGNORED)
    private String Level;
  
    /**
     * 把库位实体转换成库位传输模型
     */
    public WarehouseBinDto MapToDto() {
        WarehouseBinDto WarehouseBinDto = new WarehouseBinDto();
         try {
        BeanUtils.copyProperties(WarehouseBinDto,this);
          }
        catch (Exception e) {}
        return WarehouseBinDto;
    }

}
