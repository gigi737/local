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
 * Warehouse查询模型
 */
@NoArgsConstructor
@Data
public class WarehousePagedInput extends PagedInput {
    
    /**
     * Id主键
     */
    @JsonProperty("Id")
    private Integer Id;
    /**
     * 仓库名称模糊查询条件
     */
  	 @JsonProperty("Name")
    private String Name;
    /**
     * 地址模糊查询条件
     */
  	 @JsonProperty("Address")
    private String Address;
    /**
     * 城市模糊查询条件
     */
  	 @JsonProperty("City")
    private String City;
    /**
     * 国家模糊查询条件
     */
  	 @JsonProperty("Country")
    private String Country;
    /**
     * 联系人模糊查询条件
     */
  	 @JsonProperty("ContactPerson")
    private String ContactPerson;
    /**
     * 仓库类型模糊查询条件
     */
  	 @JsonProperty("Type")
    private String Type;
    /**
     * 环境条件模糊查询条件
     */
  	 @JsonProperty("EnvironmentalConditions")
    private String EnvironmentalConditions;
    /**
     * 联系电话模糊查询条件
     */
  	 @JsonProperty("Phone")
    private String Phone;

}
