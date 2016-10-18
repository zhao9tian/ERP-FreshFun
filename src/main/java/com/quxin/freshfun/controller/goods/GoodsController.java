package com.quxin.freshfun.controller.goods;

import com.quxin.freshfun.model.goods.GoodsCategoryOut;
import com.quxin.freshfun.service.goods.GoodsService;
import com.quxin.freshfun.utils.ResultUtil;
import com.quxin.freshfun.utils.UploadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 商品controller
 * Created by qucheng on 2016/10/15.
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "addGoods" , method = RequestMethod.POST)
    @ResponseBody
    public Map<String , Object> addGoods(){



        return null;
    }

    @RequestMapping(value = "getCategoryList" , method = RequestMethod.GET)
    @ResponseBody
    public Map<String , Object> getCategoryList(){
        Map<String , Object> result;
        List<GoodsCategoryOut> categoryList = goodsService.getCategoryList();
        if(categoryList != null && categoryList.size()>0){
            result = ResultUtil.success(categoryList);
        }else{
            result = ResultUtil.fail(1004 ,"没有查询到分类信息");
            logger.error("没有查询到分类信息");
        }
        return result;
    }

    /**
     * 上传图片
     * @param request 页面请求
     * @return 返回请求结果
     */
    @RequestMapping(value = "/uploadPic" ,method = RequestMethod.POST)
    @ResponseBody
    public Map<String , Object> uploadPic(HttpServletRequest request){
        String imgPath;
        Map<String, Object> result;
        try {
            imgPath = UploadUtils.uploadPic(request);
            if(imgPath == null){
                result = ResultUtil.fail(1004 , "图片格式不对");
                logger.error("图片格式不对");
            }else{
                result = ResultUtil.success(imgPath);
            }
        } catch (IOException e) {
            logger.error("获取图片InputStream异常",e);
            return ResultUtil.fail(1004 , "获取图片InputStream异常");
        }

        return result;
    }

}
