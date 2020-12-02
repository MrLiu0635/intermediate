package com.inspur.edp.lcm.zhaoleitr.core.service;

import com.inspur.edp.lcm.zhaoleitr.api.entity.Material;
import com.inspur.edp.lcm.zhaoleitr.api.service.MaterialService;
import com.inspur.edp.lcm.zhaoleitr.core.repo.MaterialRepository;


public class MaterialServiceImpl implements MaterialService{
	MaterialRepository repository;
	public MaterialServiceImpl(MaterialRepository repository){
		this.repository=repository;
	}

	@Override
	public Material getMaterial(int id) {
		return repository.findById(id).orElse(null);
	}
}
