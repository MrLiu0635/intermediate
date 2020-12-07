package com.inspur.edp.lcm.zhaoleitr.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class ListRes<T> {
    public ListRes(List<T> ts) {
        setRes(ts);
    }

    private List<T> res;
}
