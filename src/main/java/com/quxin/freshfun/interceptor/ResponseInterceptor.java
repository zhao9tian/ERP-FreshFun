package com.quxin.freshfun.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Ziming on 2016/11/24.
 * 校验请求的控制器
 */
public class ResponseInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println("校验请求的控制器,正在设置请求和响应的参数……");
        if(httpServletRequest.getHeader("origin")!=null&&httpServletRequest.getHeader("origin").contains(".freshfun365.com")) {
            httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("origin"));
            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
