package com.inspur.edp.lcm.zhaoleitr.api.dto;

import lombok.Data;

public class ParamErrorException extends RuntimeException {
    public ParamErrorException(Object obj) {
        super();
        setObj(obj);
    }

    public ParamErrorException() {
        super();
    }
    private Object obj;

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Object getObj() {
        return obj;
    }
}
