package com.quxin.freshfun.model.goods;

import java.util.List;

/**
 * 专题对象,用于专题详情
 * Created by qucheng on 2016/10/29.
 */
public class ThemeParamOut {
    /**专题Id*/
    private Long themeId ;
    /**专题名称*/
    private String themeName;
    /**专题描述*/
    private String themeDes;
    /**专题图片*/
    private String themeImg;
    /**是否禁用*/
    private Integer isForbidden;
    /**商品Idlist*/
    private List<Long> goodsIdList;

    public Long getThemeId() {
        return themeId;
    }

    public void setThemeId(Long themeId) {
        this.themeId = themeId;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getThemeDes() {
        return themeDes;
    }

    public void setThemeDes(String themeDes) {
        this.themeDes = themeDes;
    }

    public String getThemeImg() {
        return themeImg;
    }

    public void setThemeImg(String themeImg) {
        this.themeImg = themeImg;
    }

    public Integer getIsForbidden() {
        return isForbidden;
    }

    public void setIsForbidden(Integer isForbidden) {
        this.isForbidden = isForbidden;
    }

    public List<Long> getGoodsIdList() {
        return goodsIdList;
    }

    public void setGoodsIdList(List<Long> goodsIdList) {
        this.goodsIdList = goodsIdList;
    }

    @Override
    public String toString() {
        return "ThemePOJO{" +
                "themeId=" + themeId +
                ", themeName='" + themeName + '\'' +
                ", themeDes='" + themeDes + '\'' +
                ", themeImg='" + themeImg + '\'' +
                ", isForbidden=" + isForbidden +
                ", goodsIdList=" + goodsIdList +
                '}';
    }
}
