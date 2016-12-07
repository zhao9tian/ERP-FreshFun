package com.quxin.freshfun.utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.quxin.freshfun.model.order.WxAccessTokenInfo;

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
     * 获取微信accessToken
     * @param appid
     * @param appSecert
     */
    public static WxAccessTokenInfo getAccessToken(String appid, String appSecert){
        StringBuilder sb = new StringBuilder();
        sb.append(WzConstantUtil.ACCESS_TOKEN_URL);
        sb.append("&appid=");
        sb.append(appid);
        sb.append("&secret=");
        sb.append(appSecert);
        WxAccessTokenInfo tokenInfo = new WxAccessTokenInfo();
        tokenInfo = sendWxRequest(sb, tokenInfo);
        return tokenInfo;
    }

    /**
     * 发送请求
     * @param sb
     * @param t
     * @param <T>
     * @return
     */
    private static <T> T sendWxRequest(StringBuilder sb,T t) {
        Object o = new Object();
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String StrJson = gson.toJson(o);
        String str = HttpClientUtils.jsonToPost(sb.toString(), StrJson);
        return strToJson(str,t);
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
