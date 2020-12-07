package com.inspur.edp.lcm.zhaoleitr.core.repo;

import com.inspur.edp.lcm.zhaoleitr.api.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock,Integer> {
    Stock findByMaterialId(Integer id);
}
