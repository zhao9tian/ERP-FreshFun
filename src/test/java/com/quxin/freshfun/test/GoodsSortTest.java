package com.quxin.freshfun.test;

import com.quxin.freshfun.controller.goods.GoodsSortController;
import com.quxin.freshfun.model.goods.GoodsSelectionPOJO;
import com.quxin.freshfun.model.goods.ThemePOJO;
import com.quxin.freshfun.service.goods.GoodsSortService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qucheng on 2016/10/17.
 */
public class GoodsSortTest extends TestBase{


    private final Logger logger = LoggerFactory.getLogger(getClass());

    private GoodsSortService goodsSortService;

    private GoodsSortController goodsSortController;

    @Before
    public void setUp() throws Exception {
        goodsSortService = getContext().getBean("goodsSortService", GoodsSortService.class);
        goodsSortController = getContext().getBean("goodsSortController", GoodsSortController.class);
    }


    @After
    public void tearDown() throws Exception {
        getContext().close();
    }

    @Test
    public void querySortGoodsById() {
        System.out.println(goodsSortService.querySortGoodsById(58L));
    }

    @Test
    public void querySortGoods() {
        System.out.println(goodsSortService.querySortGoods());
    }





    @Test
    public void addThemeValue(){
        List<Long> themeSort = new ArrayList<>();
        themeSort.add(1L);
        themeSort.add(2L);//查重,要不要校验所有的Id
        themeSort.add(3L);
        System.out.println(goodsSortService.addThemeSort(themeSort));
    }

    @Test
    public void queryThemeValue(){
        List<ThemePOJO> themePOJOs = goodsSortService.queryThemeSort();
        System.out.println(themePOJOs);
     }

    @Test
    public void addSelectionValue(){
        Map<String , Object> selection = new HashMap<>();
        selection.put("goodsId",9);
        selection.put("img","http:this is a jpg.jpg");
//        Map<String , Object> selection1 = new HashMap<>();
//        selection1.put("goodsId",1L);
//        selection1.put("img","http:this is a jpg.jpg");
        List<Map<String , Object>> selectionSort = new ArrayList<>();
        selectionSort.add(selection);
//        selectionSort.add(selection1);
        System.out.println(goodsSortService.addSelectionSort(selectionSort));
    }

    @Test
    public void querySelectionValue(){
        List<GoodsSelectionPOJO> goodsSelections = goodsSortService.querySelectionSort();
        System.out.println(goodsSelections);
    }

    @Test
    public void AddBannerValue(){
        List<Map<String , Object>> bannerSort = new ArrayList<>();
        Map<String , Object> map = new HashMap<>();
        map.put("themeId", 1L);
        map.put("img","http://adadad,jpg");
        map.put("url","haha");
        bannerSort.add(map);
        System.out.println(goodsSortService.addBannerSort(bannerSort));
    }


    @Test
    public void queryBannerValue(){
        System.out.println(goodsSortService.queryBannerSort());
    }

    public static void main(String[] args) {
        Integer a = 1;
        Long a1 = (long)a;
        System.out.println(a1);
    }


    /***********************************************controller*****************************************************/


    @Test
    public void queryGoodsSort(){
        System.out.println(goodsSortController.queryGoodsSortList());
    }
    @Test
    public void addGoodsSort(){

        Map<String , List<Integer>> goodsSortParam = new HashMap<>();
        List<Integer> goodsSort = new ArrayList<>();
        goodsSort.add(9);
//        goodsSort.add(1);
//        goodsSort.add(1);
        goodsSortParam.put("allSort",goodsSort);
        System.out.println(goodsSortController.saveAllGoodsSort(goodsSortParam));
    }



    @Test
    public void querySelectionCtrl(){
        System.out.println(goodsSortController.querySelection());
    }

    @Test
    public void addSelectionCtrl(){
        Map<String , List<Map<String, Object>>> sortSelection = new HashMap<>();
        List<Map<String , Object>> sort = new ArrayList<>();
        Map<String , Object> value = new HashMap<>();
        value.put("goodsId",9);
        value.put("selectionImg","http://hahaha.jpg");
        Map<String , Object> value2 = new HashMap<>();
        value2.put("goodsId",25);
        value2.put("selectionImg","http://hahaha.jpg");
        sort.add(value);
        sort.add(value2);
        sortSelection.put("selectionSort" , sort);
        System.out.println(goodsSortController.addSelection(sortSelection));
    }

    @Test
    public void queryBannerCtrl(){
        System.out.println(goodsSortController.queryBannerSort());
    }

    @Test
    public void addBannerCtrl(){
        Map<String , List<Map<String, Object>>> banner = new HashMap<>();
        List<Map<String , Object>> sort = new ArrayList<>();
        Map<String , Object> value = new HashMap<>();
        value.put("themeId",1);
        value.put("img","http://hahaha.jpg");
        value.put("url","http://hahaha.do");
        Map<String , Object> value2 = new HashMap<>();
        value2.put("themeId",2);
        value2.put("img","http://hahaha.jpg");
        value2.put("url","http://hahaha.do");
        sort.add(value);
        sort.add(value2);
        banner.put("bannerSort" , sort);
        System.out.println(goodsSortController.saveBannerSort(banner));
    }


}
