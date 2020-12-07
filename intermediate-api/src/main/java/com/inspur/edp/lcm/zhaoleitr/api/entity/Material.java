package com.inspur.edp.lcm.zhaoleitr.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity
@Data
public class Material {
	@Id
	private Integer id;

	private String name;

	private String unit;

	@JsonProperty("unit_price")
	private String unitPrice;
}
