package com.inspur.edp.lcm.zhaoleitr.api.service;

import com.inspur.edp.lcm.zhaoleitr.api.dto.StockListRes;
import com.inspur.edp.lcm.zhaoleitr.api.dto.StockPutParam;
import com.inspur.edp.lcm.zhaoleitr.api.dto.StockPutRes;
import com.inspur.edp.lcm.zhaoleitr.api.entity.Stock;

public interface StockService {
    StockListRes getStockList();

    StockPutRes update(StockPutParam param);
}
