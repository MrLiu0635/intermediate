package com.inspur.edp.lcm.zhaoleitr.core.service;

import com.inspur.edp.lcm.zhaoleitr.api.entity.Material;
import com.inspur.edp.lcm.zhaoleitr.api.service.MaterialService;
import com.inspur.edp.lcm.zhaoleitr.core.repo.MaterialRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MaterialServiceImpl implements MaterialService{
	@Resource
	MaterialRepository repository;

	@Override
	public Material getMaterial(int id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public void save(Material material){
		repository.save(material);
	}

	@Override
	public void deleteMaterial(int id) {
		repository.deleteById(id);
	}
}
