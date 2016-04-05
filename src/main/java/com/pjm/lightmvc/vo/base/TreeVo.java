package com.pjm.lightmvc.vo.base;

import java.util.List;

/**
 * Created by PanJM on 2016/3/25.
 */
public class TreeVo<T> extends BaseVo{

    private Long treeId;
    private String text;
    private String state = "open";
    private boolean checked = false;
    private Object attributes;
    private List<T> children;
    private String iconCls;
    /**
     * 父级id
     */
    private String pId;

    /**
     * 用于定位节点位置
     */
    private String locationTree;

    public Long getTreeId() {
        return treeId;
    }

    public void setTreeId(Long treeId) {
        this.treeId = treeId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Object getAttributes() {
        return attributes;
    }

    public void setAttributes(Object attributes) {
        this.attributes = attributes;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getLocationTree() {
        return locationTree;
    }

    public void setLocationTree(String locationTree) {
        this.locationTree = locationTree;
    }
}
