package com.inspur.edp.lcm.zhaoleitr.core.service;

import com.inspur.edp.lcm.zhaoleitr.api.dto.*;
import com.inspur.edp.lcm.zhaoleitr.api.entity.Material;
import com.inspur.edp.lcm.zhaoleitr.api.entity.Stock;
import com.inspur.edp.lcm.zhaoleitr.api.service.MaterialService;
import com.inspur.edp.lcm.zhaoleitr.api.service.StockService;
import com.inspur.edp.lcm.zhaoleitr.core.repo.StockRepository;
import com.inspur.edp.lcm.zhaoleitr.core.utils.AbstractConverter;
import com.inspur.edp.lcm.zhaoleitr.core.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class StockServiceImpl implements StockService {
    @Autowired
    MaterialService materialService;
    @Autowired
    RedisUtils redisUtils;
    @Resource
    StockRepository stockRepository;

    @Override
    @Transactional
    public StockPutRes update(StockPutParam param) {
        if (Objects.isNull(param) || Objects.isNull((param.getMaterialId())) || param.getQuantity() == null || Float.isNaN(param.getQuantity())) {
            throw new ParamErrorException(param);
        }
        Stock stock = stockRepository.findByMaterialId(param.getMaterialId());
        if (stock != null) {
            try {
                if (!redisUtils.lock(RedisUtils.key_for_putstock)) {
                    throw new BingfaException();
                }
                String format = String.format("%19.5f", param.getQuantity()).trim();
                stock.setQuantity(format);
                stockRepository.save(stock);
                Material material = materialService.getMaterial(stock.getMaterialId());
                StockPutRes stockPutRes = AbstractConverter.toTarget(stock, StockPutRes.class);
                stockPutRes.setQuantity(Float.valueOf(stock.getQuantity()).toString());
                stockPutRes.setMaterialName(material.getName());
                stockPutRes.setMaterialUnitPrice(material.getUnitPrice());
                stockPutRes.setMaterialUnit(material.getUnit());
                return stockPutRes;
            } finally {
                redisUtils.delete(RedisUtils.key_for_putstock);
            }
        } else {
            throw new ParamErrorException(param);
        }
    }

    @Override
    public StockListRes getStockList() {
        StockListRes stockListRes = new StockListRes();
        List<Stock> stockList = stockRepository.findAll();
        if (CollectionUtils.isEmpty(stockList)) return stockListRes;
        ArrayList<StockRes> stockResList = new ArrayList<>();

        stockList.forEach(stock -> {
            final StockRes stockRes = AbstractConverter.toTarget(stock, StockRes.class);
            Material material = materialService.getMaterial(stock.getMaterialId());
            stockRes.setMaterialName(material.getName());
            stockRes.setMaterialUnit(material.getUnit());
            stockRes.setMaterialUnitPrice(material.getUnitPrice());
            stockResList.add(stockRes);
        });
        stockListRes.setRes(stockResList);
        return stockListRes;
    }
}
