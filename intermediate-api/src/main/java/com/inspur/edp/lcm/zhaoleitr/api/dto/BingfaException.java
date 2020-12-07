package com.inspur.edp.lcm.zhaoleitr.api.dto;

public class BingfaException extends RuntimeException {
    public BingfaException(Object obj) {
        super();
        setObj(obj);
    }

    public BingfaException() {
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
