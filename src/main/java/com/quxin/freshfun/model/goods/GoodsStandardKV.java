package com.quxin.freshfun.model.goods;

/**
 * 商品规格键值对
 * Created by qc on 2016/11/1.
 */
public class GoodsStandardKV {

    /**
     * 商品属性key
     */
    private String key ;
    /**
     * 商品name
     */
    private String name;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GoodsStandardKV{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
