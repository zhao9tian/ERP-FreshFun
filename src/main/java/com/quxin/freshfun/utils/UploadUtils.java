package com.quxin.freshfun.utils;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

/**
 * 图片上传类
 *
 * @author qucheng
 */
public class UploadUtils {

    /**
     * 上传图片
     *
     * @param request 请求
     * @return 返回上传路径
     * @throws IOException 获取图片InputStream异常
     */
    public static Map<String, Object> uploadPic(HttpServletRequest request) throws IOException {
        Map<String ,Object> result = new HashMap<>();
        String imgPath = "";
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        Iterator<String> iter = multiRequest.getFileNames();
        while (iter.hasNext()) {
            MultipartFile file = multiRequest.getFile(iter.next());
            if (file.getSize() < 307200) {
                if (!"".equals(file.getOriginalFilename())) {
                    if (filterFileType(file.getOriginalFilename())) {
                        String picName = file.getOriginalFilename();
                        BufferedImage buff = ImageIO.read(file.getInputStream());
                        Integer width = buff.getWidth();
                        Integer height = buff.getHeight();
                        String editFileName = System.currentTimeMillis() + "_" + width + "x" + height + picName.substring(picName.lastIndexOf("."));
                        String path = createDirs() + "/" + editFileName;
                        imgPath = "http://pic1.freshfun365.com" + OSSUtils.uploadPic(file.getInputStream(), path);
                        result.put("imgPath", imgPath);
                        result.put("imgWidth" , width);
                        result.put("imgHeight" , height);
                        break;
                    } else {
                        return null;
                    }
                }
            } else {
                return null;
            }
        }
        return result;
    }

    /**
     * 生成文件保存目录
     *
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
     *
     * @param originalFilename 带后缀的文件名
     * @return 是否是图片格式
     */
    private static Boolean filterFileType(String originalFilename) {
        Boolean bool = false;
        if (originalFilename != null && !"".equals(originalFilename)) {
            if (originalFilename.lastIndexOf(".") != -1) {
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
