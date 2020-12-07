package com.inspur.edp.lcm.zhaoleitr.core.repo;

import com.inspur.edp.lcm.zhaoleitr.api.entity.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLine,Integer> {
    List<OrderLine> findAllByOrderId(Integer id);
}

