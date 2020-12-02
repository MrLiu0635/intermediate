package com.inspur.edp.lcm.zhaoleitr.api.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity
@Data
public class Material {
	@Id
	private int id;

	private float quantity;
}
