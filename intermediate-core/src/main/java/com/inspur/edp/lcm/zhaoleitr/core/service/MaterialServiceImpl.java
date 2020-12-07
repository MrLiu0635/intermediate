package com.inspur.edp.lcm.zhaoleitr.core.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspur.edp.lcm.zhaoleitr.api.dto.*;
import com.inspur.edp.lcm.zhaoleitr.api.entity.Material;
import com.inspur.edp.lcm.zhaoleitr.api.entity.Order;
import com.inspur.edp.lcm.zhaoleitr.api.entity.OrderLine;
import com.inspur.edp.lcm.zhaoleitr.api.entity.Stock;
import com.inspur.edp.lcm.zhaoleitr.api.service.MaterialService;
import com.inspur.edp.lcm.zhaoleitr.core.repo.MaterialRepository;
import com.inspur.edp.lcm.zhaoleitr.core.repo.OrderLineRepository;
import com.inspur.edp.lcm.zhaoleitr.core.repo.OrderRespository;
import com.inspur.edp.lcm.zhaoleitr.core.repo.StockRepository;
import com.inspur.edp.lcm.zhaoleitr.core.utils.AbstractConverter;
import com.inspur.edp.lcm.zhaoleitr.core.utils.RedisUtils;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class MaterialServiceImpl implements MaterialService{
	@Resource
	MaterialRepository materialRepository;

	@Resource
	StockRepository stockRepository;

	@Autowired
	RedisUtils redisUtils;

	@Resource
	OrderRespository orderRespository;

	@Resource
	OrderLineRepository orderLineRepository;

	private String getDocu(int a) {
		if (a == 1) {
			return "service_order";
		} else if (a == 0) {
			return "common_order";
		} else {
			throw new RuntimeException("document error");
		}
	}

	private int getDocuInt(String a) {
		if (StringUtils.isEmpty(a)) {
			throw new ParamErrorException(a);
		}
		if ("common_order".equals(a)) {
			return 0;
		} else if ("service_order".equals(a)) {
			return 1;
		} else {
			throw new ParamErrorException(a);
		}
	}

	@Override
	public List<OrderRes> search(SearchParam param) {

		List<Order> all = orderRespository.findAll();// findAllByNumberLike('%' + param.getNumber().toString() + '%');
		List<OrderRes> orderRes1 = new ArrayList<>();
		if(CollectionUtils.isEmpty(all) ){
			return orderRes1;
		}

		if (Objects.isNull(param)) {
			List<OrderRes> res = new ArrayList<>();
			for (Order order:all) {
				List<OrderLine> lines = orderLineRepository.findAllByOrderId(order.getId());
				OrderRes orderRes = getOrderRes(order, lines);
				res.add(orderRes);
			}
			return res;
		}

		if (param.getNumber() != null ) {
			if (param.getNumber() < 1) {
				throw new ParamErrorException(param);
			} else {
				all = all.stream().filter(item->item.getNumber().contains(param.getNumber().toString())).collect(Collectors.toList());
			}
		}

		if(CollectionUtils.isEmpty(all) ){
			return orderRes1;
		}

		if (!StringUtils.isEmpty(param.getSalEmployee())) {
			all = all.stream().filter(item->item.getSalEmployee().contains(param.getSalEmployee())).collect(Collectors.toList());
		}

		if(CollectionUtils.isEmpty(all) ){
			return orderRes1;
		}

		if (!StringUtils.isEmpty(param.getCustomer())) {
			all = all.stream().filter(item->item.getCustomer().contains(param.getCustomer())).collect(Collectors.toList());
		}

		if(CollectionUtils.isEmpty(all) ){
			return orderRes1;
		}

		if (!StringUtils.isEmpty(param.getDocumentType())) {
			int docuInt = getDocuInt(param.getDocumentType());
			all = all.stream().filter(item->item.getDocumentType() == docuInt).collect(Collectors.toList());
		}

		if(CollectionUtils.isEmpty(all) ){
			return orderRes1;
		}

		if (!StringUtils.isEmpty(param.getMaterial_ids())) {
			try {
				List<Integer> psl = Arrays.stream(param.getMaterial_ids().split(",")).map(c -> Integer.valueOf(c)).collect(Collectors.toList());
				all = all.stream().filter(item -> psl.contains(item.getId())).collect(Collectors.toList());
			} catch (Exception e){
				throw new ParamErrorException();
			}
		}

		if(CollectionUtils.isEmpty(all) ){
			return orderRes1;
		}

		if (param.getFromDate() != null) {
			all = all.stream().filter(item -> item.getBizDate().compareTo(param.getFromDate()) >= 0 ).collect(Collectors.toList());
		}

		if(CollectionUtils.isEmpty(all) ){
			return orderRes1;
		}

		if (param.getToDate() != null) {
			all = all.stream().filter(item -> param.getToDate().compareTo(item.getBizDate()) > 0 ).collect(Collectors.toList());
		}

		if (param.getFromDate() != null && param.getToDate() != null && param.getFromDate().compareTo(param.getToDate()) > 0) {
			throw new ParamErrorException();
		}

		if(CollectionUtils.isEmpty(all) ){
			return orderRes1;
		}

		if (param.getFrom_total_amount() != null && param.getTo_total_amount() != null && param.getFrom_total_amount().compareTo(param.getTo_total_amount()) > 0) {
			throw new ParamErrorException();
		}

		if (param.getFrom_total_amount() != null) {
			all = all.stream().filter(item -> new BigDecimal(item.getAmountTotal()).compareTo(param.getFrom_total_amount()) >= 0 ).collect(Collectors.toList());
		}

		if(CollectionUtils.isEmpty(all) ){
			return orderRes1;
		}

		if (param.getTo_total_amount() != null) {
			all = all.stream().filter(item -> param.getTo_total_amount().compareTo(new BigDecimal(item.getAmountTotal())) > 0 ).collect(Collectors.toList());
		}

		if(CollectionUtils.isEmpty(all) ){
			return orderRes1;
		}

		List<OrderRes> res = new ArrayList<>();
		for (Order order:all) {
			List<OrderLine> lines = orderLineRepository.findAllByOrderId(order.getId());
			OrderRes orderRes = getOrderRes(order, lines);
			res.add(orderRes);
		}
		return res;
	}

	private OrderRes getOrderRes(Order order, List<OrderLine> lines) {
		OrderRes orderRes = AbstractConverter.toTarget(order, OrderRes.class);
		orderRes.setDocumentType(getDocu(order.getDocumentType()));
		if (!CollectionUtils.isEmpty(lines)) {

			List<OrderLineRes> orderLineRes = AbstractConverter.toTargetList(lines, OrderLineRes.class);
			orderLineRes.forEach(lineRes -> {
				Optional<Material> materialOps = materialRepository.findById(lineRes.getMaterialId());
				lineRes.setMaterialName(materialOps.isPresent()?materialOps.get().getName():"");
			});
			orderRes.setLines(orderLineRes);
		}
		return orderRes;
	}

	private void val(OrderParam param) {
		if (Objects.isNull(param)
		|| StringUtils.isEmpty(param.getDocumentType())
		|| StringUtils.isEmpty(param.getSalEmployee())
		|| StringUtils.isEmpty(param.getCustomer())
		|| param.getBizDate() == null
		|| StringUtils.isEmpty(param.getLinesStr())) {
			throw new ParamErrorException(param);
		}
	}
	private void valLine(OrderLineParam lineParam) {
		if (Objects.isNull(lineParam) || StringUtils.isEmpty(lineParam.getMaterialId()) || lineParam.getQuantity() == null) {
			throw new ParamErrorException(lineParam);
		}
	}
	@Override
	@Transactional
	public OrderRes saveOrder(OrderParam param) {
		val(param);

		try {
			if (!redisUtils.lock(RedisUtils.key_for_createorder)) {
				throw new BingfaException();
			}
			Order order = AbstractConverter.toTarget(param, Order.class);
			int orderId = Integer.valueOf(String.valueOf(redisUtils.incr("order_id", 1)));
			order.setId(orderId);

			ObjectMapper mapper = new ObjectMapper();
			try {
				List<OrderLineParam> psl = mapper.readValue(param.getLinesStr(), new TypeReference<List<OrderLineParam>>(){});
				param.setLines(psl);
			} catch (Exception e){
				throw new ParamErrorException();
			}

			List<OrderLineParam> lines = param.getLines();
			Map<Integer, Material> map = new HashMap<>();
			Map<Integer, Stock> mapS = new HashMap<>();
			BigDecimal bigAll = new BigDecimal(0);

			List<OrderLine> lineList = new ArrayList<>();
			for (OrderLineParam line :
					lines) {
				valLine(line);
				String id = UUID.randomUUID().toString();
				Material material = null;

				if (map.containsKey(line.getMaterialId())) {
					material = map.get(line.getMaterialId());
				} else {
					Optional<Material> materialOps = materialRepository.findById(line.getMaterialId());
					if (!materialOps.isPresent()) {
						throw new ParamErrorException();
					}
					material = materialOps.get();
					map.put(line.getMaterialId(), material);
				}

				BigDecimal multiply = line.getQuantity().multiply(new BigDecimal(material.getUnitPrice()));
				bigAll = bigAll.add(multiply);

				Stock stock = null;
				if (mapS.containsKey(material.getId())) {
					stock = mapS.get(material.getId());
				} else {
					stock = stockRepository.findByMaterialId(material.getId());
				}

				if (line.getQuantity().compareTo(new BigDecimal(stock.getQuantity())) > 0) {
					throw new NumOverException(material.getName());
				} else {
					BigDecimal rb = new BigDecimal(stock.getQuantity()).subtract(line.getQuantity());
					stock.setQuantity(rb.setScale(5, BigDecimal.ROUND_HALF_UP).toString());
					mapS.put(material.getId(), stock);
					stockRepository.save(stock);
				}
				OrderLine ol = new OrderLine()
						.setId(id)
						.setMaterialId(line.getMaterialId())
						.setAmount(multiply.setScale(2, BigDecimal.ROUND_HALF_UP).toString())
						.setOrderId(orderId)
						.setQuantity(line.getQuantity().setScale(5, BigDecimal.ROUND_HALF_UP).toString());
				lineList.add(ol);
			}
			orderLineRepository.saveAll(lineList);

			String inc = "SO-" + String.format("%06d", Integer.valueOf(String.valueOf(redisUtils.incr(RedisUtils.order_number, 1))));
			order.setNumber(inc);
			order.setDocumentType(getDocuInt(param.getDocumentType()));
			order.setAmountTotal(bigAll.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			orderRespository.save(order);
			OrderRes orderRes = getOrderRes(order, lineList);
			return orderRes;
		} finally {
			redisUtils.delete(RedisUtils.key_for_createorder);
		}
	}

	private boolean del(Integer id) {
		Optional<Order> orderOps = orderRespository.findById(id);
		if (orderOps.isPresent()) {
			Order order = orderOps.get();
			List<OrderLine> lines = orderLineRepository.findAllByOrderId(order.getId());
			if (!CollectionUtils.isEmpty(lines))
				orderLineRepository.deleteInBatch(lines);
			orderRespository.delete(order);
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public OrderRes updateOrder(OrderParam param) {
		val(param);
		try {
			if (!redisUtils.lock(RedisUtils.key_for_putorder)) {
				throw new BingfaException();
			}
			// del
			Order orderOld;
			Optional<Order> orderOps = orderRespository.findById(param.getId());
			List<OrderLine> linesOld = null;
			if (orderOps.isPresent()) {
				orderOld = orderOps.get();
				linesOld = orderLineRepository.findAllByOrderId(orderOld.getId());
				if (!CollectionUtils.isEmpty(linesOld))
					orderLineRepository.deleteInBatch(linesOld);
				orderRespository.delete(orderOld);
			} else {
				OrderRes orderRes = saveOrder(param);
				orderRes.setDocumentType("('" + orderRes.getDocumentType() + "',)");
				orderRes.setSalEmployee("('" + orderRes.getSalEmployee() + "',)");
				return orderRes;
			}

			Order order = AbstractConverter.toTarget(param, Order.class);
			order.setId(orderOld.getId());
			order.setNumber(orderOld.getNumber());

			ObjectMapper mapper = new ObjectMapper();
			try {
				List<OrderLineParam> psl = mapper.readValue(param.getLinesStr(), new TypeReference<List<OrderLineParam>>(){});
				param.setLines(psl);
			} catch (Exception e){
				throw new ParamErrorException();
			}

			List<OrderLineParam> lines = param.getLines();
			Map<Integer, Material> map = new HashMap<>();
			BigDecimal bigAll = new BigDecimal(0);
			Map<Integer, Stock> mapS = new HashMap<>();
			List<OrderLine> lineList = new ArrayList<>();
			Map<Integer, OrderLine> linesOldMap = new HashMap<>();
			for (OrderLine linOld:
				 linesOld) {
				linesOldMap.put(linOld.getMaterialId(), linOld);
			}
			for (OrderLineParam line:
					lines) {
				valLine(line);
				String id = UUID.randomUUID().toString();
				Material material = null;
				if (map.containsKey(line.getMaterialId())) {
					material = map.get(line.getMaterialId());
				} else {
					Optional<Material> materialOps = materialRepository.findById(line.getMaterialId());
					if (!materialOps.isPresent()) {
						throw new ParamErrorException();
					}
					material = materialOps.get();
					map.put(line.getMaterialId(), material);
				}

				Stock stock = null;
				if (mapS.containsKey(material.getId())) {
					stock = mapS.get(material.getId());
				} else {
					stock = stockRepository.findByMaterialId(material.getId());
				}

				BigDecimal bigDecimal = new BigDecimal(stock.getQuantity());
				if (linesOldMap.containsKey(material.getId())) {
					bigDecimal = bigDecimal.add(new BigDecimal(linesOldMap.get(material.getId()).getQuantity()));
				}

				if (line.getQuantity().compareTo(bigDecimal) > 0) {
					throw new NumOverException(material.getName());
				} else {
					bigDecimal = bigDecimal.subtract(line.getQuantity());
					stock.setQuantity(bigDecimal.setScale(5, BigDecimal.ROUND_HALF_UP).toString());
					mapS.put(material.getId(), stock);
					stockRepository.save(stock);
				}

				BigDecimal multiply = line.getQuantity().multiply(new BigDecimal(material.getUnitPrice()));
				bigAll = bigAll.add(multiply);
				OrderLine ol = new OrderLine()
						.setId(id)
						.setMaterialId(line.getMaterialId())
						.setAmount(multiply.setScale(2, BigDecimal.ROUND_HALF_UP).toString())
						.setOrderId(orderOld.getId())
						.setQuantity(line.getQuantity().setScale(5, BigDecimal.ROUND_HALF_UP).toString());
				lineList.add(ol);
			}
			orderLineRepository.saveAll(lineList);

			order.setDocumentType(getDocuInt(param.getDocumentType()));
			order.setAmountTotal(bigAll.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			orderRespository.save(order);
			OrderRes orderRes = getOrderRes(order, lineList);
			orderRes.setDocumentType("('" + orderRes.getDocumentType() + "',)");
			orderRes.setSalEmployee("('" + orderRes.getSalEmployee() + "',)");
			return orderRes;
		} finally {
			redisUtils.delete(RedisUtils.key_for_putorder);
		}
	}

	@Override
	public List<Material> getMaterial() {
		return materialRepository.findAll();
	}

	@Override
	public Material getMaterial(int id) {
		return materialRepository.findById(id).orElse(null);
	}

	@Override
	public void save(Material material){
		materialRepository.save(material);
	}

	@Override
	public void deleteMaterial(int id) {
		materialRepository.deleteById(id);
	}
}
