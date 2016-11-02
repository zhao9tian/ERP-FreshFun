package com.quxin.freshfun.model.goods;

public class GoodsBaseOut {
    /**
     * 商品ID
     */
    private Long goodsId;
    /**
     * 商品标题
     */
    private String title;
    /**
     * 商品首页图片 index_img
     */
    private String img;
    /**
     * 商品单价
     */
    private Integer shopPrice;
    /**
     * 原价
     */
    private Integer originPrice;
    /**
     * 销量
     */
    private Integer saleNum;
    /**
     * 库存
     */
    private Integer storeNum;
    /**
     * 是否上架 0:下  1:上
     */
    private Integer isOnSale;
    /**
     * 创建时间
     */
    private Long createTime;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(Integer shopPrice) {
        this.shopPrice = shopPrice;
    }

    public Integer getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(Integer originPrice) {
        this.originPrice = originPrice;
    }

    public Integer getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(Integer saleNum) {
        this.saleNum = saleNum;
    }

    public Integer getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(Integer storeNum) {
        this.storeNum = storeNum;
    }

    public Integer getIsOnSale() {
        return isOnSale;
    }

    public void setIsOnSale(Integer isOnSale) {
        this.isOnSale = isOnSale;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "GoodsBaseOut{" +
                "goodsId=" + goodsId +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", shopPrice=" + shopPrice +
                ", originPrice=" + originPrice +
                ", saleNum=" + saleNum +
                ", storeNum=" + storeNum +
                ", isOnSale=" + isOnSale +
                ", createTime=" + createTime +
                '}';
    }
}