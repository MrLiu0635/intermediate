package com.inspur.edp.lcm.zhaoleitr.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StockPutParam {
    @JsonProperty("material_id")
    private Integer materialId;

    private Float quantity;
}
