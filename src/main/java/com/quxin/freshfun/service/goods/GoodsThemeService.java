package com.quxin.freshfun.service.goods;

import com.quxin.freshfun.model.goods.ThemePOJO;

import java.util.List;

/**
 * 商品专题Service
 * Created by qucheng on 2016/10/28.
 */
public interface GoodsThemeService {

    /**
     * 保存专题
     * @param themePOJO 专题对象
     * @return 是否保存成功
     */
    Boolean addTheme(ThemePOJO themePOJO);

    /**
     * 查询商品专题
     * @param themeId 专题Id
     * @return 返回专题对象
     */
    ThemePOJO queryThemeByThemeId(Long themeId);

    /**
     * 编辑专题
     * @param themePOJO 专题对象
     * @return 是否编辑成功
     */
    Boolean modifyTheme(ThemePOJO themePOJO);

    /**
     * 删除专题
     * @param themeId 专题id
     * @return 是否删除成功
     */
    Boolean removeThemeByThemeId(Long themeId);

    /**
     * 启用,禁用
     * @param themeId 专题Id
     * @param themeStatus  0:禁用 1:启用
     * @return 是否成功
     */
    Boolean changeThemeStatus(Long themeId ,Integer themeStatus);

    /**
     * 查询专题列表
     * @return 返回专题列表
     */
    List<ThemePOJO> queryThemeList();

    /**
     * 校验专题名称是否重复
     * @param themeName 专题名称
     * @return true : 已经存在  false : 不存在
     */
    Boolean isExistThemeName(String themeName);

}
