package com.pjm.lightmvc.contants;

import com.pjm.lightmvc.contants.base.BaseEnum;

/**
 * Created by PanJM on 2016/3/16.
 *  使用状态
 */
public enum UseStatus implements BaseEnum{

    ACTIVITY(1, "启用"),

    UNACTIVITI(-1, "停用");

    private Integer value;

    private String label;

    UseStatus(Integer value, String label) {
        this.value = value;
        this.label = label;
    }


    @Override
    public String getValue() {
        return this.value.toString();
    }

    @Override
    public String getLabel() {
        return this.label;
    }
}
