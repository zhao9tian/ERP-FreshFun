package com.quxin.freshfun.model.goods;

import java.util.List;

/**
 * 专题对象
 * Created by qucheng on 2016/10/28.
 */
public class ThemePOJO {
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
    /**创建时间*/
    private Long created;
    /**编辑时间*/
    private Long updated;
    /**商品Idlist*/
    private String goodsIdList;

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

    public String getGoodsIdList() {
        return goodsIdList;
    }

    public void setGoodsIdList(String goodsIdList) {
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
                ", created=" + created +
                ", updated=" + updated +
                ", goodsIdList=" + goodsIdList +
                '}';
    }
}
