package com.quxin.freshfun.model.goods;

public class GoodsPOJO {
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
    /**商品副标题*/
    private String title ;
    /**悦选小编说*/
    private String ffunsaid;
    /**原价*/
    private Integer originPrice;
    /**商品规格*/
    private Integer goods_standard;
    /**轮播图片*/
    private String carouseImg;
    /**详情图片*/
    private String detailImg;
    /**销量*/
    private Integer saleNum;
    /**库存*/
    private Integer stockNum;
    /**商户Id*/
    private Integer storeId;
    /**代理Id*/
    private Integer agentId;
    /**是否上架*/
    private Integer isOnSale;
    /**是否在B端销售*/
    private Integer is_b_sale;
    /**是否被代理*/
    private Integer is_agent;
    /**代理费用*/
    private Integer agentMoney;
    /**产品优势*/
    private String goodsStrength;
    /**代理优势*/
    private String agentStrength;
    /**商品编号*/
    private Integer goodsNo;
    /**创建时间*/
    private Long created ;
    /**编辑时间*/
    private Long updated;


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