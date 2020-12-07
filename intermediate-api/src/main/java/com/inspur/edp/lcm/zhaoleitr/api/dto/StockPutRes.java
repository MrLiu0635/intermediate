package com.inspur.edp.lcm.zhaoleitr.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StockPutRes {
    private int id;

    @JsonProperty("material_id_id")
    private int materialId;

    private String quantity;

    @JsonProperty("material_name")
    private String materialName;

    @JsonProperty("material_unit")
    private String materialUnit;

    @JsonProperty("material_unit_price")
    private String materialUnitPrice;
}
