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
 * WarehouseBin查询模型
 */
@NoArgsConstructor
@Data
public class WarehouseBinPagedInput extends PagedInput {
    
    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 库位编码模糊查询条件
     */
  	 @JsonProperty("BinCode")
    private String BinCode;
    /**
     * 类型模糊查询条件
     */
  	 @JsonProperty("Type")
    private String Type;
    /**
     * 层模糊查询条件
     */
  	 @JsonProperty("Level")
    private String Level;
     /**
     * 所属仓库
     */
  	 @JsonProperty("WarehouseId")
    private Integer WarehouseId;

}
