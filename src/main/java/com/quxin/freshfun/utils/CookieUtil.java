package com.quxin.freshfun.utils;

import com.quxin.freshfun.model.erpuser.ErpUserPOJO;
import com.quxin.freshfun.service.erpuser.ErpUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ziming on 2016/10/10.
 * 创建CookieUtil类，封装cookie的常用方法
 */
public class CookieUtil {
    //cookie 有效时间
    private static Integer cookieMaxDay = 14;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 获取cookie的有效时间（秒数）
     * @return  cookie的有效时间
     */
    public static Integer getCookieMaxAge(){
        return 60*60*24*cookieMaxDay;
    }

    /**
     * 获取加密后的cookie value
     * @param userId  用户id
     * @return  base64
     */
    public static String getCookieValueByUserId(Long userId,String password){
        Long nowDate = null;
        try {
            nowDate = DateUtils.stringToLong(DateUtils.getDate(new Date(), "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss")/1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //System.out.println("userId:"+userId.toString()+ "*** nowDate:"+nowDate.toString()+ "*** cookieMaxAge:"+ CookieUtil.getCookieMaxAge().toString());
        String cookieValue = userId.toString()+"-"+password+"-"+nowDate.toString();
        String base64OfValue = getBase64(cookieValue);
        return base64OfValue;
    }

    /**
     * 获取cookie中的userId
     * @param request  request请求
     * @return  用户id
     */
    public static Long getUserIdFromCookie(HttpServletRequest request){
        if(getCookieByName(request,"crmUserId")==null||"".equals(getCookieByName(request,"crmUserId")))
            return null;
        String valueString = getFromBase64(getCookieByName(request,"crmUserId").getValue());
        String[] strArray = valueString.split("-");
        Long userId = Long.parseLong(strArray[0]);//用户id
        return  userId;
    }

    public static String getPWDFromCookie(HttpServletRequest request){
        if(getCookieByName(request,"crmUserId")==null||"".equals(getCookieByName(request,"crmUserId")))
            return null;
        String valueString = getFromBase64(getCookieByName(request,"crmUserId").getValue());
        String[] strArray = valueString.split("-");
        String pwd = strArray[1];//用户密码
        return  pwd;
    }

    public static boolean checkAuth(HttpServletRequest request){
        if(getCookieByName(request,"crmUserId")==null||"".equals(getCookieByName(request,"crmUserId")))
            return false;
        String valueString = getFromBase64(getCookieByName(request,"crmUserId").getValue());
        String[] strArray = valueString.split("-");
        Long userId = Long.parseLong(strArray[0]);//用户id
        Long createDate = Long.parseLong(strArray[2]);//创建时间
        Long cookieAge = Long.parseLong(getCookieMaxAge().toString());//cookie有效期
        Long curTime = System.currentTimeMillis()/1000;
        if(userId==null||"".equals(userId)){  //错误cookie
            return  false;
        }else if(curTime>createDate+cookieAge){    //cookie 超时
            return  false;
        }
        return  true;
    }

    /**
     * 根据名字获取cookie
     * @param request 请求
     * @param name cookie名字
     * @return  目标cookie
     */
    public static Cookie getCookieByName(HttpServletRequest request,String name){
        Map<String,Cookie> cookieMap = ReadCookieMap(request);
        if(cookieMap.containsKey(name)){
            Cookie cookie = (Cookie)cookieMap.get(name);
            return cookie;
        }else{
            return null;
        }
    }
    /**
     * 将cookie封装到Map里面
     * @param request  请求
     * @return  目标map
     */
    private static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){
        Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
        Cookie[] cookies = request.getCookies();
        if(null!=cookies){
            for(Cookie cookie : cookies){
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    /**
     * base64加密
     * @param strValue 要加密的字符串
     * @return  加密后的字符串
     */
    private static String getBase64(String strValue) {
        byte[] b = null;
        String s = null;
        try {
            b = strValue.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
            s = new BASE64Encoder().encode(b);
        }
        return s;
    }

    /**
     * base64解密
     * @param strValue  要解密的字符串
     * @return   解密后的字符串
     */
    private static String getFromBase64(String strValue) {
        byte[] b = null;
        String result = null;
        if (strValue != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                b = decoder.decodeBuffer(strValue);
                result = new String(b, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
