package com.pjm.lightmvc.model.sys;

import com.pjm.lightmvc.model.base.IdEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by PanJM on 2016/3/17.
 * 角色实体类
 */
@Entity
@Table(name = "sys_role")
public class TRole extends IdEntity {

    /**
     * 角色名称
     */
    @Column(name = "NAME", length = 20)
    private String name;

    /**
     * 排序
     */
    @Column(name = "SEQ", length = 3)
    private Integer seq;

    /**
     * 描述
     */
    @Column(name = "DESCRIPTION", length = 100)
    private String description;

    /**
     * 角色类型
     */
    @Column(name = "ROLE_TYPE", nullable = false, length = 10)
    private Integer type;

    /**
     * 资源
     */
    @ManyToMany
    @JoinTable(name = "sys_role_resource",
            joinColumns = {
                    @JoinColumn(name = "ROLE_ID", nullable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "RESOURCE_ID", nullable = false)
            }
    )
    private Set<TResource> tResourceSet = new HashSet<>(0);

    /**
     * 机构
     */
    @ManyToOne
    @JoinColumn(name = "ORG_ID")
    private TOrganization tOrganization;

    /**
     * 用户
     */
    @OneToMany(mappedBy = "tRole")
    private Set<TUser> tUserSet = new HashSet<>(0);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public TOrganization gettOrganization() {
        return tOrganization;
    }

    public void settOrganization(TOrganization tOrganization) {
        this.tOrganization = tOrganization;
    }

    public Set<TUser> gettUserSet() {
        return tUserSet;
    }

    public void settUserSet(Set<TUser> tUserSet) {
        this.tUserSet = tUserSet;
    }

    public Set<TResource> gettResourceSet() {
        return tResourceSet;
    }

    public void settResourceSet(Set<TResource> tResourceSet) {
        this.tResourceSet = tResourceSet;
    }
}
