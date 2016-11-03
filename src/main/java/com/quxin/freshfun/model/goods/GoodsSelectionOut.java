package com.quxin.freshfun.model.goods;

/**
 * 精选出参
 * Created by qucheng on 2016/10/28.
 */
public class GoodsSelectionOut {
    /**
     * 商品Id
     */
    private Long goodsId;

    /**
     * 商品主题
     */
    private String goodsTitle ;
    /**
     * 精选图片地址
     */
    private String selectionImg;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getSelectionImg() {
        return selectionImg;
    }

    public void setSelectionImg(String selectionImg) {
        this.selectionImg = selectionImg;
    }

    @Override
    public String toString() {
        return "GoodsSelectionOut{" +
                "goodsId=" + goodsId +
                ", goodsTitle='" + goodsTitle + '\'' +
                ", selectionImg='" + selectionImg + '\'' +
                '}';
    }
}
