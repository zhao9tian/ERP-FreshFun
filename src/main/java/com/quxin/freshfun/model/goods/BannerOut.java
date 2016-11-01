package com.quxin.freshfun.model.goods;

/**
 * banner排序出参实体
 * Created by qucheng on 2016/10/28.
 */
public class BannerOut {

    private Long themeId;

    private String themeName;

    private String bannerImg;

    private String url;

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

    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "BannerOut{" +
                "themeId='" + themeId + '\'' +
                ", themeName='" + themeName + '\'' +
                ", bannerImg='" + bannerImg + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
