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
 * StockOut查询模型
 */
@NoArgsConstructor
@Data
public class StockOutPagedInput extends PagedInput {
    
    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 单号模糊查询条件
     */
  	 @JsonProperty("No")
    private String No;
    /**
     * 备注模糊查询条件
     */
  	 @JsonProperty("Remark")
    private String Remark;
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
     * 出库类型
     */
  	 @JsonProperty("StockOutType")
    private Integer StockOutType;
     /**
     * 出库状态
     */
  	 @JsonProperty("StockOutStatus")
    private Integer StockOutStatus;
    /**
     * 出库时间时间范围
     */
    @JsonProperty("OutTimeRange")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<LocalDateTime> OutTimeRange;
    /**
     * 计划出库时间时间范围
     */
    @JsonProperty("PlanOutTimeRange")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<LocalDateTime> PlanOutTimeRange;

    /**
     * 是否未完成的
     */
    @JsonProperty("IsNotComplted")
    private Boolean IsNotComplted;

}
