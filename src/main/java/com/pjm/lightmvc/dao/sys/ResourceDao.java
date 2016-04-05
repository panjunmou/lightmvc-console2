package com.pjm.lightmvc.dao.sys;

import com.pjm.lightmvc.dao.BaseDao;
import com.pjm.lightmvc.model.sys.TResource;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by PanJM on 2016/3/25.
 */
@Repository
public class ResourceDao extends BaseDao<TResource>{

    public TResource findByTreeId(long treeId) throws Exception {
        Query query = getEntityManager().createQuery("select q from TResource q where q.treeId =:treeId");
        Map<String, Object> map = new HashMap<>();
        map.put("treeId", treeId);
        TResource tResource = getSingleObject(query, map);
        return tResource;
    }
}
