package com.example.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CargoReplenishmentDto {



    /**
     * 货主
     */
    @JsonProperty("CustomerId")
    private Integer CustomerId;


    /**
     * 商品
     */
    @JsonProperty("CargoId")
    private Integer CargoId;



    /**
     * 当前库存
     */
    @JsonProperty("CurrentQty")
    private Double CurrentQty;

    /**
     * 缺货数量
     */
    @JsonProperty("NeedQty")
    private Double NeedQty;






    @JsonProperty("CustomerDto")
    private CustomerDto CustomerDto;

    @JsonProperty("CargoDto")
    private CargoDto CargoDto;

}
