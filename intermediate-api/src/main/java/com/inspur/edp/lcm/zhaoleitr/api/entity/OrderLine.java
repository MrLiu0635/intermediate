package com.inspur.edp.lcm.zhaoleitr.api.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "sale_order_line")
@Entity
@Data
@Accessors(chain = true)
public class OrderLine {
    @Id
    private String id;
    private Integer materialId;
    private String quantity;
    private String amount;
    private Integer orderId;
}
