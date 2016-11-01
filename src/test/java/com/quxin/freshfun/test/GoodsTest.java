package com.quxin.freshfun.test;

import com.quxin.freshfun.model.goods.GoodsPOJO;
import com.quxin.freshfun.model.goods.GoodsStandardPOJO;
import com.quxin.freshfun.service.goods.GoodsService;
import org.junit.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 商品单元测试
 * Created by qucheng on 2016/10/18.
 */
public class GoodsTest extends TestBase {


//    private GoodsController goodsController;
    private GoodsService goodsService;

    @Before
    public void setUp() throws Exception {
//        goodsController = getContext().getBean("goodsController", GoodsController.class);
        goodsService = getContext().getBean("goodsService", GoodsService.class);
    }


    @After
    public void tearDown() throws Exception {
        getContext().close();
    }

    @org.junit.Test
    public void AddGoods(){
        GoodsPOJO goodsPOJO = createGoods();
        goodsService.addGoods(goodsPOJO);
    }

    @org.junit.Test
    public void modifyGoods(){
        GoodsPOJO goodsPOJO = createGoods();
        goodsService.modifyGoods(goodsPOJO);
    }

    @org.junit.Test
    public void queryGoodsById(){
        System.out.println(goodsService.queryGoodsByGoodsId(26L));
    }

    @org.junit.Test
    public void queryAllGoods(){
        Map<String,Object> qc = new HashMap<>();
        qc.put("subTitle","阿");
        qc.put("isOnSale" , 0);
        qc.put("orderByCreate",1);
        System.out.println(goodsService.queryAllGoods(qc));

    }

    @org.junit.Test
    public void changeStatus(){
        System.out.println(goodsService.changeStatus(26L,10,2));//1.2
    }

    @org.junit.Test
    public void toTheme(){
        System.out.println(goodsService.goodsToTheme(9L,2L));
    }

    @org.junit.Test
    public void removeGoodsByGoodsId(){
        System.out.println(goodsService.queryGoodsByGoodsId(26L));
        System.out.println(goodsService.removeGoodsByGoodsId(26L));
        System.out.println(goodsService.queryGoodsByGoodsId(26L));
    }

    private GoodsPOJO createGoods(){
        GoodsPOJO goodsPOJO = new GoodsPOJO();
        goodsPOJO.setGoodsId(9L);
        goodsPOJO.setTitle("阿玛尼1");
        goodsPOJO.setSubtitle("淘宝上的阿玛尼1");
        goodsPOJO.setOriginPrice(30000001);
        goodsPOJO.setShopPrice(3000001);
        goodsPOJO.setGoodsDes("谁买谁傻逼1");
        goodsPOJO.setCatagory1(2);
        goodsPOJO.setCatagory2(102);
        goodsPOJO.setCatagory3(10201);
        goodsPOJO.setCatagory4(1020101);
        goodsPOJO.setGoodsCost(30001);
        goodsPOJO.setGoodsImg("http://freshfunpic.oss-cn-hangzhou.aliyuncs.com/image/20161027/035b1f65-e203-4897-b2c4-b77ac3aa82a0.jpg");
        goodsPOJO.setAppId(138383L);
        goodsPOJO.setShopId(1438L);
        goodsPOJO.setSaleNum(1100);
        goodsPOJO.setStockNum(120);
        goodsPOJO.setIsOnSale(1);
        goodsPOJO.setCreated(System.currentTimeMillis()/1000);
        goodsPOJO.setUpdated(System.currentTimeMillis()/1000);
//        goodsPOJO.setDetailImg("1http://freshfunpic.oss-cn-hangzhou.aliyuncs.com/image/20161027/2d5b7b10-a74a-45ca-8759-5dfb953d22ed.png," +
//                "1http://freshfunpic.oss-cn-hangzhou.aliyuncs.com/image/20161027/540eb9df-32b0-440e-8837-7aa00a991a86.png," +
//                "1http://freshfunpic.oss-cn-hangzhou.aliyuncs.com/image/20161027/8c89631c-66e1-4737-8e6d-de7cdf6f44ea.png," +
//                "1http://freshfunpic.oss-cn-hangzhou.aliyuncs.com/image/20161027/f19101e1-2231-4bc8-b1f6-2d9a7c743f88.jpg," +
//                "1http://freshfunpic.oss-cn-hangzhou.aliyuncs.com/image/20161027/fbf882ee-7ec5-400b-9f1a-08e665696d63.jpg," +
//                "1http://freshfunpic.oss-cn-hangzhou.aliyuncs.com/image/20161027/b0dd710b-6baa-4f3c-bd65-7fd6bd8e1790.jpg"
//        );
//        goodsPOJO.setCarouselImg("1http://freshfunpic.oss-cn-hangzhou.aliyuncs.com/image/20161027/21927c1d-ba3d-44a8-bad3-9e56c75d65d7.jpg," +
//                "1http://freshfunpic.oss-cn-hangzhou.aliyuncs.com/image/20161027/5b8aeaa7-8ca9-4f17-963b-026a09d38513.jpg,"+
//                "1http://freshfunpic.oss-cn-hangzhou.aliyuncs.com/image/20161027/8c89631c-66e1-4737-8e6d-de7cdf6f44ea.png," +
//                "1http://freshfunpic.oss-cn-hangzhou.aliyuncs.com/image/20161027/f19101e1-2231-4bc8-b1f6-2d9a7c743f88.jpg," +
//                "1http://freshfunpic.oss-cn-hangzhou.aliyuncs.com/image/20161027/fbf882ee-7ec5-400b-9f1a-08e665696d63.jpg," +
//                "1http://freshfunpic.oss-cn-hangzhou.aliyuncs.com/image/20161027/b0dd710b-6baa-4f3c-bd65-7fd6bd8e1790.jpg");
        GoodsStandardPOJO goodsStandardPOJO = createGoodsStandard();
        goodsStandardPOJO.setGoodsId(goodsPOJO.getGoodsId());
        goodsPOJO.setGoodsStandardPOJO(goodsStandardPOJO);
        return goodsPOJO;
    }

    private GoodsStandardPOJO createGoodsStandard(){
        GoodsStandardPOJO goodsStandardPOJO = new GoodsStandardPOJO(
                "爱马仕1","12","13","14","15","16","17","18","19","110",
                "11","12","13","114","115","16","17","18","19","110",
                "11","11","11","11","111","111","11","11","19","110",
                "11","12","13","14","15","116","17","18");
        return goodsStandardPOJO;
    }


}
