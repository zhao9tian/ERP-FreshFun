package com.quxin.freshfun.test;

import com.alibaba.fastjson.JSON;
import com.quxin.freshfun.controller.goods.GoodsController;
import com.quxin.freshfun.model.flow.FlowPOJO;
import com.quxin.freshfun.model.goods.GoodsPOJO;
import com.quxin.freshfun.model.goods.GoodsStandardPOJO;
import com.quxin.freshfun.service.flow.FlowService;
import com.quxin.freshfun.service.goods.GoodsService;
import org.junit.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品单元测试
 * Created by qucheng on 2016/10/18.
 */
public class FlowTest extends TestBase {


    private FlowService flowService;

    @Before
    public void setUp() throws Exception {
        flowService = getContext().getBean("flowService", FlowService.class);
    }


    @After
    public void tearDown() throws Exception {
        getContext().close();
    }

    @org.junit.Test
    public void AddFlow(){
        FlowPOJO flow = new FlowPOJO();
        flow.setAppId(90010L);
        flow.setFlowType(0);
        flow.setFlowMoney(9000);
        flow.setOrderId(13566614L);
        flowService.addFlow(flow);
    }

    @org.junit.Test
    public void flows(){
        System.out.println(flowService.queryFlowListByAppId(90010L , 0 , 5));
    }


    @Test
    public void flow(){

    }

}
