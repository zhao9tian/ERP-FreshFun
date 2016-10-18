package com.quxin.freshfun.model.goods;

public class GoodsPOJO {
   /**商品ID*/
    private Integer id ;
    /**商品名称*/
    private String goodsName;
    /**商品首页图片*/
    private String goodsImg;
    /**商品单价*/
    private Integer goodsShopPrice;
    /**商品成本价*/
    private Integer goodsCost;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getGoodsShopPrice() {
        return goodsShopPrice;
    }

    public void setGoodsShopPrice(Integer goodsShopPrice) {
        this.goodsShopPrice = goodsShopPrice;
    }

    public Integer getGoodsCost() {
        return goodsCost;
    }

    public void setGoodsCost(Integer goodsCost) {
        this.goodsCost = goodsCost;
    }

    @Override
    public String toString() {
        return "GoodsPOJO{" +
                "id=" + id +
                ", goodsName='" + goodsName + '\'' +
                ", goodsImg='" + goodsImg + '\'' +
                ", goodsShopPrice=" + goodsShopPrice +
                ", goodsCost=" + goodsCost +
                '}';
    }
}