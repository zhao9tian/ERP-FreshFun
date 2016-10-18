package com.quxin.freshfun.test;

import com.tinify.Source;
import com.tinify.Tinify;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.util.concurrent.*;

/**
 * 图片压缩工具
 * Created by qucheng on 2016/10/8.
 */
class TinyPngUtils {

//    private static final String KEY = "ld1wBf3JeUqcAXZTNMmPyfR79HnooFg1";
    private static final String KEY = "WOpjLXHy7B3SdK87MhvlpKhRN3x3ZHQk";


    /**
     * 压缩文件
     * @param inputStream 输入流
     * @return 返回压缩过的输入流
     * @throws IOException IO异常抛出
     */
    static InputStream compress(InputStream inputStream) throws IOException {
        Tinify.setKey(KEY);
        InputStream is = null;
        if(inputStream != null){
            is = byte2Input(Source.fromBuffer(input2byte(inputStream)).toBuffer());
        }
        return is;
    }

    /**
     * 将inputStream转为字节数组
     * @param inStream 输入流
     * @return 字节数组
     * @throws IOException IO异常
     */
    private static byte[] input2byte(InputStream inStream)
            throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        return swapStream.toByteArray();
    }

    /**
     * 将字节数组转为inputStream
     * @param buf 字节数组
     * @return 流
     */
    private static InputStream byte2Input(byte[] buf) {
        return new ByteArrayInputStream(buf);
    }


    public static void main(String[] args) throws IOException {

        Tinify.setKey(KEY);
        System.out.println("start");
        Long a = System.currentTimeMillis();

        byte[] sourceData = Files.readAllBytes(Paths.get("/Users/tianmingzhao/Downloads/全民电商web_mob.jpg"));
        byte[] resultData = Tinify.fromBuffer(sourceData).toBuffer();

        Long b = System.currentTimeMillis();
        System.out.println("spend" + (b - a) / 1000);

    }
}
