package com.common.persistence.condition;

import com.common.persistence.contants.DBConstants;

/**
 * Created by PanJM on 2016/3/16.
 */

public class Condition implements DBConstants {

    /**
     * 属性名称
     */
    private String name;

    /**
     * 属性值
     */
    private Object value;

    /**
     * 运算符
     */
    private String operate;

    public Condition() {

    }

    public Condition(String name, Object value, String operate) {
        this.name = name;
        this.value = value;
        this.operate = operate;
    }

    public Condition(String name, String operate) {
        this.name = name;
        this.operate = operate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public boolean hasInOperate() {
        return this.operate != null && (this.operate.equalsIgnoreCase(IN) || this.operate.equalsIgnoreCase(NOT_IN));
    }

    public boolean hasNull() {
        return this.operate != null && (this.operate.equalsIgnoreCase(ISNULL) || this.operate.equalsIgnoreCase(NOTNULL));
    }
}
