package com.pjm.lightmvc.vo.base;

import java.io.Serializable;

/**
 * Created by PanJm on 2016/3/15.
 * Json返回对象的封装
 */
public class JsonVo implements Serializable{

    private static final long serialVersionUID = 8816936788178987039L;

    private boolean success = false;

    private String msg = "";

    private Object obj = null;

    private String text;

    private String value;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
