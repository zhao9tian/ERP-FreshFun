package com.quxin.freshfun.model.goods;

/**
 * banner排序出参实体
 * Created by qucheng on 2016/10/28.
 */
public class BannerOut {

    private Long themeId;

    private String themeName;

    private String img;

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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
                ", img='" + img + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
