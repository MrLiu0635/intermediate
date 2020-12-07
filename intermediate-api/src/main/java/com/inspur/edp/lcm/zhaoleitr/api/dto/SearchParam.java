package com.inspur.edp.lcm.zhaoleitr.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
public class SearchParam {

    private Integer number;

    @JsonProperty("service")
    private String documentType;
    public void setDocument_type(String v) {
        setDocumentType(v);
    }

    @JsonProperty("sal_employee")
    private String salEmployee;
    public void setSal_employee(String v) {
        setSalEmployee(v);
    }

    private String customer;

    @JsonProperty("from_date")
    private Timestamp fromDate;
    public void setFrom_date(Timestamp t) {
        setFromDate(t);
    }

    @JsonProperty("to_date")
    private Timestamp toDate;
    public void setTo_date(Timestamp t) {
        setToDate(t);
    }

    private BigDecimal from_total_amount;
    private BigDecimal to_total_amount;

    private String material_ids;
}
