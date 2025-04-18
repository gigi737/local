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
 * WarehouseRelativeUser查询模型
 */
@NoArgsConstructor
@Data
public class WarehouseRelativeUserPagedInput extends PagedInput {
    
    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
     /**
     * 用户
     */
  	 @JsonProperty("UserId")
    private Integer UserId;
     /**
     * 仓库
     */
  	 @JsonProperty("WarehouseId")
    private Integer WarehouseId;

}
