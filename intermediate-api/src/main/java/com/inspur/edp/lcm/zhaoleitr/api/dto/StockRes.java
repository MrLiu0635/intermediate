package com.inspur.edp.lcm.zhaoleitr.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StockRes {
    private int id;

    @JsonProperty("material_id_id")
    private int materialId;

    // 0.00000
    private String quantity;

    @JsonProperty("material_name")
    private String materialName;

    @JsonProperty("material_unit")
    private String materialUnit;

    @JsonProperty("material_unit_price")
    private String materialUnitPrice;
}
