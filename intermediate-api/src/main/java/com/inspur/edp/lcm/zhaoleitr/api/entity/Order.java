package com.inspur.edp.lcm.zhaoleitr.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Table(name = "sale_order")
@Entity
@Data
public class Order {
    @Id
    private Integer id;

    // 0 = common
    private Integer documentType;

    private String number;

    private Timestamp bizDate;

    private String salEmployee;

    private String customer;

    private String amountTotal;
}
