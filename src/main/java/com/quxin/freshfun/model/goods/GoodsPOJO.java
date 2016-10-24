package com.quxin.freshfun.model.goods;

public class GoodsPOJO {
    /**
     * 商品ID
     */
    private Long goodsId;
    /**
     * 商品名称--也叫商品标题
     */
    private String goodsName;
    /**
     * 商品副标题
     */
    private String title;
    /**
     * 悦选小编说
     */
    private String ffunsaid;
    /**
     * 商品首页图片 index_img
     */
    private String goodsImg;
    /**
     * 商品单价
     */
    private Integer goodsShopPrice;
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
    private String carouseImg;
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
    private Integer catalog1;
    /**
     * 二级类目
     */
    private Integer catalog2;
    /**
     * 三级类目
     */
    private Integer catalog3;
    /**
     * 四级类目
     */
    private Integer catalog4;

    /**
     * 是否在B端销售
     */
    private Integer isBSale;
    /**
     * 是否被代理
     */
    private Integer isAgent;
    /**
     * 商户Id
     */
    private Long storeId;
    /**
     * 代理Id
     */
    private Long agentId;
    /**
     * 代理费用
     */
    private Integer agentMoney;
    /**
     * 产品优势
     */
    private String goodsStrength;
    /**
     * 代理优势
     */
    private String agentStrength;
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

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFfunsaid() {
        return ffunsaid;
    }

    public void setFfunsaid(String ffunsaid) {
        this.ffunsaid = ffunsaid;
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

    public String getCarouseImg() {
        return carouseImg;
    }

    public void setCarouseImg(String carouseImg) {
        this.carouseImg = carouseImg;
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

    public Integer getCatalog1() {
        return catalog1;
    }

    public void setCatalog1(Integer catalog1) {
        this.catalog1 = catalog1;
    }

    public Integer getCatalog2() {
        return catalog2;
    }

    public void setCatalog2(Integer catalog2) {
        this.catalog2 = catalog2;
    }

    public Integer getCatalog3() {
        return catalog3;
    }

    public void setCatalog3(Integer catalog3) {
        this.catalog3 = catalog3;
    }

    public Integer getCatalog4() {
        return catalog4;
    }

    public void setCatalog4(Integer catalog4) {
        this.catalog4 = catalog4;
    }

    public Integer getIsBSale() {
        return isBSale;
    }

    public void setIsBSale(Integer isBSale) {
        this.isBSale = isBSale;
    }

    public Integer getIsAgent() {
        return isAgent;
    }

    public void setIsAgent(Integer isAgent) {
        this.isAgent = isAgent;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public Integer getAgentMoney() {
        return agentMoney;
    }

    public void setAgentMoney(Integer agentMoney) {
        this.agentMoney = agentMoney;
    }

    public String getGoodsStrength() {
        return goodsStrength;
    }

    public void setGoodsStrength(String goodsStrength) {
        this.goodsStrength = goodsStrength;
    }

    public String getAgentStrength() {
        return agentStrength;
    }

    public void setAgentStrength(String agentStrength) {
        this.agentStrength = agentStrength;
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
                ", goodsName='" + goodsName + '\'' +
                ", title='" + title + '\'' +
                ", ffunsaid='" + ffunsaid + '\'' +
                ", goodsImg='" + goodsImg + '\'' +
                ", goodsShopPrice=" + goodsShopPrice +
                ", originPrice=" + originPrice +
                ", goodsCost=" + goodsCost +
                ", carouseImg='" + carouseImg + '\'' +
                ", detailImg='" + detailImg + '\'' +
                ", saleNum=" + saleNum +
                ", stockNum=" + stockNum +
                ", isOnSale=" + isOnSale +
                ", catalog1=" + catalog1 +
                ", catalog2=" + catalog2 +
                ", catalog3=" + catalog3 +
                ", catalog4=" + catalog4 +
                ", isBSale=" + isBSale +
                ", isAgent=" + isAgent +
                ", storeId=" + storeId +
                ", agentId=" + agentId +
                ", agentMoney=" + agentMoney +
                ", goodsStrength='" + goodsStrength + '\'' +
                ", agentStrength='" + agentStrength + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", goodsStandardPOJO=" + goodsStandardPOJO +
                '}';
    }
}