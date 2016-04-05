package com.pjm.lightmvc.dao.sys;

import com.pjm.lightmvc.dao.BaseDao;
import com.pjm.lightmvc.model.sys.TUser;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by PanJM on 2016/3/16.
 */
@Repository
public class UserDao extends BaseDao<TUser> {

    /**
     * 根据登录名或手机号码查找用户
     * @param nameOrPhone
     * @return
     * @throws Exception
     */
    public TUser getUserByNameOrPhone(String nameOrPhone) throws Exception{
        Query query = getEntityManager().createQuery("select u from TUser u where u.loginName = :loginName or u.phone = :phone");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("loginName", nameOrPhone);
        parameters.put("phone", nameOrPhone);
        TUser tUser = getSingleObject(query, parameters);
        return tUser;
    }

}
