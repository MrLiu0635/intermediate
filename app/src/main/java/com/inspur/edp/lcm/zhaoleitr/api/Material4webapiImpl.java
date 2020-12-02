package com.inspur.edp.lcm.zhaoleitr.api;

import com.inspur.edp.lcm.zhaoleitr.api.entity.Material;
import com.inspur.edp.lcm.zhaoleitr.api.service.Material4WebApiService;
import com.inspur.edp.lcm.zhaoleitr.api.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class Material4webapiImpl {
	@Autowired
	MaterialService materialService;

	@ResponseBody
	@RequestMapping("/hello")
	public String getMaterial() {
		materialService.getMaterial(1);
		return "hello";
	}


}
