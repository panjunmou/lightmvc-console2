package com.common.persistence.pagetable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PanJM on 2016/3/16.
 */
public class QueryResult<T> {

    /**
     * 总记录数
     */
    private long totalCount;

    /**
     * 查询结果集
     */
    private List<T> reultList;

    public QueryResult() {
        reultList = new ArrayList<>();
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getReultList() {
        if (reultList == null) {
            reultList = new ArrayList<>();
        }
        return reultList;
    }

    public void setReultList(List<T> reultList) {
        this.reultList = reultList;
    }
}
