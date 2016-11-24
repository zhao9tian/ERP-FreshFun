package com.quxin.freshfun.test;

import com.quxin.freshfun.controller.withdraw.WithdrawController;
import com.quxin.freshfun.model.flow.FlowPOJO;
import com.quxin.freshfun.model.withdraw.WithdrawPOJO;
import com.quxin.freshfun.service.flow.FlowService;
import com.quxin.freshfun.service.withdraw.WithdrawService;
import org.junit.*;
import org.junit.Test;

/**
 * 商品单元测试
 * Created by qucheng on 2016/10/18.
 */
public class WithdrawTest extends TestBase {


    private WithdrawService withdrawService;

    @Before
    public void setUp() throws Exception {
        withdrawService = getContext().getBean("withdrawService", WithdrawService.class);
    }


    @After
    public void tearDown() throws Exception {
        getContext().close();
    }

    @org.junit.Test
    public void queryUserFlows(){
        System.out.println(withdrawService.queryAvailableMoney(90010L));
    }

    @org.junit.Test
    public void queryWithdrawListByAppId(){

        System.out.println(withdrawService.queryWithdrawListByAppId(888888L , 0 ,2));
    }

    @org.junit.Test
    public void addWithdraw(){
        WithdrawPOJO withdrawPOJO = new WithdrawPOJO();
        withdrawPOJO.setAppId(888888L);
        withdrawPOJO.setUserId(1234L);
        withdrawPOJO.setAccountType(10);
        withdrawPOJO.setAccountNum("qucheng12345");
        withdrawPOJO.setWithdrawMoney(9000);
//        withdrawPOJO.set

        System.out.println(withdrawService.addWithdraw(withdrawPOJO));
    }

    @Test
    public void queryCountWithdrawByAppId(){
        System.out.println(withdrawService.queryCountWithdrawByAppId(888888L));
    }
    @Test
    public void queryTotalMoney(){
        System.out.println(withdrawService.queryTotalMoney(888888L));
    }
    @Test
    public void queryUnrecordMoney(){
        System.out.println(withdrawService.queryUnrecordMoney(888888L));
    }
}
