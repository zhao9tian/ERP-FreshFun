package com.quxin.freshfun.utils;


import com.aliyun.oss.OSSClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * @author TuZl
 */
public class OSSUtils {
    private static OSSClient client = null;

    static {
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        String accessKeyId = "wdhNdpIWAjlQaNkG";
        String accessKeySecret = "4fhRflFow19uPPQjPs4Spjt98GdRWj";
        client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    /**
     * 获得url链接
     * @param remotePath 服务器文件路径
     * @return URL链接
     */
    private static String getUrl(String remotePath) {
      // 设置URL过期时间为10年  3600L* 1000*24*365*10
      Date expiration = new Date(new Date().getTime() + 3600L * 1000 * 24 * 365 * 10);
      // 生成URL
      URL url = client.generatePresignedUrl("freshfunpic", remotePath, expiration);
      if (url != null) {
          StringBuffer urlSb;
          urlSb = new StringBuffer(url.toString());
          return urlSb.substring(urlSb.indexOf("/image"),urlSb.indexOf("?"));
      }
      return null;
    }

    /**
     * 上传图片
     * 会自动生成文件夹
     * @param localFilePath 本地文件字节流
     * @param remoteFilePath 服务器文件路径
     * @return 返回图片访问路径
     */
    public static String uploadPic(InputStream localFilePath, String remoteFilePath){
        client.putObject("freshfunpic", remoteFilePath , localFilePath);
        return getUrl(remoteFilePath);
    }

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("d://haha.png");
        InputStream is = new FileInputStream(file);
        OSSUtils.uploadPic(is,"image/wx_head_img/tu.png");
    }

}