package com.inspur.edp.lcm.zhaoleitr.api.dto;

import lombok.Data;

@Data
public class Res<T> {
    public Res(T t) {
        setRes(t);
    }

    private T res;
}
