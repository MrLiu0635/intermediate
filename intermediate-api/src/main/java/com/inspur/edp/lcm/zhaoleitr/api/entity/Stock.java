package com.inspur.edp.lcm.zhaoleitr.api.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity
@Data
public class Stock {
    @Id
    private Integer id;

    private Integer materialId;

    // 0.00000
    private String quantity;
}
