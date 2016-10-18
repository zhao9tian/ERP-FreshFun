package com.quxin.freshfun.test;

import com.quxin.freshfun.model.goods.GoodsSortParam;
import com.quxin.freshfun.service.goods.GoodsSortService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

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
        System.out.println(goodsSortService.querySortGoodsById(58));
    }

    @Test
    public void querySortGoods() {
        System.out.println(goodsSortService.querySortGoods());
    }

    @Test
    public void save(){
        List<GoodsSortParam> allSort = new ArrayList<>();
        GoodsSortParam g1 = new GoodsSortParam();
        g1.setSortId(111);
        g1.setGoodsId(222);
        allSort.add(g1);
        GoodsSortParam g2 = new GoodsSortParam();
        g2.setSortId(111);
        g2.setGoodsId(222);
        allSort.add(g2);
        goodsSortService.addAllGoodsSort(allSort);
    }
}
