package com.quxin.freshfun.dao;

import com.quxin.freshfun.model.goods.ThemePOJO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 专题DB
 * Created by qucheng on 2016/10/28.
 */
public interface GoodsThemeMapper {

    /**
     * 新增专题
     * @param themePOJO 专题对象
     * @return 插入记录数
     */
    Integer insertTheme(ThemePOJO themePOJO);

    /**
     * 编辑专题
     * @param themePOJO 专题对象
     * @return 记录数
     */
    Integer updateTheme(ThemePOJO themePOJO);

    /**
     * 删除专题
     * @param themeId 专题Id
     * @return 返回删除数量
     */
    Integer deleteThemeByThemeId(Long themeId);

    /**
     * 查询专题
     * @param themeId 专题Id
     * @return 专题对象
     */
    ThemePOJO selectThemeByThemeId(Long themeId);

    /**
     * 查询专题列表
     * @return 专利列表
     */
    List<ThemePOJO> selectThemeList();

    /**
     * 通过专题名称查询专题数量
     * @param themeName 专题名称
     * @return 专题数量
     */
    Integer selectCountByThemeName(@Param("themeName") String themeName);
}
