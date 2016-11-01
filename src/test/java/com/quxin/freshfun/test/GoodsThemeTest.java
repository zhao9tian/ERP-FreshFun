package com.quxin.freshfun.test;

import com.quxin.freshfun.controller.goods.GoodsThemeController;
import com.quxin.freshfun.model.goods.ThemePOJO;
import com.quxin.freshfun.service.goods.GoodsThemeService;
import org.junit.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 专题测试类
 * Created by qucheng on 2016/10/28.
 */
public class GoodsThemeTest extends TestBase{

    private GoodsThemeService themeService;

    private GoodsThemeController goodsThemeController;

    @Before
    public void setUp(){
        themeService = getContext().getBean("goodsThemeService",GoodsThemeService.class);
        goodsThemeController = getContext().getBean("goodsThemeController",GoodsThemeController.class);
    }

    @After
    public void after(){
        getContext().close();
    }

    @Test
    public void addTheme(){
        ThemePOJO themePOJO = new ThemePOJO();
        themePOJO.setThemeName("专题名");
        themePOJO.setThemeDes("专题描述");
        themePOJO.setThemeImg("http://freshfunpic.oss-cn-hangzhou.aliyuncs.com/image/20161027/035b1f65-e203-4897-b2c4-b77ac3aa82a0.jpg");
        themePOJO.setGoodsIdList("[1,2,3,4,5,6,7]");
        System.out.println(themeService.addTheme(themePOJO));
    }

    @Test
    public void modifyTheme(){
        ThemePOJO themePOJO = new ThemePOJO();
        themePOJO.setThemeId(1L);
        themePOJO.setThemeName("专题名1");
        themePOJO.setThemeDes("专题描述1");
        themePOJO.setIsForbidden(1);
        themePOJO.setThemeImg("1http://freshfunpic.oss-cn-hangzhou.aliyuncs.com/image/20161027/035b1f65-e203-4897-b2c4-b77ac3aa82a0.jpg");
        themePOJO.setGoodsIdList("[11,2,3,4,5,6,7]");
        System.out.println(themeService.modifyTheme(themePOJO));
    }


    @Test
    public void deleteTheme(){
        System.out.println(themeService.removeThemeByThemeId(1L));
    }

    @Test
    public void changeStatus(){
        System.out.println(themeService.changeThemeStatus(1L,0));
    }

    @Test
    public void queryThemeById(){
        System.out.println(themeService.queryThemeByThemeId(2L));
    }
    @Test
    public void queryThemeList(){
        System.out.println(themeService.queryThemeList());
    }

    /*************************************************************************/
    @Test
    public void saveTheme(){
//        List<Integer> goodsIds = new ArrayList<>();
//        goodsIds.add(9);
//        goodsIds.add(25);
        List<String> goodsIds = new ArrayList<>();
        goodsIds.add("9");
        goodsIds.add("25");
//        goodsIds.add(1L);
//        goodsIds.add(2L);
        Map<String , Map<String , Object>> mapParam = new HashMap<>();
        Map<String , Object> map = new HashMap<>();
        map.put("themeName" , "woshizhuanti2211");
        map.put("themeDes" , "woshizhuanti miaoshu");
        map.put("themeImg","woshitupiandizhi");
        map.put("goodsIds" , goodsIds);
//        map.put("goodsIds" , "[22]");
        mapParam.put("themeParam",map);
        System.out.println(goodsThemeController.saveTheme(mapParam));
    }

    @Test
    public void saveThemeSort(){
        Map<String , List<Long>> param = new HashMap<>();
        List<Long> themeIds = new ArrayList<>();
        themeIds.add(1L);
        themeIds.add(2L);
        param.put("themeSort" , themeIds);
        System.out.println(goodsThemeController.saveThemeSort(param));
    }

    @Test
    public void queryThemeSort(){
        System.out.println(goodsThemeController.queryThemeSort());
    }
}
