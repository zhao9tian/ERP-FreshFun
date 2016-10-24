package com.quxin.freshfun.test;

import com.quxin.freshfun.service.goods.GoodsSortService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by qucheng on 2016/10/17.
 */
public class GoodsSortTest extends TestBase{


    private final Logger logger = LoggerFactory.getLogger(getClass());

    private GoodsSortService goodsSortService;

    @Before
    public void setUp() throws Exception {
        goodsSortService = getContext().getBean("goodsSortService", GoodsSortService.class);
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
    public void save(){
    }
}
