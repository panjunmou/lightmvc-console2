package com.pjm.lightmvc.model.sys;

import com.pjm.lightmvc.model.base.IdEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * Created by PanJM on 2016/3/15.
 * 用户实体类
 */
@Entity
@Table(name = "sys_user")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TUser extends IdEntity{

    /**
     * 登录名
     */
    @Column(name = "LOGINNAME", length = 20, unique = true)
    private String loginName;

    /**
     * 用户名
     */
    @Column(name = "USERNAME", length = 20)
    private String userName;

    /**
     * 密码
     */
    @Column(name = "PASSWORD", length = 50)
    private String passWord;

    /**
     * 性别
     */
    @Column(name = "SEX", length = 3)
    private Integer sex;

    /**
     * 年龄
     */
    @Column(name = "AGE", length = 3)
    private Integer age; // 年龄

    /**
     * 电话
     */
    @Column(name = "PHONE", length = 20, unique = true)
    private String phone;

    /**
     * 状态
     */
    @Column(name = "STATUS", length = 3)
    private Integer status;

    /**
     * 角色
     */
    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private TRole tRole;

    @ManyToOne
    @JoinColumn(name = "ORG_NO",referencedColumnName = "ORG_NO")
    private TOrganization tOrganization;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public TRole gettRole() {
        return tRole;
    }

    public void settRole(TRole tRole) {
        this.tRole = tRole;
    }

    public TOrganization gettOrganization() {
        return tOrganization;
    }

    public void settOrganization(TOrganization tOrganization) {
        this.tOrganization = tOrganization;
    }
}
