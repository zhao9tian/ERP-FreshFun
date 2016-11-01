package com.quxin.freshfun.utils;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * 图片上传类
 * @author qucheng
 */
public class UploadUtils {

    /**
     * 上传图片
     * @param request 请求
     * @return 返回上传路径
     * @throws IOException 获取图片InputStream异常
     */
    public static String uploadPic(HttpServletRequest request) throws IOException {
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
        Boolean bool = false;
        if (originalFilename != null && !"".equals(originalFilename)) {
            if(originalFilename.lastIndexOf(".") != -1){
                String contentType = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
                List<String> fileTypes = new ArrayList<>();
                fileTypes.add("jpg");
                fileTypes.add("jpeg");
                fileTypes.add("png");
                fileTypes.add("gif");
                bool = fileTypes.contains(contentType);
            }
        }
        return bool;
    }

}
