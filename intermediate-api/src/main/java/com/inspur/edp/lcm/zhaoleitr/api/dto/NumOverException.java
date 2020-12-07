package com.inspur.edp.lcm.zhaoleitr.api.dto;

public class NumOverException extends RuntimeException {
    public NumOverException(Object obj) {
        super();
        setObj(obj);
    }

    public NumOverException() {
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
