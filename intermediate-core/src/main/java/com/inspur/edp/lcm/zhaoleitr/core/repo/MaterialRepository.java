package com.inspur.edp.lcm.zhaoleitr.core.repo;

import com.inspur.edp.lcm.zhaoleitr.api.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface MaterialRepository extends JpaRepository<Material,Integer> {
}
