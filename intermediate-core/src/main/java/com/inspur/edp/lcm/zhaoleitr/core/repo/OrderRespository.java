package com.inspur.edp.lcm.zhaoleitr.core.repo;

import com.inspur.edp.lcm.zhaoleitr.api.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRespository extends JpaRepository<Order,Integer> {
    List<Order> findAllByNumberLike(String number);
}

