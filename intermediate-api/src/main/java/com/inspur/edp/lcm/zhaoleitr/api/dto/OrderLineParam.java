package com.inspur.edp.lcm.zhaoleitr.api.dto;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderLineParam {
    @JsonProperty("material_id")
    private Integer materialId;

    public void setMaterial_id(Integer m) {
        setMaterialId(m);
    }

    private BigDecimal quantity;
}
