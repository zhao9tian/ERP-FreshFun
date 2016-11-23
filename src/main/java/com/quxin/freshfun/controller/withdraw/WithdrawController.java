package com.quxin.freshfun.controller.withdraw;

import com.alibaba.fastjson.JSON;
import com.quxin.freshfun.model.flow.FlowPOJO;
import com.quxin.freshfun.model.withdraw.WithdrawPOJO;
import com.quxin.freshfun.service.erpuser.ErpUserService;
import com.quxin.freshfun.service.flow.FlowService;
import com.quxin.freshfun.service.goods.GoodsService;
import com.quxin.freshfun.service.order.OrderService;
import com.quxin.freshfun.service.withdraw.WithdrawService;
import com.quxin.freshfun.utils.MoneyFormatUtils;
import com.quxin.freshfun.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 提现,交易流水
 * Created by qucheng on 16/11/22.
 */
@Controller
@RequestMapping("/withdraw")
public class WithdrawController {

    private static Logger logger = LoggerFactory.getLogger(WithdrawController.class);

    @Autowired
    private FlowService flowService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private WithdrawService withdrawService;

    @Autowired
    private ErpUserService erpUserService;

    /**
     * 查询用户流水
     *
     * @param currentPage 当前页
     * @param pageSize    页面大小
     * @param request     请求
     * @return 请求结果
     */
    @RequestMapping(value = "/queryUserFlows", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryUserFlows(Integer currentPage, Integer pageSize, HttpServletRequest request) {
        List<Map> flowlist = new ArrayList<>();
//        Long appId = erpUserService.

        //todo 参数校验 处理分页
        Long appId = 90010L;
//        if(appId == null){
//            return ResultUtil.fail(1004 , "appId为空");
//        }
        Integer count = flowService.queryCountByAppId(appId);
        if (count == null) {
            count = 0;
        }
        if (pageSize == null) {
            return ResultUtil.fail(1004, "pageSize为空");
        }
        Integer totalPage = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        if (currentPage == null) {
            return ResultUtil.fail(1004, "currentPage为空");
        } else if (currentPage == 0) {
            currentPage = 1;
        } else if (currentPage > totalPage) {
            currentPage = totalPage;
        }
        Integer start = (currentPage - 1) * pageSize;

        List<FlowPOJO> flows = flowService.queryFlowListByAppId(appId, start, pageSize);
        long now = System.currentTimeMillis();
        if (flows != null && flows.size() > 0) {
            for (FlowPOJO flow : flows) {
                Map<String, Object> map = new HashMap<>();
                map.put("tradingTime", flow.getCreated());
                Long goodsId = Long.valueOf(orderService.queryOrderDetailByOrderId(flow.getOrderId()).getGoodsId());
                map.put("content", goodsService.queryGoodsByGoodsId(goodsId).getTitle());//通过orderId查询商品title
                String flowMoney = "";
                if (flow.getFlowType() == 0) {
                    flowMoney = "+" + MoneyFormatUtils.getMoneyFromInteger(flow.getFlowMoney());
                } else if (flow.getFlowType() == 1) {
                    flowMoney = "-" + MoneyFormatUtils.getMoneyFromInteger(flow.getFlowMoney());
                }
                map.put("money", flowMoney);
                flowlist.add(map);
            }
        }
        System.out.println(System.currentTimeMillis() - now);
        Map<String, Object> data = new HashMap<>();
        data.put("flows", flowlist);
        data.put("totalPage", totalPage);
        data.put("total", count);
        return ResultUtil.success(data);
    }

    /**
     * 查询可提现金额
     *
     * @param request 请求
     * @return 返回结果
     */
    @RequestMapping(value = "/queryAvailableMoney", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryAvailableMoney(HttpServletRequest request) {
        Long appId = 90010L;//TODO
        if (appId != null) {
            Integer availableMoney = withdrawService.queryAvailableMoney(appId);
            Map<String, Object> data = new HashMap<>();
            data.put("availableMoney", MoneyFormatUtils.getMoneyFromInteger(availableMoney));
            return ResultUtil.success(data);
        } else {
            return ResultUtil.fail(1004, "无法获取到appId");
        }
    }


    @RequestMapping(value = "/queryWithdrawList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryWithdrawList(Integer currentPage, Integer pageSize, HttpServletRequest request) {
        List<Map> withdrawlist = new ArrayList<>();
//        Long appId = erpUserService.

        //todo 参数校验 处理分页
        Long appId = 90010L;
//        if(appId == null){
//            return ResultUtil.fail(1004 , "appId为空");
//        }
        Integer count = withdrawService.queryCountWithdrawByAppId(appId);
        if (count == null) {
            count = 0;
        }
        if (pageSize == null) {
            return ResultUtil.fail(1004, "pageSize为空");
        }
        Integer totalPage = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        if (currentPage == null) {
            return ResultUtil.fail(1004, "currentPage为空!");
        } else if (currentPage == 0) {
            currentPage = 1;
        } else if (currentPage > totalPage) {
            currentPage = totalPage;
        }
        Integer start = (currentPage - 1) * pageSize;
        List<WithdrawPOJO> withdraws = withdrawService.queryWithdrawListByAppId(appId, start, pageSize);
        if (withdraws != null && withdraws.size() > 0) {
            for (WithdrawPOJO withdrawPOJO : withdraws) {
                Map<String, Object> withdraw = new HashMap<>();
                withdraw.put("withdrawTime", withdrawPOJO.getCreated());
                withdraw.put("withdrawMoney", MoneyFormatUtils.getMoneyFromInteger(withdrawPOJO.getWithdrawMoney()));
                withdraw.put("accountType", withdrawPOJO.getAccountType());
                withdraw.put("withdrawStatus", withdrawPOJO.getState());
                withdrawlist.add(withdraw);
            }
        }
        Map<String, Object> data = new HashMap<>();
        data.put("withdraws", withdrawlist);
        data.put("totalPage", totalPage);
        data.put("total", count);
        return ResultUtil.success(data);
    }

    /**
     * 申请提现
     *
     * @param param 提现参数
     * @return 请求结果
     */
    @RequestMapping(value = "/addWithdrawApply", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addWithdrawApply(@RequestBody Map<String, Object> param) {
        //校验入参
        if (validate(param)) {
            Long appId = null;
            if (appId == null) {
                return ResultUtil.fail(1004, "提现appId为空");
            }
            Integer availableMoney = withdrawService.queryAvailableMoney(appId);
            Integer withdrawMoney = Math.round((float) param.get("withdrawMoney") * 100) ;
            if(withdrawMoney > availableMoney){
                return ResultUtil.fail(1004 , "提现金额大于可提现金额");
            }
            WithdrawPOJO withdrawPOJO = new WithdrawPOJO();
            withdrawPOJO.setAppId(appId);
            withdrawPOJO.setAccountType((Integer) param.get("accountType"));
            withdrawPOJO.setAccountNum((String) param.get("accountNum"));
            withdrawPOJO.setWithdrawMoney(withdrawMoney);
            if(withdrawService.addWithdraw(withdrawPOJO)){
                return ResultUtil.success();
            }else{
                return ResultUtil.fail(1004 , "保存提现申请失败");
            }
        } else {
            return ResultUtil.fail(1004, "提现入参有误:" + JSON.toJSONString(param));
        }
    }



    /**
     * 校验提现入参
     *
     * @param param 提现入参
     * @return 是否提现
     */
    private boolean validate(Map<String, Object> param) {
        if (param != null) {
            try {
                Float withdrawMoney = (Float) param.get("withdrawMoney");//TODO ceshi
                Integer accountType = (Integer) param.get("accountType");
                String accountNum = (String) param.get("accountNum");
                if (withdrawMoney == null || withdrawMoney <= 0) {
                    logger.error("提现金额必须大于0");
                    return false;
                }
                if (accountType == null) {
                    logger.error("账户类型为空");
                    return false;
                }
                if (accountNum == null) {
                    logger.error("账户账号为空");
                    return false;
                }
            } catch (ClassCastException e) {
                logger.error("提现入参类型有误" + e);
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

}
