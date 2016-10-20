package com.quxin.freshfun.model.goods;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by gsix on 2016/10/21.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GoodsOrderOut {
    /**商品ID*/
    private Integer id ;
    /**商品名称--也叫商品标题*/
    private String goodsName;
    /**商品首页图片*/
    private String goodsImg;
    /**商品单价*/
    private Integer goodsShopPrice;
    /**商品成本价*/
    private Integer goodsCost;
    /**
     * 商品描述
     */
    private String goodsDes;
    /**
     * 前端显示金额
     */
    private String marketMoney;

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

    public String getGoodsDes() {
        return goodsDes;
    }

    public void setGoodsDes(String goodsDes) {
        this.goodsDes = goodsDes;
    }

    public String getMarketMoney() {
        return marketMoney;
    }

    public void setMarketMoney(String marketMoney) {
        this.marketMoney = marketMoney;
    }
}
