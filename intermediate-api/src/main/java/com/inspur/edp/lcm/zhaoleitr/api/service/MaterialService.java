package com.inspur.edp.lcm.zhaoleitr.api.service;

import com.inspur.edp.lcm.zhaoleitr.api.entity.Material;

public interface MaterialService {
	Material getMaterial(int id);

	void save(Material material);

	void deleteMaterial(int id);
}
