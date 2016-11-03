package com.quxin.freshfun.controller.uploadPic;

import com.quxin.freshfun.utils.ResultUtil;
import com.quxin.freshfun.utils.UploadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * 图片上传类
 * @author qucheng
 */
@Controller
@RequestMapping("/")
public class UploadController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 上传图片
     * @param request 页面请求
     * @return 返回请求结果
     */
    @RequestMapping(value = "/uploadPic", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadPic(HttpServletRequest request) {
        String imgPath;
        Map<String, Object> result;
        try {
            imgPath = UploadUtils.uploadPic(request);
            if (imgPath == null) {
                result = ResultUtil.fail(1004, "上传失败,请检查上传图片大小或图片格式");
                logger.error("图片格式，或者大小不正确");
            } else {
                result = ResultUtil.success(imgPath);
            }
        } catch (IOException e) {
            logger.error("获取图片InputStream异常", e);
            return ResultUtil.fail(1004, "获取图片InputStream异常");
        }
        return result;
    }
}
