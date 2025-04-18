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
 * InventoryRuningRecord查询模型
 */
@NoArgsConstructor
@Data
public class InventoryRuningRecordPagedInput extends PagedInput {


    /**
     * 库位
     */
    @JsonProperty("WarehouseBinId")
    private Integer WarehouseBinId;
    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 类型模糊查询条件
     */
  	 @JsonProperty("Type")
    private String Type;
    /**
     * 关联单号模糊查询条件
     */
  	 @JsonProperty("RelativeNo")
    private String RelativeNo;
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
     /**
     * 货主
     */
  	 @JsonProperty("CustomerId")
    private Integer CustomerId;

}
