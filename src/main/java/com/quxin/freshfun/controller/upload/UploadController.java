package com.quxin.freshfun.controller.upload;

import com.quxin.freshfun.model.order.OrderDetailsPOJO;
import com.quxin.freshfun.service.order.OrderService;
import com.quxin.freshfun.utils.ResultUtil;
import com.quxin.freshfun.utils.Stream2Bean;
import com.quxin.freshfun.utils.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 图片上传类
 * @author qucheng
 */
@Controller
@RequestMapping("/")
public class UploadController {


    @Autowired
    private OrderService orderService ;

    /**
     * 上传图片
     * @param request 页面请求
     * @return 返回请求结果
     */
    @RequestMapping(value = "/uploadPic", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadPic(HttpServletRequest request) {
        Map<String, Object> result;
        try {
            Map<String , Object> imgInfo = UploadUtils.uploadPic(request);
            if (imgInfo == null) {
                result = ResultUtil.fail(1004, "上传失败,请检查上传图片大小或图片格式");
            } else {
                result = ResultUtil.success(imgInfo);
            }
        } catch (IOException e) {
            return ResultUtil.fail(1004, "获取图片InputStream异常");
        }
        return result;
    }

    /**
     * 上传图片
     * @param request 页面请求
     * @return 返回请求结果
     */
    @RequestMapping(value = "/uploadExcel", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadExcel(HttpServletRequest request) {
        Map<String, Object> result;
        Long now1 = System.currentTimeMillis();
        try {
            InputStream excelInputStream = UploadUtils.uploadExcel(request);
            if (excelInputStream == null) {
                result = ResultUtil.fail(1004, "上传失败,请检查文件格式和大小");
            } else {
                List<OrderDetailsPOJO>  orderDetailsPOJOs = Stream2Bean.getOrdersByInputStream(excelInputStream);
                StringBuilder sb = new StringBuilder("订单Id为:");
                if(orderDetailsPOJOs != null){
                    for(OrderDetailsPOJO order : orderDetailsPOJOs){
                        if(orderService.deliverOrder(order) != 1){
                            sb.append(order.getOrderId()).append(",");
                        }
                    }
                }
                if(sb.toString().endsWith(",")){
                    sb.deleteCharAt(sb.length()-1).append("的订单导入物流信息失败!");
                    result = ResultUtil.fail(1004,sb.toString());
                }else{
                    result = ResultUtil.success();
                }
            }
        } catch (IOException e) {
            return ResultUtil.fail(1004, "获取图片InputStream异常");
        }
        System.out.println("修改时间"+(System.currentTimeMillis() - now1));
        return result;
    }


}
