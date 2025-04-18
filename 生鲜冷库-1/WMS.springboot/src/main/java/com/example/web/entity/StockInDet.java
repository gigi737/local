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
 * 入库明细表
 */
@Data
@TableName("`StockInDet`")
public class StockInDet extends BaseEntity {

      
    /**
     * 入库单
     */  
    @JsonProperty("StockInId")
    @TableField(value="StockInId",updateStrategy = FieldStrategy.IGNORED)
    private Integer StockInId;          
      
    /**
     * 库位
     */  
    @JsonProperty("WarehouseBinId")
    @TableField(value="WarehouseBinId",updateStrategy = FieldStrategy.IGNORED)
    private Integer WarehouseBinId;          
      
    /**
     * 商品
     */  
    @JsonProperty("CargoId")
    @TableField(value="CargoId",updateStrategy = FieldStrategy.IGNORED)
    private Integer CargoId;          
      
    /**
     * 数量
     */  
    @JsonProperty("Qty")
    @TableField(value="Qty",updateStrategy = FieldStrategy.IGNORED)
    private Double Qty;      
      
    /**
     * 仓库
     */  
    @JsonProperty("WarehouseId")
    @TableField(value="WarehouseId",updateStrategy = FieldStrategy.IGNORED)
    private Integer WarehouseId;          
  
    /**
     * 把入库明细实体转换成入库明细传输模型
     */
    public StockInDetDto MapToDto() {
        StockInDetDto StockInDetDto = new StockInDetDto();
         try {
        BeanUtils.copyProperties(StockInDetDto,this);
          }
        catch (Exception e) {}
        return StockInDetDto;
    }

}
