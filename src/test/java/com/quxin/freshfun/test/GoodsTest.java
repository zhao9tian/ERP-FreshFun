package com.quxin.freshfun.test;

import com.quxin.freshfun.controller.goods.GoodsController;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 商品单元测试
 * Created by qucheng on 2016/10/18.
 */
public class GoodsTest extends TestBase {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    private GoodsController goodsController;

    @Before
    public void setUp() throws Exception {
        goodsController = getContext().getBean("goodsController", GoodsController.class);
    }


    @After
    public void tearDown() throws Exception {
        getContext().close();
    }

    @org.junit.Test
    public void TestGoods(){
        logger.error("打印在控制台的");
        logger.info("设置info才能在文件里面");
//        System.out.println(goodsController.getCategoryList());
    }

}
