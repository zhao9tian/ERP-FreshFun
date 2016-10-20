package com.quxin.freshfun.test;

import com.quxin.freshfun.controller.goods.GoodsController;
import com.quxin.freshfun.service.goods.GoodsService;
import com.quxin.freshfun.service.goods.GoodsTypeService;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by qucheng on 2016/10/18.
 */
public class GoodsTest extends TestBase {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private GoodsTypeService goodsTypeService;

    private GoodsController goodsController;

    @Before
    public void setUp() throws Exception {
        goodsTypeService = getContext().getBean("goodsTypeService", GoodsTypeService.class);
        goodsController = getContext().getBean("goodsController", GoodsController.class);
    }


    @After
    public void tearDown() throws Exception {
        getContext().close();
    }

    @org.junit.Test
    public void TestGoods(){
        goodsTypeService.insertTest();
//        System.out.println(goodsController.getCategoryList());
    }

}
