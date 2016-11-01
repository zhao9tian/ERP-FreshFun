package com.quxin.freshfun.model.goods;

/**
 * 专题排序,用于专题排序
 * Created by qucheng on 2016/10/29.
 */
public class ThemeSortOut {
    /**专题Id*/
    private Long themeId ;
    /**专题名称*/
    private String themeName;

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

    @Override
    public String toString() {
        return "ThemeSortOut{" +
                "themeId=" + themeId +
                ", themeName='" + themeName + '\'' +
                '}';
    }
}
