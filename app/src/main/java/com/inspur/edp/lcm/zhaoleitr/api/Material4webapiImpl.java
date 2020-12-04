package com.inspur.edp.lcm.zhaoleitr.api;

import com.inspur.edp.lcm.zhaoleitr.api.entity.Material;
import com.inspur.edp.lcm.zhaoleitr.api.service.MaterialService;
import com.inspur.edp.lcm.zhaoleitr.core.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
public class Material4webapiImpl {
	@Autowired
	MaterialService materialService;

	@Autowired
	RedisUtils redisUtils;

	@RequestMapping("/hello")
	public String getMaterial() {
		if (redisUtils.get("1") != null) {
			Material Material = (Material)redisUtils.get("1");
			return String.valueOf(Material.getQuantity());
		}
		Material material2 = materialService.getMaterial(1);
		log.info("add 1 to reids");
		redisUtils.set("1", material2);
		return "hello";
	}

	@PutMapping("/hello/save")
	public void updateMaterial(Material material) {
		materialService.save(material);
	}

	@PostMapping("/hello/save")
	public void createMaterial(Material material) {
		materialService.save(material);
	}

	@RequestMapping(path="hello",params = {"id","quantity"})
	public Material get(int id,String quantity) {
		return materialService.getMaterial(id);
	}

	@RequestMapping(path="hello/{id}")
	public Material getById(@PathVariable Integer id) {
		return materialService.getMaterial(id);
	}

	@DeleteMapping(path="hello/{id}")
	public void deleteMaterial(@PathVariable Integer id) {
		materialService.deleteMaterial(id);
	}

	@PostMapping("/hello/savejson")
	public void createMaterialWithJson(@RequestBody Material material) {
		materialService.save(material);
	}
}
