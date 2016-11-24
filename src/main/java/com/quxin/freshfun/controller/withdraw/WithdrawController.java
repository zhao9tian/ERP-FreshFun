package com.quxin.freshfun.controller.withdraw;

import com.alibaba.fastjson.JSON;
import com.quxin.freshfun.model.flow.FlowPOJO;
import com.quxin.freshfun.model.withdraw.WithdrawPOJO;
import com.quxin.freshfun.service.erpuser.ErpUserService;
import com.quxin.freshfun.service.flow.FlowService;
import com.quxin.freshfun.service.goods.GoodsService;
import com.quxin.freshfun.service.order.OrderService;
import com.quxin.freshfun.service.withdraw.WithdrawService;
import com.quxin.freshfun.utils.CookieUtil;
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
        Long appId = getAppIdByRequest(request);
        if (appId == null) {
            return ResultUtil.fail(1004, "获取appId有误");
        }
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
        } else {
            if (currentPage > totalPage) {
                currentPage = totalPage;
            }
            if (currentPage == 0) {
                currentPage = 1;
            }
        }
        Integer start = (currentPage - 1) * pageSize;
        List<FlowPOJO> flows = flowService.queryFlowListByAppId(appId, start, pageSize);
        if (flows != null && flows.size() > 0) {
            for (FlowPOJO flow : flows) {
                Map<String, Object> map = new HashMap<>();
                map.put("tradingTime", flow.getCreated());
                String flowMoney = null;
                String content = null;
                if (flow.getFlowType() == 0) {//入账
                    try {
                        Long goodsId = Long.valueOf(orderService.queryOrderDetailByOrderId(flow.getOrderId()).getGoodsId());
                        content = goodsService.queryGoodsByGoodsId(goodsId).getTitle();
                    } catch (NullPointerException e) {
                        logger.error("未查询到相关的商品信息" + e);
                    }
                    flowMoney = "+" + MoneyFormatUtils.getMoneyFromInteger(flow.getFlowMoney());
                } else if (flow.getFlowType() == 1) {//提现
                    content = "提现";
                    flowMoney = "-" + MoneyFormatUtils.getMoneyFromInteger(flow.getFlowMoney());
                }
                map.put("content", content);//通过orderId查询商品title
                map.put("money", flowMoney);
                flowlist.add(map);
            }
        }
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
        Long appId = getAppIdByRequest(request);
        if (appId != null) {
            Integer availableMoney = withdrawService.queryAvailableMoney(appId);
            if (availableMoney == null) {
                availableMoney = 0;
            }
            Map<String, Object> data = new HashMap<>();
            data.put("availableMoney", MoneyFormatUtils.getMoneyFromInteger(availableMoney));
            return ResultUtil.success(data);
        } else {
            return ResultUtil.fail(1004, "获取appId有误");
        }
    }


    /**
     * 查询提现记录
     *
     * @param currentPage 当前页
     * @param pageSize    页面大小
     * @param request     请求
     * @return 请求结果
     */
    @RequestMapping(value = "/queryWithdrawList", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryWithdrawList(Integer currentPage, Integer pageSize, HttpServletRequest request) {
        List<Map> withdrawlist = new ArrayList<>();
        Long appId = getAppIdByRequest(request);
        if (appId == null) {
            return ResultUtil.fail(1004, "获取appId有误");
        }
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
        } else {
            if (currentPage > totalPage) {
                currentPage = totalPage;
            }
            if (currentPage == 0) {
                currentPage = 1;
            }
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
    public Map<String, Object> addWithdrawApply(@RequestBody Map<String, Object> param, HttpServletRequest request) {
        //校验入参
        if (validate(param)) {
            Long appId = getAppIdByRequest(request);
            if (appId == null) {
                return ResultUtil.fail(1004, "获取appId有误");
            }
            Integer availableMoney = withdrawService.queryAvailableMoney(appId);
            Integer withdrawMoney = Math.round(Float.parseFloat((String) param.get("withdrawMoney")) * 100);
            if (withdrawMoney > availableMoney) {
                return ResultUtil.fail(1004, "提现金额大于可提现金额");
            }
            WithdrawPOJO withdrawPOJO = new WithdrawPOJO();
            withdrawPOJO.setAppId(appId);
            withdrawPOJO.setUserId(CookieUtil.getUserIdFromCookie(request));
            withdrawPOJO.setAccountType((Integer) param.get("accountType"));
            withdrawPOJO.setAccountNum((String) param.get("accountNum"));
            withdrawPOJO.setWithdrawMoney(withdrawMoney);
            if (withdrawService.addWithdraw(withdrawPOJO)) {
                return ResultUtil.success();
            } else {
                return ResultUtil.fail(1004, "保存提现申请失败");
            }
        } else {
            return ResultUtil.fail(1004, "提现入参有误:" + JSON.toJSONString(param));
        }
    }

    /**
     * 查询我的钱
     *
     * @param request 请求
     * @return 请求结果
     */
    @RequestMapping(value = "/queryMyMoney", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryMyMoney(HttpServletRequest request) {
        Long appId = getAppIdByRequest(request);
        if (appId != null) {
            Integer totalMoney = withdrawService.queryTotalMoney(appId);//查询累计入账
            Integer unrecord = withdrawService.queryUnrecordMoney(appId);//查询未入账
            if (totalMoney == null) {
                totalMoney = 0;
            }
            totalMoney = Math.round(((float) totalMoney) / 10);//TODO 10%
            if (unrecord == null) {
                unrecord = 0;
            }
            unrecord = Math.round(((float) unrecord) / 10);//TODO 10%
            Map<String, Object> data = new HashMap<>();
            data.put("totalMoney", MoneyFormatUtils.getMoneyFromInteger(totalMoney));
            data.put("unrecord", MoneyFormatUtils.getMoneyFromInteger(unrecord));
            return ResultUtil.success(data);
        } else {
            return ResultUtil.fail(1004, "获取appId有误");
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
                String withdrawMoney = (String) param.get("withdrawMoney");
                Integer accountType = (Integer) param.get("accountType");
                String accountNum = (String) param.get("accountNum");
                if (withdrawMoney == null) {
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

    /**
     * 查询appId
     *
     * @param request 请求
     * @return appId
     */
    private Long getAppIdByRequest(HttpServletRequest request) {
        Long userId = CookieUtil.getUserIdFromCookie(request);
        Long appId = null;
        if (userId == null) {
            logger.error("无法获取用户Id");
        }
        if (erpUserService.queryUserById(userId) == null) {
            logger.error("没有userId为" + userId + "的用户");
        } else {
            appId = erpUserService.queryUserById(userId).getAppId();
            if (appId == null) {
                logger.error("用户" + userId + "没有appId");
            }
        }
        return appId;
    }


}
