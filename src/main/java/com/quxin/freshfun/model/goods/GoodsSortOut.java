package com.quxin.freshfun.model.goods;

/**
 * 商品排序页面显示对应实体
 * Created by qucheng on 2016/10/12.
 */
public class GoodsSortOut {

    /**商品ID*/
    private Integer goodsId;
    /**商品名称*/
    private String goodsName;
    /**商品首页图片*/
    private String goodsImg;
    /**商品单价*/
    private String goodsPrice;
    /**商品成本价*/
    private String goodsCost;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsCost() {
        return goodsCost;
    }

    public void setGoodsCost(String goodsCost) {
        this.goodsCost = goodsCost;
    }

    @Override
    public String toString() {
        return "GoodsSortOut{" +
                "goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsImg='" + goodsImg + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", goodsCost=" + goodsCost +
                '}';
    }
}
