package com.quxin.freshfun.model.goods;

public class GoodsPOJO {
    /**
     * 商品ID
     */
    private Long goodsId;
    /**
     * 商品标题
     */
    private String title;
    /**
     * 商品副标题
     */
    private String subtitle;
    /**
     * 悦选小编说
     */
    private String goodsDes;
    /**
     * 商品首页图片 index_img
     */
    private String goodsImg;
    /**
     * 商品单价
     */
    private Integer shopPrice;
    /**
     * 原价
     */
    private Integer originPrice;
    /**
     * 商品成本价
     */
    private Integer goodsCost;
    /**
     * 轮播图片
     */
    private String carouselImg;
    /**
     * 详情图片
     */
    private String detailImg;
    /**
     * 销量
     */
    private Integer saleNum;
    /**
     * 库存
     */
    private Integer stockNum;
    /**
     * 是否上架 0:上  1:下
     */
    private Integer isOnSale;
    /**
     * 一级类目 食品
     */
    private Integer catagory1;
    /**
     * 二级类目
     */
    private Integer catagory2;
    /**
     * 三级类目
     */
    private Integer catagory3;
    /**
     * 四级类目
     */
    private Integer catagory4;

    /**
     * 商户Id
     */
    private Long shopId;

    private Long appId;
    /**
     * 创建时间
     */
    private Long created;
    /**
     * 编辑时间
     */
    private Long updated;

    private GoodsStandardPOJO goodsStandardPOJO;

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

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getGoodsDes() {
        return goodsDes;
    }

    public void setGoodsDes(String goodsDes) {
        this.goodsDes = goodsDes;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
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

    public Integer getGoodsCost() {
        return goodsCost;
    }

    public void setGoodsCost(Integer goodsCost) {
        this.goodsCost = goodsCost;
    }

    public String getCarouselImg() {
        return carouselImg;
    }

    public void setCarouselImg(String carouselImg) {
        this.carouselImg = carouselImg;
    }

    public String getDetailImg() {
        return detailImg;
    }

    public void setDetailImg(String detailImg) {
        this.detailImg = detailImg;
    }

    public Integer getSaleNum() {
        return saleNum;
    }

    public void setSaleNum(Integer saleNum) {
        this.saleNum = saleNum;
    }

    public Integer getStockNum() {
        return stockNum;
    }

    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }

    public Integer getIsOnSale() {
        return isOnSale;
    }

    public void setIsOnSale(Integer isOnSale) {
        this.isOnSale = isOnSale;
    }

    public Integer getCatagory1() {
        return catagory1;
    }

    public void setCatagory1(Integer catagory1) {
        this.catagory1 = catagory1;
    }

    public Integer getCatagory2() {
        return catagory2;
    }

    public void setCatagory2(Integer catagory2) {
        this.catagory2 = catagory2;
    }

    public Integer getCatagory3() {
        return catagory3;
    }

    public void setCatagory3(Integer catagory3) {
        this.catagory3 = catagory3;
    }

    public Integer getCatagory4() {
        return catagory4;
    }

    public void setCatagory4(Integer catagory4) {
        this.catagory4 = catagory4;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
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

    public GoodsStandardPOJO getGoodsStandardPOJO() {
        return goodsStandardPOJO;
    }

    public void setGoodsStandardPOJO(GoodsStandardPOJO goodsStandardPOJO) {
        this.goodsStandardPOJO = goodsStandardPOJO;
    }

    @Override
    public String toString() {
        return "GoodsPOJO{" +
                "goodsId=" + goodsId +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", goodsDes='" + goodsDes + '\'' +
                ", goodsImg='" + goodsImg + '\'' +
                ", shopPrice=" + shopPrice +
                ", originPrice=" + originPrice +
                ", goodsCost=" + goodsCost +
                ", carouselImg='" + carouselImg + '\'' +
                ", detailImg='" + detailImg + '\'' +
                ", saleNum=" + saleNum +
                ", stockNum=" + stockNum +
                ", isOnSale=" + isOnSale +
                ", catagory1=" + catagory1 +
                ", catagory2=" + catagory2 +
                ", catagory3=" + catagory3 +
                ", catagory4=" + catagory4 +
                ", shopId=" + shopId +
                ", appId=" + appId +
                ", created=" + created +
                ", updated=" + updated +
                ", goodsStandardPOJO=" + goodsStandardPOJO +
                '}';
    }
}