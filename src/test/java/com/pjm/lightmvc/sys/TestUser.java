package com.pjm.lightmvc.sys;

import com.common.persistence.condition.Condition;
import com.common.persistence.contants.DBConstants;
import com.common.persistence.pagetable.PageModel;
import com.common.persistence.pagetable.QueryResult;
import com.pjm.lightmvc.BaseTest;
import com.pjm.lightmvc.dao.sys.UserDao;
import com.pjm.lightmvc.model.sys.TOrganization;
import com.pjm.lightmvc.model.sys.TUser;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PanJM on 2016/3/18.
 */
public class TestUser extends BaseTest {

    @PersistenceContext
    private EntityManager entityManager;
    @Resource
    private UserDao userDao;

    @Test
    @Rollback(false)
    public void testSaveUser() throws Exception {
        TUser tUser = new TUser();
        tUser.setLoginName("user2");
        tUser.setUserName("用户2");
        tUser.setPassWord("admin");
        TOrganization tOrganization = new TOrganization();
        tOrganization.setId(1l);
        tUser.settOrganization(tOrganization);
        userDao.save(tUser);
    }

    @Test
    public void testFindUser() {
        TUser tUser = entityManager.find(TUser.class, 1l);
        System.out.println("tUser.getUserName() = " + tUser.getUserName());
    }

    @Test
    public void testGetQueryResult() throws Exception {
        List<Condition> conditionList = new ArrayList<>();
//        Condition condition = new Condition("id", "1l,10l,12l", DBConstants.IN);
        /*Long[] longs = new Long[3];
        longs[0] = 1l;
        longs[1] = 10l;
        longs[2] = 12l;
        Condition condition = new Condition("id", longs, DBConstants.IN);*/
        List<Long> longList = new ArrayList<>();
        longList.add(1l);
        longList.add(10l);
        longList.add(12l);
        Condition condition = new Condition("id", longList, DBConstants.IN);

        conditionList.add(condition);

        PageModel pageModel = new PageModel();
        pageModel.setSort("createDate,id");
        pageModel.setOrder(DBConstants.DESC + "," + DBConstants.DESC);
        QueryResult<TUser> pageResult = userDao.getPageResult(conditionList, pageModel);
        List<TUser> reultList = pageResult.getReultList();
        for (int i = 0; i < reultList.size(); i++) {
            TUser tUser = reultList.get(i);
            System.out.println("tUser.getLoginName() = " + tUser.getLoginName());
        }
    }
}

