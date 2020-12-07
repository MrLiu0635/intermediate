package com.inspur.edp.lcm.zhaoleitr.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OrderLineRes {
    @JsonProperty("material_id")
    private Integer materialId;
    @JsonProperty("material_name")
    private String materialName;
    private String quantity;
    private String amount;
}
