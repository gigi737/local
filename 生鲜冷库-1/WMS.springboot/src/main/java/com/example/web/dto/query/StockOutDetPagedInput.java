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
 * StockOutDet查询模型
 */
@NoArgsConstructor
@Data
public class StockOutDetPagedInput extends PagedInput {
    
    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
     /**
     * 出库单
     */
  	 @JsonProperty("StockOutId")
    private Integer StockOutId;
     /**
     * 商品
     */
  	 @JsonProperty("CargoId")
    private Integer CargoId;
     /**
     * 仓库
     */
  	 @JsonProperty("WarehouseId")
    private Integer WarehouseId;
     /**
     * 库位
     */
  	 @JsonProperty("WarehouseBinId")
    private Integer WarehouseBinId;

}
