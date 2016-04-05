package com.pjm.lightmvc.contants.sys.resource;

import com.pjm.lightmvc.contants.base.BaseEnum;

/**
 * Created by PanJM on 2016/3/29.
 * 资源菜单图标
 */
public enum ResourceIcon implements BaseEnum {

    COMPANY("icon-company", "父级菜单"),

    FOLDER("icon-folder", "子级菜单"),

    PAGE("icon-page", "页面"),

    LINK("icon-link", "链接"),

    BTN("icon-btn", "按钮");

    private String value;

    private String label;

    ResourceIcon(String value, String label) {
        this.value = value;
        this.label = label;
    }


    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getLabel() {
        return this.label;
    }
}
