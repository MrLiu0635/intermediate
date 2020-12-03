package com.inspur.edp.lcm.zhaoleitr.api;

import com.inspur.edp.lcm.zhaoleitr.api.entity.Material;
import com.inspur.edp.lcm.zhaoleitr.api.service.MaterialService;
import com.inspur.edp.lcm.zhaoleitr.core.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@Slf4j
public class Material4webapiImpl {
	@Autowired
	MaterialService materialService;

	@Autowired
	RedisUtils redisUtils;

	@ResponseBody
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


}
