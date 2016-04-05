package com.pjm.lightmvc.dao;

import com.common.persistence.QueryBuilder;
import com.common.persistence.condition.Condition;
import com.common.persistence.pagetable.PageModel;
import com.common.persistence.pagetable.QueryResult;
import com.pjm.lightmvc.model.base.IdEntity;
import com.pjm.lightmvc.util.GenericsUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

/**
 * Created by PanJM on 2016/3/16.
 */
public class BaseDao<T extends IdEntity> {

    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    protected Class<T> entityClass = GenericsUtil.getSuperClassGenricType(this.getClass());

    /**
     * 保存实体对象
     *
     * @param t
     * @return
     * @throws Exception
     */
    public T save(T t) throws Exception {
        t.setCreateDate(new Date());
        t.setUpdateDate(new Date());
        entityManager.persist(t);
        return t;
    }

    /**
     * 根据对象删除实体
     *
     * @param entity
     */
    public void delete(T entity) throws Exception {
        entityManager.remove(entityManager.merge(entity));
    }

    /**
     * 根据id删除实体
     *
     * @param id
     * @throws Exception
     */
    public void delete(Long id) throws Exception {
        T entity = (T) entityManager.find(entityClass, id);
        entityManager.remove(entity);
    }

    /**
     * 更新实体
     *
     * @param entity
     */
    public void update(T entity) {
        entity.setUpdateDate(new Date());
        entityManager.merge(entity);
    }

    /**
     * 查找实体
     * @param id
     * @return
     */
    public T find(Long id) {
        T t = entityManager.find(entityClass, id);
        return t;
    }

    /**
     * 获取单个对象
     *
     * @param query
     * @param parameters
     * @return
     * @throws Exception
     */
    public T getSingleObject(Query query, Map<String, Object> parameters) throws Exception {
        List resultList = getObjects(query, parameters);
        if (resultList.size() == 0) {
            return null;
        } else {
            return (T) resultList.get(0);
        }
    }

    /**
     * 获取多个对象
     *
     * @param query
     * @param parameters
     * @return
     * @throws Exception
     */
    public List<T> getObjects(Query query, Map<String, Object> parameters) throws Exception {
        for (String name : parameters.keySet()) {
            query.setParameter(name, parameters.get(name));
        }
        List<T> tList = query.getResultList();
        if (tList == null) {
            return Collections.EMPTY_LIST;
        }
        return tList;
    }

    /**
     * 多条件查询
     *
     * @param firstIndex
     * @param maxResult
     * @param conditionList
     * @param orderBy
     * @return
     * @throws Exception
     */
    public QueryResult<T> getPageResult(int firstIndex, int maxResult, List<Condition> conditionList, LinkedHashMap<String, String> orderBy) throws Exception {
        QueryBuilder builder = new QueryBuilder();
        builder.from(entityClass);
        builder.where(conditionList);
        builder.order(orderBy);
        System.out.println("Print HQL:" + printHQL(builder));
        Query query = entityManager.createQuery(builder.toString());
        if (firstIndex != -1 && maxResult != -1) query.setFirstResult(firstIndex).setMaxResults(maxResult);
        List<T> list = getObjects(query, builder.getParameters());
        QueryResult<T> result = new QueryResult<>();
        result.setReultList(list);
        return result;
    }

    private String printHQL(QueryBuilder builder) {
        Map<String, Object> parameters = builder.getParameters();
        String buildStr = builder.toString();
        for (String name : parameters.keySet()) {
            buildStr = buildStr.replace(":" + name, parameters.get(name).toString());
        }
        return buildStr;
    }

    /**
     * 多条件查询
     *
     * @param conditionList
     * @param pageModel
     * @return
     * @throws Exception
     */
    public QueryResult<T> getPageResult(List<Condition> conditionList, PageModel pageModel) throws Exception {
        return getPageResult(pageModel.getStart(), pageModel.getPageSize(), conditionList, pageModel.getOrderBy());
    }
}
