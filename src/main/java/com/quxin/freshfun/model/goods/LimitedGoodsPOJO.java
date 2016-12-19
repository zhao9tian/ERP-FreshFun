package com.quxin.freshfun.model.goods;

/**
 * 限量商品
 * Created by qucheng on 16/12/14.
 */
public class LimitedGoodsPOJO {

    /**
     * 限量购商品ID
     */
    private Long limitedGoodsId ;
    /**
     * 限量购价格 {"discountPrice":"2900"}
     */
    private String limitedPrice ;

    /**
     * 限量购实时库存
     */
    private Integer limitedRealStock;

    /**
     * 限时购总库存
     */
    private Integer limitedStock;

    /**
     * 创建时间
     */
    private Long created ;


    /**
     * 修改时间
     */
    private Long updated ;

    //**********************************附加商品属性*****************************************

    /**
     * 商品title
     */
    private String goodsTitle ;

    /**
     * 售价
     */
    private Integer shopPrice;

    /**
     * 销量
     */
    private Integer goodsLeaveStock;

    public Integer getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(Integer shopPrice) {
        this.shopPrice = shopPrice;
    }


    public Integer getGoodsLeaveStock() {
        return goodsLeaveStock;
    }

    public void setGoodsLeaveStock(Integer goodsLeaveStock) {
        this.goodsLeaveStock = goodsLeaveStock;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public Long getLimitedGoodsId() {
        return limitedGoodsId;
    }

    public void setLimitedGoodsId(Long limitedGoodsId) {
        this.limitedGoodsId = limitedGoodsId;
    }

    public String getLimitedPrice() {
        return limitedPrice;
    }

    public void setLimitedPrice(String limitedPrice) {
        this.limitedPrice = limitedPrice;
    }

    public Integer getLimitedRealStock() {
        return limitedRealStock;
    }

    public void setLimitedRealStock(Integer limitedRealStock) {
        this.limitedRealStock = limitedRealStock;
    }

    public Integer getLimitedStock() {
        return limitedStock;
    }

    public void setLimitedStock(Integer limitedStock) {
        this.limitedStock = limitedStock;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }
}
