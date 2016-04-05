package com.pjm.lightmvc.contants.sys.resource;

import com.pjm.lightmvc.contants.base.BaseEnum;

/**
 * Created by PanJM on 2016/3/28.
 * 资源类型
 */
public enum  ResourceStatus implements BaseEnum {

    MENU(0, "菜单"),

    BUTTON(1, "按钮"),

    LINK(2, "链接"),

    PAGE(3, "页面");

    private Integer value;

    private String label;

    ResourceStatus(Integer value, String label) {
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
