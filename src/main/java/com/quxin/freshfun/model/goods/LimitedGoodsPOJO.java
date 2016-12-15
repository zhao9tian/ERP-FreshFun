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
     * 限量购价格
     */
    private String limitedPrice ;

    /**
     * 限量购销量
     */
    private Integer limitedSaleNum ;

    /**
     * 限时购库存
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

    public Integer getLimitedSaleNum() {
        return limitedSaleNum;
    }

    public void setLimitedSaleNum(Integer limitedSaleNum) {
        this.limitedSaleNum = limitedSaleNum;
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
