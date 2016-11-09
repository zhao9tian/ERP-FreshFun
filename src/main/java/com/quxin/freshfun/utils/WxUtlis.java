package com.quxin.freshfun.utils;


import com.google.gson.Gson;

import java.util.Random;

/**
 * Created by gsix on 2016/11/2.
 */
public class WxUtlis {

    public static String getNonceStr() {
        Random random = new Random();
        return MD5Util.MD5Encode(String.valueOf(random.nextInt(10000)), "GBK");
    }

    /**
     * String转对象
     * @param str
     * @return
     */
    public static <T> T strToJson(String str,T t){
        Gson gson = new Gson();
        T wxInfo = (T) gson.fromJson(str,t.getClass());
        return wxInfo;
    }

    /**
     * 获取当前时间戳
     * @return
     */
    public static Long getCurrentDate(){
        return System.currentTimeMillis()/1000;
    }
}
