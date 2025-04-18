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
 * Cargo查询模型
 */
@NoArgsConstructor
@Data
public class CargoPagedInput extends PagedInput {
    
    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 商品名称模糊查询条件
     */
  	 @JsonProperty("Name")
    private String Name;
    /**
     * 条码模糊查询条件
     */
  	 @JsonProperty("SKU")
    private String SKU;
    /**
     * 品牌模糊查询条件
     */
  	 @JsonProperty("Brand")
    private String Brand;
    /**
     * 规格型号模糊查询条件
     */
  	 @JsonProperty("Specification")
    private String Specification;
    /**
     * 单位模糊查询条件
     */
  	 @JsonProperty("Unit")
    private String Unit;
    /**
     * 最低库存预警模糊查询条件
     */
  	 @JsonProperty("MinStockAlert")
    private Double MinStockAlert;
    /**
     * 最高库存限制模糊查询条件
     */
  	 @JsonProperty("MaxStockLimit")
    private Double MaxStockLimit;
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
     * 商品分类
     */
  	 @JsonProperty("CargoTypeId")
    private Integer CargoTypeId;

}
