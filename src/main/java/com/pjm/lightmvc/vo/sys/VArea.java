package com.pjm.lightmvc.vo.sys;

import com.pjm.lightmvc.model.sys.TArea;
import com.pjm.lightmvc.vo.base.TreeVo;

/**
 * Created by PanJM on 2016/4/5.
 */
public class VArea extends TreeVo<TArea> {

    /**
     * 区域名称
     */
    private String name;

    /**
     * 区域描述
     */
    private String description;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 父节点id
     */
    private String pId;

    /**
     * 用于定位节点位置
     */
    private String locationTree;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
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
