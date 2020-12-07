package com.inspur.edp.lcm.zhaoleitr.api.service;

import com.inspur.edp.lcm.zhaoleitr.api.dto.OrderParam;
import com.inspur.edp.lcm.zhaoleitr.api.dto.OrderRes;
import com.inspur.edp.lcm.zhaoleitr.api.dto.Res;
import com.inspur.edp.lcm.zhaoleitr.api.dto.SearchParam;
import com.inspur.edp.lcm.zhaoleitr.api.entity.Material;

import javax.transaction.Transactional;
import java.util.List;

public interface MaterialService {
	List<Material> getMaterial();

	Material getMaterial(int id);

	void save(Material material);

	void deleteMaterial(int id);

	OrderRes saveOrder(OrderParam param);

	OrderRes updateOrder(OrderParam param);

	List<OrderRes> search(SearchParam param);
}
