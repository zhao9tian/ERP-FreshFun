package com.quxin.freshfun.model.goods;

/**
 * 商品排序对象
 * Created by qucheng on 2016/10/17.
 */
public class GoodsSortParam {
    /**
     * 排序商品Id
     */
    private Integer sortId;
    /**
     * 商品Id
     */
    private Integer goodsId;

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public GoodsSortParam() {
    }

    public GoodsSortParam(Integer sortId, Integer goodsId) {
        this.sortId = sortId;
        this.goodsId = goodsId;
    }

    @Override
    public String toString() {
        return "GoodsSortParam{" +
                "sortId=" + sortId +
                ", goodsId=" + goodsId +
                '}';
    }
}
