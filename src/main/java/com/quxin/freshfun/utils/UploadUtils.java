package com.quxin.freshfun.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * 图片上传类
 * @author qucheng
 */
@Controller
@RequestMapping("/")
public class UploadUtils {

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
            imgPath = upload(request);
            if (imgPath == null) {
                result = ResultUtil.fail(1004, "图片格式不对");
                logger.error("图片格式不对");
            } else {
                result = ResultUtil.success(imgPath);
            }
        } catch (IOException e) {
            logger.error("获取图片InputStream异常", e);
            return ResultUtil.fail(1004, "获取图片InputStream异常");
        }
        return result;
    }


    /**
     * 上传图片实现
     * @param request 请求
     * @return 返回地址
     * @throws IOException IO异常
     */
    private String upload(HttpServletRequest request) throws IOException {
        String imgPath = "";
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        Iterator<String> iter = multiRequest.getFileNames();
        while (iter.hasNext()) {
            MultipartFile file = multiRequest.getFile(iter.next());
            if (!"".equals(file.getOriginalFilename())) {
                if (filterFileType(file.getOriginalFilename())) {
                    String picName = file.getOriginalFilename();
                    String editFileName = UUID.randomUUID() + picName.substring(picName.lastIndexOf("."));
                    String path = createDirs() + "/" + editFileName;
                    imgPath = OSSUtils.uploadPic(file.getInputStream(), path);
                    break;
                } else {
                    return null;
                }
            }
        }
        return imgPath;
    }

    /**
     * 生成文件保存目录
     * @return 图片相对项目的路径
     */
    private static String createDirs() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return "image/" + year + month + day;
    }

    /**
     * 过滤上传的文件的类型
     * @param originalFilename 带后缀的文件名
     * @return 是否是图片格式
     */
    private static Boolean filterFileType(String originalFilename) {
        String contentType ;
        if (originalFilename != null && !"".equals(originalFilename)) {
            if(originalFilename.lastIndexOf(".") != -1){
                contentType = originalFilename.substring(originalFilename.lastIndexOf("."));
                List<String> fileTypes = new ArrayList<>();
                fileTypes.add("jpg");
                fileTypes.add("jpeg");
                fileTypes.add("png");
                fileTypes.add("gif");
                return fileTypes.contains(contentType);
            }
        }
        return false;
    }
}
