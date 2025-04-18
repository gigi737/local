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
 * InventoryCheck查询模型
 */
@NoArgsConstructor
@Data
public class InventoryCheckPagedInput extends PagedInput {
    
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
     * 盘点人
     */
  	 @JsonProperty("CheckUserId")
    private Integer CheckUserId;
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
     * 是否执行库存调整
     */
  	 @JsonProperty("IsExcuteInventoryChange")
    private Boolean IsExcuteInventoryChange;
    /**
     * 盘点时间时间范围
     */
    @JsonProperty("CheckTimeRange")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private List<LocalDateTime> CheckTimeRange;

}
