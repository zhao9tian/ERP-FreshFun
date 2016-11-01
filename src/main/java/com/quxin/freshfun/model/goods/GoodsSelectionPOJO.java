package com.quxin.freshfun.model.goods;

/**
 * 精选
 * Created by qucheng on 2016/10/28.
 */
public class BannerOut {
    /**
     * 商品基本信息
     */
    private GoodsPOJO goodsPOJO;

    /**
     * 精选图片地址
     */
    private String selectionImg;

    public GoodsPOJO getGoodsPOJO() {
        return goodsPOJO;
    }

    public void setGoodsPOJO(GoodsPOJO goodsPOJO) {
        this.goodsPOJO = goodsPOJO;
    }

    public String getSelectionImg() {
        return selectionImg;
    }

    public void setSelectionImg(String selectionImg) {
        this.selectionImg = selectionImg;
    }

    @Override
    public String toString() {
        return "GoodsSelectionPOJO{" +
                "goodsPOJO=" + goodsPOJO +
                ", selectionImg='" + selectionImg + '\'' +
                '}';
    }
}
