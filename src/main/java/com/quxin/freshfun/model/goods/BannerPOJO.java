package com.quxin.freshfun.model.goods;

/**
 * banner实体
 * Created by qucheng on 2016/10/28.
 */
public class BannerPOJO {

    private ThemePOJO themePOJO;

    private String bannerImg;

    private String url ;

    public ThemePOJO getThemePOJO() {
        return themePOJO;
    }

    public void setThemePOJO(ThemePOJO themePOJO) {
        this.themePOJO = themePOJO;
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
        return "BannerPOJO{" +
                "themePOJO=" + themePOJO +
                ", bannerImg='" + bannerImg + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
