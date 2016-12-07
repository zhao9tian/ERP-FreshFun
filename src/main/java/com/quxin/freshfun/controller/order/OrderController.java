package com.quxin.freshfun.controller.order;

import com.quxin.freshfun.model.erpuser.ErpUserPOJO;
import com.quxin.freshfun.model.order.OrderDetailsPOJO;
import com.quxin.freshfun.model.order.OrderQueryParam;
import com.quxin.freshfun.model.order.OrderSaleInfo;
import com.quxin.freshfun.model.order.RefundOut;
import com.quxin.freshfun.service.erpuser.ErpUserService;
import com.quxin.freshfun.service.order.OrderService;
import com.quxin.freshfun.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台订单管理
 * Created by qingtian on 2016/10/20.
 */
@Controller
@RequestMapping("/")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ErpUserService erpUserService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 查询所有订单
     *
     * @return 返回请求结果
     */
    @RequestMapping("/selectBackstageOrders")
    @ResponseBody
    public Map<String, Object> selectBackstageOrders(OrderQueryParam orderParam) throws BusinessException {
        if (orderParam == null || orderParam.getPage() == null || orderParam.getPage() <= 0 || orderParam.getPageSize() == null) {
            return ResultUtil.fail(1004, "传入参数有误");
        }
        //设置字符编码
        setQueryCoding(orderParam);
        return ResultUtil.success(orderService.selectBackstageOrders(orderParam));
    }

    /**
     * 设置查询条件字符编码
     *
     * @param orderParam
     */
    private void setQueryCoding(OrderQueryParam orderParam) {
        if (!StringUtils.isEmpty(orderParam.getAppName()))
            orderParam.setAppName(EncodingFormat(orderParam.getAppName()));
        if (!StringUtils.isEmpty(orderParam.getGoodsName()))
            orderParam.setGoodsName(EncodingFormat(orderParam.getGoodsName()));
        if (!StringUtils.isEmpty(orderParam.getGoodsTitle()))
            orderParam.setGoodsTitle(EncodingFormat(orderParam.getGoodsTitle()));
        if (!StringUtils.isEmpty(orderParam.getNickName()))
            orderParam.setNickName(EncodingFormat(orderParam.getNickName()));
        if (!StringUtils.isEmpty(orderParam.getDeliveryName()))
            orderParam.setDeliveryName(EncodingFormat(orderParam.getDeliveryName()));
    }

    private String EncodingFormat(String code) {
        try {
            return new String(code.getBytes("iso-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("字符编码异常", e);
        }
        return code;
    }

    @RequestMapping("/findOrdersByPlatform")
    @ResponseBody
    public Map<String, Object> findOrdersByPlatform(HttpServletRequest request, OrderQueryParam orderParam) throws BusinessException {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> resultData = new HashMap<>();
        if (orderParam == null || orderParam.getOrderStatus() == null || orderParam.getPage() == null || orderParam.getPageSize() == null) {
            map.put("code", 1004);
            map.put("msg", "参数不能为null");
            resultMap.put("status", map);
            return resultMap;
        }
        //获取AppId
        Long appId = getAppId(request);
        orderParam.setAppId(appId);

        orderParam.setPage((orderParam.getPage() - 1) * orderParam.getPageSize());
        List<OrderDetailsPOJO> order = null;
        int size = 0;
        int total = 0;
        if (!((order = orderService.findOrdersByPlatform(orderParam)) == null && (order = new ArrayList<>()) != null)) {
            total = orderService.selectBackstageOrdersCount(orderParam);
            if (total % orderParam.getPageSize() == 0) {
                size = total / orderParam.getPageSize();
            } else {
                size = total / orderParam.getPageSize() + 1;
            }
            order = MoneyFormatUtils.setBackstageMoney(order);
        }
        map.put("code", 1001);
        map.put("msg", "请求成功");
        resultData.put("totalPage", size);
        resultData.put("total", total);
        resultData.put("list", order);
        resultMap.put("status", map);
        resultMap.put("data", resultData);
        return resultMap;
    }

    /**
     * 获取AppId
     *
     * @param request 请求
     * @return AppId
     */
    public Long getAppId(HttpServletRequest request) throws BusinessException {
        Long userId = CookieUtil.getUserIdFromCookie(request);
        ErpUserPOJO erpUserPOJO = erpUserService.queryUserById(userId);
        if (erpUserPOJO == null || erpUserPOJO.getAppId() == null)
            throw new BusinessException("订单获取AppId失败");
        return erpUserPOJO.getAppId();
    }

    /**
     * 查询商户对应销售额信息
     *
     * @return
     * @throws BusinessException
     */
    @RequestMapping("/findSaleInfo")
    @ResponseBody
    public Map<String, Object> findSaleInfo(HttpServletRequest request) throws BusinessException {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> resultMap = new HashMap<>();

        Long appId = getAppId(request);
        if (appId == null) {
            logger.error("查询商户销售额AppId为null");
            map.put("code", 1004);
            map.put("msg", "查询失败");
            resultMap.put("status", map);
            return resultMap;
        }
        OrderSaleInfo saleInfo = orderService.findSaleInfo(appId);
        if (saleInfo == null)
            saleInfo = new OrderSaleInfo();
        map.put("code", 1001);
        map.put("msg", "请求成功");
        resultMap.put("status", map);
        resultMap.put("data", saleInfo);
        return resultMap;
    }

    /**
     * 获取订单数目
     *
     * @return
     */
    @RequestMapping("/getOrderNum")
    @ResponseBody
    public Map<String, Object> getOrderNum() {
        Map<String, Object> orderNumList = orderService.getOrderNum(null);
        if (orderNumList == null)
            orderNumList = new HashMap<>();
        return ResultUtil.success(orderNumList);
    }

    @RequestMapping("/getPlatformOrderNum")
    @ResponseBody
    public Map<String, Object> getPlatformOrderNum(HttpServletRequest request) throws BusinessException {
        //获取AppId
        Long appId = getAppId(request);
        Map<String, Object> orderNumList = orderService.getOrderNum(appId);
        if (orderNumList == null)
            orderNumList = new HashMap<>();
        return ResultUtil.success(orderNumList);
    }

    /**
     * 审核退款
     *
     * @return
     */
    @RequestMapping("/updateRefundInfo")
    @ResponseBody
    public Map<String, Object> updateRefundInfo(Long orderId, Integer action) throws BusinessException {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> resultMap = new HashMap<>();
        if (orderId == null || action == null) {
            map.put("code", 1004);
            map.put("msg", "传入待参数不正确");
            resultMap.put("status", map);
            return resultMap;
        }
        switch (action) {
            case 0:
                Integer state = orderService.rebutRefunds(orderId);
                if (state == 0) {
                    map.put("code", 1004);
                    map.put("msg", "申请退款失败");
                    resultMap.put("status", map);
                    return resultMap;
                }
                map.put("code", 1001);
                map.put("msg", "请求成功");
                resultMap.put("status", map);
                resultMap.put("data", state);
                break;
            case 1:
                String refundResult = orderService.orderRefunds(orderId);
                if (!"SUCCESS".equals(refundResult)) {
                    map.put("code", 1004);
                    map.put("msg", refundResult);
                    resultMap.put("status", map);
                    return resultMap;
                }
                map.put("code", 1001);
                map.put("msg", "请求成功");
                resultMap.put("status", map);
                resultMap.put("data", refundResult);
                break;
        }
        return resultMap;
    }

    /**
     * 查看退款详情
     *
     * @return
     */
    @RequestMapping("/getRefundInfo")
    @ResponseBody
    public Map<String, Object> getRefundInfo(Long orderId) throws BusinessException {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> resultMap = new HashMap<>();
        if (orderId == null) {
            map.put("code", 1004);
            map.put("msg", "传入待参数不正确");
            resultMap.put("status", map);
            return resultMap;
        }
        RefundOut refundInfo = orderService.getRefundInfo(orderId);
        if (refundInfo == null) {
            map.put("code", 1004);
            map.put("msg", "查询数据不存在");
            resultMap.put("status", map);
            return resultMap;
        }
        map.put("code", 1001);
        map.put("msg", "请求成功");
        resultMap.put("status", map);
        resultMap.put("data", refundInfo);
        return resultMap;
    }

    /**
     * 查询已完成订单
     *
     * @return
     */
    @RequestMapping("/findFinishOrder")
    @ResponseBody
    public Map<String, Object> findFinishOrder(Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> resultData = new HashMap<>();
        if (page == null || page == 0 || pageSize == null || pageSize <= 0) {
            map.put("code", 1004);
            map.put("msg", "传入当前页码不正确");
            resultMap.put("status", map);
            return resultMap;
        }
        int currentPage = (page - 1) * pageSize;
        List<OrderDetailsPOJO> finishOrderList = orderService.findFinishOrder(currentPage, pageSize);
        Integer finishOrderCount = orderService.findFinishOrderCount();
        finishOrderList = MoneyFormatUtils.setBackstageMoney(finishOrderList);
        int size = 1;
        if (finishOrderCount % pageSize == 0) {
            size = finishOrderCount / pageSize;
        } else {
            size = finishOrderCount / pageSize + 1;
        }
        map.put("code", 1001);
        map.put("msg", "请求成功");
        resultData.put("totalPage", size);
        resultData.put("total", finishOrderCount);
        resultData.put("page", page);
        resultData.put("pageSize", pageSize);
        resultData.put("list", finishOrderList);
        resultMap.put("status", map);
        resultMap.put("data", resultData);
        return resultMap;
    }

    /**
     * 发货
     *
     * @param order 物流信息
     * @return 请求是否成功
     */
    @RequestMapping(value = "/deliverOrder", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> deliverOrder(@RequestBody OrderDetailsPOJO order) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> resultMap = new HashMap<>();
        if (StringUtils.isEmpty(order.getOrderId()) || StringUtils.isEmpty(order.getActualMoney()) || StringUtils.isEmpty(order.getDeliveryNum()) || StringUtils.isEmpty(order.getDeliveryName())) {
            map.put("code", 1004);
            map.put("msg", "参数有误");
            resultMap.put("status", map);
            return resultMap;
        } else {
            int orderStatus = orderService.deliverOrder(order);
            if (orderStatus <= 0) {
                map.put("code", 1004);
                map.put("msg", "发货失败");
                resultMap.put("status", map);
            } else {
                map.put("code", 1001);
                map.put("msg", "请求成功");
                resultMap.put("status", map);
            }
            resultMap.put("data", orderStatus);
        }
        return resultMap;
    }

    /**
     * 订单删除
     *
     * @return
     */
    @RequestMapping("orderDel")
    @ResponseBody
    public Map<String, Object> orderDel(Long orderId) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> resultMap = new HashMap<>();
        if (null == orderId || 0 == orderId) {
            map.put("code", 1004);
            map.put("msg", "参数传递错误");
            resultMap.put("status", map);
            return resultMap;
        }
        Integer delStatus = orderService.orderDel(orderId);
        if (delStatus <= 0) {
            map.put("code", 1004);
            map.put("msg", "订单删除失败");
            resultMap.put("status", map);
            return resultMap;
        } else {
            map.put("code", 1001);
            map.put("msg", "请求成功");
            resultMap.put("status", map);
            resultMap.put("data", delStatus);
            return resultMap;
        }
    }

    /**
     * 订单备注
     *
     * @param orderId 订单Id
     * @param remark  备注内容
     * @return 请求结果
     */
    @RequestMapping("/orderRemark")
    @ResponseBody
    public Map<String, Object> orderRemark(String orderId, String remark) throws UnsupportedEncodingException {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> resultMap = new HashMap<>();

        if (StringUtils.isEmpty(orderId) || StringUtils.isEmpty(remark)) {
            map.put("code", 1004);
            map.put("msg", "传入参数不正确");
            resultMap.put("status", map);
            return resultMap;
        }
        String utfRemark = new String(remark.getBytes("iso-8859-1"), "utf-8");
        Integer status = orderService.orderRemark(orderId, utfRemark);
        if (status == null || status <= 0) {
            map.put("code", 1004);
            map.put("msg", "备注失败");
            resultMap.put("status", map);
            return resultMap;
        } else {
            map.put("code", 1001);
            map.put("msg", "请求成功");
            resultMap.put("status", map);
            resultMap.put("data", status);
        }
        return resultMap;
    }

    /**
     * 订单导出Excel
     *
     * @return
     */
    @RequestMapping("/exportOrder")
    public String exportOrder(HttpServletResponse response, OrderQueryParam orderQueryParam) throws BusinessException {
        if (orderQueryParam == null) {
            return null;
        }
        try {
            setExcelTitle(response);
            //设置字符编码
            setQueryCoding(orderQueryParam);
            List<OrderDetailsPOJO> orderList = orderService.exportOrder(orderQueryParam);
            if (orderList != null) {
                ExportOrderExcelUtils exportOrder = new ExportOrderExcelUtils();
                exportOrder.ExportExcel(orderList, response.getOutputStream(), orderQueryParam.getOrderStatus());
            }
        } catch (IOException e) {
            logger.error("导出Excel订单IO异常", e);
        }
        return null;
    }

    /**
     * 设置Excel信息
     *
     * @param response
     * @throws UnsupportedEncodingException
     */
    private void setExcelTitle(HttpServletResponse response) throws UnsupportedEncodingException {
        String fileName = new String(("订单管理").getBytes("gb2312"), "iso8859-1") + ".xlsx";
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setCharacterEncoding("utf-8");
    }

}
