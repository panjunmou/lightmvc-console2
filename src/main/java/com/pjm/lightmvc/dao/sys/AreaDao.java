package com.pjm.lightmvc.dao.sys;

import com.pjm.lightmvc.dao.BaseDao;
import com.pjm.lightmvc.model.sys.TArea;
import org.springframework.stereotype.Repository;

/**
 * Created by PanJM on 2016/4/5
 */
@Repository
public class AreaDao extends BaseDao<TArea>{
    public TArea findByTreeId(long treeId) {
        return null;
    }
}
