package com.quxin.freshfun.model.goods;

/**
 * 商品分类出参
 * Created by qucheng on 2016/10/18.
 */
public class GoodsCategoryOut {
    private Integer categoryKey ;
    private String categoryName;

    public Integer getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(Integer categoryKey) {
        this.categoryKey = categoryKey;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "GoodsCategoryOut{" +
                "categoryKey=" + categoryKey +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
