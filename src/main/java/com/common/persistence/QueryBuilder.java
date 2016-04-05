package com.common.persistence;

import com.common.persistence.condition.Condition;
import com.common.persistence.contants.DBConstants;
import com.pjm.lightmvc.model.base.IdEntity;

import javax.persistence.Entity;
import java.util.*;

/**
 * Created by PanJM on 2016/3/16.
 */

public class QueryBuilder {

    private StringBuilder builder;

    private Map<String, Object> parameters;

    public QueryBuilder() {
        init();
    }

    private void init() {
        if (builder == null) {
            builder = new StringBuilder("");
        }
        builder.append(DBConstants.SELECT).append(DBConstants.ALIAS);
    }

    public Map<String, Object> getParameters() {
        if (parameters == null) {
            parameters = new HashMap<>();
        }
        return parameters;
    }

    public <T extends IdEntity> void from(Class<T> entityClass) {
        String entityName = getEntityName(entityClass);
        builder.append(DBConstants.FROM).append(entityName).append(" ").append(DBConstants.ALIAS);
    }

    private <T extends IdEntity> String getEntityName(Class<T> entityClass) {
        String entityName = entityClass.getSimpleName();
        Entity entity = entityClass.getAnnotation(Entity.class);
        if (entity != null && !"".equals(entity.name())) {
            entityName = entity.name();
        }
        return entityName;
    }

    public void where(List<Condition> conditionList) {
        builder.append(DBConstants.WHERE);
        bulidWhere(conditionList);
    }

    public void order(LinkedHashMap<String, String> orderBy) {
        builder.append(buildOrderby(orderBy));
    }

    /**
     * 组装where语句
     *
     * @param conditionList
     */
    private void bulidWhere(List<Condition> conditionList) {
        if (conditionList != null && conditionList.size() > 0) {
            for (int i = 0; i < conditionList.size(); i++) {
                Condition condition = conditionList.get(i);
                builder.append(DBConstants.AND);
                String conditionName = condition.getName();
                String conditionOperate = condition.getOperate();
                Object conditionValue = condition.getValue();
                if (conditionOperate != null && conditionName != null) {
                    builder
                            .append(DBConstants.ALIAS)
                            .append(DBConstants.DOT)
                            .append(conditionName)
                            .append(" ")
                            .append(conditionOperate);
                    if (condition.hasInOperate()) {
                        if (conditionValue.getClass().isArray()) {
                            Object[] objects = (Object[]) conditionValue;
                            List<Object> objectList = Arrays.asList(objects);
                            builder
                                    .append(DBConstants.COLON)
                                    .append("param_")
                                    .append(i);
                            getParameters().put("param_" + i, objectList);
                        } else if (conditionValue instanceof String) {
                            builder
                                    .append(DBConstants.LEFT_BRACKET)
                                    .append(conditionValue)
                                    .append(DBConstants.RIGHT_BRACKET);
                        }else{
                            builder
                                    .append(DBConstants.COLON)
                                    .append("param_")
                                    .append(i);
                            getParameters().put("param_" + i, conditionValue);
                        }
                    }else{
                        if (!condition.hasNull()) {
                            builder
                                    .append(DBConstants.COLON)
                                    .append("param_")
                                    .append(i);
                            getParameters().put("param_" + i, conditionValue);
                        }
                    }
                }
            }
        }
    }

    private String buildOrderby(LinkedHashMap<String, String> orderBy) {
        StringBuffer orderbyql = new StringBuffer("");
        if(orderBy!=null && orderBy.size()>0){
            orderbyql.append(DBConstants.ORDER_BY);
            for(String key : orderBy.keySet()){
                orderbyql
                        .append(DBConstants.ALIAS)
                        .append(DBConstants.DOT)
                        .append(key)
                        .append(" ")
                        .append(orderBy.get(key))
                        .append(",");
            }
            orderbyql.deleteCharAt(orderbyql.length()-1);
        }
        return orderbyql.toString();
    }

    @Override
    public String toString() {
        return builder.toString();
    }


}
