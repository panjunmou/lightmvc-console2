package com.common.persistence.pagetable;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by PanJM on 2016/3/16.
 */

public class PageModel implements Serializable {

    private static final long serialVersionUID = 2065364514975781849L;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 当前页
     */
    private int page;

    /**
     * 每页显示记录数
     */
    private int pageSize;

    /**
     * 排序字段
     */
    private String sort;

    /**
     * asc/desc
     */
    private String order;


    /**
     * 查询结果集
     */
    private List<?> rows;

    public PageModel() {
    }

    public PageModel(String sort, String order) {
        this.sort = sort;
        this.order = order;
    }

    public PageModel(int page, int pageSize, String sort, String order) {
        super();
        this.page = page;
        this.pageSize = pageSize;
        this.sort = sort;
        this.order = order;
    }

    public LinkedHashMap<String, String> getOrderBy() {
        LinkedHashMap<String, String> orderBy = null;
        if (this.sort != null && !"".equals(this.sort) && this.order != null && !"".equals(this.order)) {
            orderBy = new LinkedHashMap<>();
            String[] sortArr = sort.split(",");
            String[] orderArr = order.split(",");
            for (int i = 0; i < sortArr.length; i++) {
                String sortName = sortArr[i];
                String orderByName = orderArr[i];
                orderBy.put(sortName, orderByName);
            }
        }
        return orderBy;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public List<?> getRows() {
        if (rows == null) {
            rows = Lists.newArrayList();
        }
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public int getStart() {
        if (page == 0) {
            page = 1;
        }
        return (page - 1) * pageSize;
    }
}
