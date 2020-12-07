package com.inspur.edp.lcm.zhaoleitr.api;

import com.inspur.edp.lcm.zhaoleitr.api.dto.*;
import com.inspur.edp.lcm.zhaoleitr.api.entity.Material;
import com.inspur.edp.lcm.zhaoleitr.api.service.MaterialService;
import com.inspur.edp.lcm.zhaoleitr.api.service.StockService;
import com.inspur.edp.lcm.zhaoleitr.core.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;


@RestController
@Slf4j
public class Material4webapiImpl {
	@Autowired
	MaterialService materialService;

	@Autowired
	StockService stockService;

	@Autowired
	RedisUtils redisUtils;

	@GetMapping("/hello")
	public String hello() {
		return "00042528-刘钊";
	}


	@GetMapping("/material")
	public MaterialListRes getMaterialList() {
		MaterialListRes materialListRes = new MaterialListRes();
		materialListRes.setRes(materialService.getMaterial());
		return materialListRes;
	}

	@GetMapping("/stock")
	public StockListRes getStockList() {
		return stockService.getStockList();
	}

	@PutMapping("/stock")
	public ResponseEntity<Res<StockPutRes>> updateStock(@RequestBody StockPutParam param) {
		try {
			Res<StockPutRes> stockPutResRes = new Res<>(stockService.update(param));
			return new ResponseEntity<>(stockPutResRes, HttpStatus.OK);
		} catch (ParamErrorException e) {
			e.printStackTrace();
			final ResponseEntity responseEntity = new ResponseEntity<>("参数异常", HttpStatus.BAD_REQUEST);
			return responseEntity;
		} catch (BingfaException e) {
			e.printStackTrace();
			final ResponseEntity responseEntity = new ResponseEntity<>("当前有其他用户在更新库存信息，请稍后重试，此时数据不更新到数据库中", HttpStatus.FORBIDDEN);
			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			final ResponseEntity responseEntity = new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
			return responseEntity;
		}

	}

//		if (redisUtils.get("1") != null) {
//			Material Material = (Material)redisUtils.get("1");
//			return String.valueOf(Material.getQuantity());
//		}
//		Material material2 = materialService.getMaterial(1);
//		log.info("add 1 to reids");
//		redisUtils.set("1", material2);
//		return "hello";
	@PutMapping("/sale_order")
	public ResponseEntity<Res<OrderRes>> putOrder(OrderParam param) {
		try {
			Res<OrderRes> stockPutResRes = new Res<>(
					materialService.updateOrder(param));
			return new ResponseEntity<>(stockPutResRes, HttpStatus.OK);
		} catch (ParamErrorException e) {
			e.printStackTrace();
			final ResponseEntity responseEntity = new ResponseEntity<>("参数异常", HttpStatus.BAD_REQUEST);
			return responseEntity;
		} catch (BingfaException e) {
			e.printStackTrace();
			final ResponseEntity responseEntity = new ResponseEntity<>("当前有其他用户在更新库存信息，请稍后重试，此时数据不更新到数据库中", HttpStatus.FORBIDDEN);
			return responseEntity;
		} catch (NumOverException e) {
			e.printStackTrace();
			final ResponseEntity responseEntity = new ResponseEntity<>("商品" + e.getObj().toString() + "库存不足，请稍后重试", HttpStatus.FORBIDDEN);
			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			final ResponseEntity responseEntity = new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
			return responseEntity;
		}
	}

	@PostMapping("/sale_order")
	public ResponseEntity<Res<OrderRes>> createOrder(OrderParam param) {
		try {
			Res<OrderRes> stockPutResRes = new Res<>(
					materialService.saveOrder(param));
			return new ResponseEntity<>(stockPutResRes, HttpStatus.OK);
		} catch (ParamErrorException e) {
			e.printStackTrace();
			final ResponseEntity responseEntity = new ResponseEntity<>("参数异常", HttpStatus.BAD_REQUEST);
			return responseEntity;
		} catch (BingfaException e) {
			e.printStackTrace();
			final ResponseEntity responseEntity = new ResponseEntity<>("当前有其他用户在更新库存信息，请稍后重试，此时数据不更新到数据库中", HttpStatus.FORBIDDEN);
			return responseEntity;
		} catch (NumOverException e) {
			e.printStackTrace();
			final ResponseEntity responseEntity = new ResponseEntity<>("商品" + e.getObj().toString() + "库存不足，请稍后重试", HttpStatus.FORBIDDEN);
			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			final ResponseEntity responseEntity = new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
			return responseEntity;
		}
	}

	@PostMapping("/search_sale_order")
	public ResponseEntity<ListRes<OrderRes>> searchOrder(SearchParam param) {
		try {
			ListRes<OrderRes> stockPutResRes = new ListRes<>(
					materialService.search(param));
			return new ResponseEntity<>(stockPutResRes, HttpStatus.OK);
		} catch (ParamErrorException e) {
			e.printStackTrace();
			final ResponseEntity responseEntity = new ResponseEntity<>("参数异常", HttpStatus.BAD_REQUEST);
			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
			final ResponseEntity responseEntity = new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
			return responseEntity;
		}
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
