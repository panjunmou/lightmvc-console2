package com.pjm.lightmvc.vo.base;

import java.io.Serializable;

/**
 * Created by PanJM on 2016/3/15.
 * 记录当前登录用户的信息
 */
public class SessionInfo implements Serializable{

    private static final long serialVersionUID = 6841513354646641095L;

    private Long id;// 用户ID
    private String loginName;// 登录名
    private String userName;// 姓名

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
}
