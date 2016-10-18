package com.quxin.freshfun.model.goods;

/**
 * 排序对象数据库实体
 * Created by qucheng on 2016/10/17.
 */
public class GoodsSortPOJO {
    private Integer id ;
    private String sortKey ;
    private String sortValue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public String getSortValue() {
        return sortValue;
    }

    public void setSortValue(String sortValue) {
        this.sortValue = sortValue;
    }
}
