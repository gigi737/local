package com.example.web.dto.query;

import com.example.web.tools.dto.PagedInput;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.util.List;

/**
 * StockIn查询模型
 */
@NoArgsConstructor
@Data
public class StockInPagedInput extends PagedInput {
    
    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 入库单号模糊查询条件
     */
  	 @JsonProperty("No")
    private String No;
    /**
     * 备注模糊查询条件
     */
  	 @JsonProperty("Remark")
    private String Remark;
     /**
     * 仓库
     */
  	 @JsonProperty("WarehouseId")
    private Integer WarehouseId;
     /**
     * 货主
     */
  	 @JsonProperty("CustomerId")
    private Integer CustomerId;
     /**
     * 入库类型
     */
  	 @JsonProperty("StockInType")
    private Integer StockInType;
     /**
     * 入库状态
     */
  	 @JsonProperty("StockInStatus")
    private Integer StockInStatus;
    /**
     * 入库时间时间范围
     */
    @JsonProperty("InTimeRange")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<LocalDateTime> InTimeRange;
    /**
     * 计划入库时间时间范围
     */
    @JsonProperty("PlanInTimeRange")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<LocalDateTime> PlanInTimeRange;


    /**
     * 是否未完成的
     */
    @JsonProperty("IsNotComplted")
    private Boolean IsNotComplted;
}
