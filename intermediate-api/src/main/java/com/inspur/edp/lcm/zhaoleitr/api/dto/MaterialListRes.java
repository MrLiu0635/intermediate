package com.inspur.edp.lcm.zhaoleitr.api.dto;

import com.inspur.edp.lcm.zhaoleitr.api.entity.Material;
import lombok.Data;

import java.util.List;

@Data
public class MaterialListRes {
    List<Material> res;
}
