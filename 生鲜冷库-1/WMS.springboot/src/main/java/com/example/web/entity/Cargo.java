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
 * 商品表
 */
@Data
@TableName("`Cargo`")
public class Cargo extends BaseEntity {

      
    /**
     * 仓库
     */  
    @JsonProperty("WarehouseId")
    @TableField(value="WarehouseId",updateStrategy = FieldStrategy.IGNORED)
    private Integer WarehouseId;          
      
    /**
     * 货主
     */  
    @JsonProperty("CustomerId")
    @TableField(value="CustomerId",updateStrategy = FieldStrategy.IGNORED)
    private Integer CustomerId;          
      
  	  /**
     * 商品名称
     */  
    @JsonProperty("Name")
    @TableField(value="Name",updateStrategy = FieldStrategy.IGNORED)
    private String Name;
      
  	  /**
     * 条码
     */  
    @JsonProperty("SKU")
    @TableField(value="SKU",updateStrategy = FieldStrategy.IGNORED)
    private String SKU;
      
  	  /**
     * 品牌
     */  
    @JsonProperty("Brand")
    @TableField(value="Brand",updateStrategy = FieldStrategy.IGNORED)
    private String Brand;
      
  	  /**
     * 规格型号
     */  
    @JsonProperty("Specification")
    @TableField(value="Specification",updateStrategy = FieldStrategy.IGNORED)
    private String Specification;
      
  	  /**
     * 单位
     */  
    @JsonProperty("Unit")
    @TableField(value="Unit",updateStrategy = FieldStrategy.IGNORED)
    private String Unit;
      
  	  /**
     * 最低库存预警
     */  
    @JsonProperty("MinStockAlert")
    @TableField(value="MinStockAlert",updateStrategy = FieldStrategy.IGNORED)
    private Double MinStockAlert;
      
  	  /**
     * 最高库存限制
     */  
    @JsonProperty("MaxStockLimit")
    @TableField(value="MaxStockLimit",updateStrategy = FieldStrategy.IGNORED)
    private Double MaxStockLimit;
      
    /**
     * 商品分类
     */  
    @JsonProperty("CargoTypeId")
    @TableField(value="CargoTypeId",updateStrategy = FieldStrategy.IGNORED)
    private Integer CargoTypeId;          
  
    /**
     * 把商品实体转换成商品传输模型
     */
    public CargoDto MapToDto() {
        CargoDto CargoDto = new CargoDto();
         try {
        BeanUtils.copyProperties(CargoDto,this);
          }
        catch (Exception e) {}
        return CargoDto;
    }

}
