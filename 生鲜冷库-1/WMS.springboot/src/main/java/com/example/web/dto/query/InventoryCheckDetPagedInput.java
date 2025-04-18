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
 * InventoryCheckDet查询模型
 */
@NoArgsConstructor
@Data
public class InventoryCheckDetPagedInput extends PagedInput {
    
    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
     /**
     * 盘点单
     */
  	 @JsonProperty("InventoryCheckId")
    private Integer InventoryCheckId;
     /**
     * 库位
     */
  	 @JsonProperty("WarehouseBinId")
    private Integer WarehouseBinId;
     /**
     * 仓库
     */
  	 @JsonProperty("WarehouseId")
    private Integer WarehouseId;
     /**
     * 商品
     */
  	 @JsonProperty("CargoId")
    private Integer CargoId;

}
