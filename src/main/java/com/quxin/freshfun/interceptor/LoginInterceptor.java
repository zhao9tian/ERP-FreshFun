package com.quxin.freshfun.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.quxin.freshfun.utils.CookieUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStreamWriter;

/**
 * Created by tianmingzhao on 16/10/13.
 */
public class LoginInterceptor implements HandlerInterceptor {

    public static Integer code = 1001;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println("拦截器执行****************************");
        code=1002;
        /*httpServletResponse.setContentType("test/html;charset=Shift-Jis");
        OutputStreamWriter writer =
                new OutputStreamWriter(httpServletResponse.getOutputStream());
        try {
            JSONObject obj = new JSONObject();
            obj.put("errMsg", "拦截器执行********未登录");
            writer.write(obj.toString());
        } finally {
            writer.flush();
            writer.close();
        }*/
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
