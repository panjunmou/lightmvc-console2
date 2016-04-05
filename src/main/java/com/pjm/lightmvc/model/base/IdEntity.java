package com.pjm.lightmvc.model.base;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by PanJM on 2016/3/15.
 *
 * @MappedSuperclass 用在父类上面。
 * 当这个类肯定是父类时，加此标注。
 * 如果改成@Entity，则继承后，多个类继承，只会生成一个表，而不是多个继承，生成多个表
 * 且子类可以不需要再实现Serializable,但是父类必须实现Serializable接口
 * 统一定义entity基类.
 */
@MappedSuperclass
public abstract class IdEntity implements Serializable{

    private static final long serialVersionUID = -2806504249722759638L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID",length = 10)
    protected Long id;

    @Column(name = "CREATE_USER", length = 20)
    protected String createUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE", length = 19)
    protected Date createDate;

    @Column(name = "UPDATE_USER", length = 20)
    protected String updateUser;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATE_DATE", length = 19)
    protected Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
