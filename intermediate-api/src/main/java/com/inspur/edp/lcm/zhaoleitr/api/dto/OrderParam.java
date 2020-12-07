package com.inspur.edp.lcm.zhaoleitr.api.dto;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.parser.JSONParser;
import lombok.Data;

import javax.persistence.Id;
import javax.ws.rs.FormParam;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Data
public class OrderParam {
    @Id
    private Integer id;

    @JsonProperty("document_type")
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

    @JsonProperty("biz_date")
    private Timestamp bizDate;

    public void setBiz_date(Timestamp t) {
        setBizDate(t);
    }

    private List<OrderLineParam> lines;

    private String linesStr;

    public void setSale_order_line(String ps) {
        setLinesStr(ps);
        return;
        /*
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<OrderLineParam> psl = mapper.readValue(ps, new TypeReference<List<OrderLineParam>>(){});
            setLines(psl);
        } catch (Exception e){
            throw new ParamErrorException();
        }
         */
    }
}
