package com.pjm.lightmvc.vo.sys;

import com.pjm.lightmvc.vo.base.BaseVo;

import java.io.Serializable;

/**
 * Created by PanJM on 2016/3/15.
 * 用户VO
 */
public class VUser extends BaseVo implements Serializable{

    private static final long serialVersionUID = -5797839586622770681L;

    private String loginName; // 登录名
    private String passWord; // 密码
    private String userName; // 姓名
    private Integer sex; // 性别
    private Integer age; // 年龄
    private String phone;//电话

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
