package com.inspur.edp.lcm.zhaoleitr.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class OrderRes {
    private Integer id;

    @JsonProperty("document_type")
    private String documentType;

    @JsonProperty("sal_employee")
    private String salEmployee;

    private String customer;

    @JsonProperty("biz_date")
    private Timestamp bizDate;

    private String number;

    @JsonProperty("amount_total")
    private String amountTotal;

    @JsonProperty("sale_order_line_ids")
    private List<OrderLineRes> lines;
}
