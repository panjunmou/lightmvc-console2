package com.pjm.lightmvc.model.sys;

import com.pjm.lightmvc.model.base.IdEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by PanJM on 2016/3/17.
 * 区域实体类
 */
@Entity
@Table(name = "sys_area")
public class TArea extends IdEntity {

    /**
     * 区域名称
     */
    @Column(name = "NAME", nullable = false, unique = true, length = 20)
    private String name;

    /**
     * 区域描述
     */
    @Column(name = "DESCRIPTION", length = 50)
    private String description;

    /**
     * 排序
     */
    @Column(name = "SEQ", length = 3)
    private Integer seq;

    /**
     * 树状关联ID
     */
    @Column(name = "TREEID",length = 10,unique = true)
    private Long treeId;

    /**
     * 父级
     */
    @ManyToOne
    @JoinColumn(name = "PARENT",referencedColumnName = "TREEID")
    private TArea parent;

    /**
     * 子集
     */
    @OneToMany(mappedBy = "parent",orphanRemoval = true)
    private Set<TArea> children = new HashSet<>();

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

    public TArea getParent() {
        return parent;
    }

    public void setParent(TArea parent) {
        this.parent = parent;
    }

    public Set<TArea> getChildren() {
        return children;
    }

    public void setChildren(Set<TArea> children) {
        this.children = children;
    }

    public Long getTreeId() {
        return treeId;
    }

    public void setTreeId(Long treeId) {
        this.treeId = treeId;
    }
}
