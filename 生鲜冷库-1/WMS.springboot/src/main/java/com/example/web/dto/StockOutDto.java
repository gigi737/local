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
 * 出库单类
 */
@Data
public class StockOutDto extends BaseDto
{

    
     
    /**
     * 单号
     */ 
    @JsonProperty("No")
    private String No;
    
     
    /**
     * 货主
     */ 
    @JsonProperty("CustomerId")
    private Integer CustomerId;          
    
     
    /**
     * 仓库
     */ 
    @JsonProperty("WarehouseId")
    private Integer WarehouseId;          
    
     
    /**
     * 出库时间
     */ 
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("OutTime")
    private LocalDateTime OutTime;             
    
     
    /**
     * 计划出库时间
     */ 
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("PlanOutTime")
    private LocalDateTime PlanOutTime;             
    
     
    /**
     * 备注
     */ 
    @JsonProperty("Remark")
    private String Remark;
    
     
    /**
     * 出库类型
     */ 
    @JsonProperty("StockOutType")
    private Integer StockOutType;    
    
    public String getStockOutTypeFormat() {
        return StockOutTypeEnum.GetEnum(StockOutType).toString();
    }
    
    private String StockOutTypeFormat;

    @JsonProperty("EmergencyRank")
    private Integer EmergencyRank;

    @JsonProperty("EmergencyRankFormat")
    public String getEmergencyRankFormat() {
        return EmergencyRankEnum.GetEnum(EmergencyRank).toString();
    }

    /**
     * 剩余秒
     * @return
     */
    @JsonProperty("RemindSecond")
    private int RemindSecond;
     
    /**
     * 出库状态
     */ 
    @JsonProperty("StockOutStatus")
    private Integer StockOutStatus;    
    
    public String getStockOutStatusFormat() {
        return StockOutStatusEnum.GetEnum(StockOutStatus).toString();
    }
    
    private String StockOutStatusFormat;
    

     @JsonProperty("WarehouseDto") 
    private WarehouseDto WarehouseDto;                        
   
     @JsonProperty("CustomerDto") 
    private CustomerDto CustomerDto;                        
   
 	 /**
     * 把出库单传输模型转换成出库单实体
     */
    public StockOut MapToEntity(){
        StockOut StockOut= new StockOut();
        try {
         BeanUtils.copyProperties(StockOut,this);
          }
        catch (Exception e) {
         }
        return StockOut;
    }

}
