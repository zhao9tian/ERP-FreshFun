package com.quxin.freshfun.interceptor;

import com.quxin.freshfun.model.erpuser.ErpUserPOJO;
import com.quxin.freshfun.service.erpuser.ErpUserService;
import com.quxin.freshfun.utils.CookieUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ziming on 2016/11/22
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private ErpUserService erpUserService;

    /**
     *  首先判断session中的用户是否超时，在判断cookie中的用户是否有效
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();  //最终返回的map数据
        Map<String,Object> mapStatus = new HashMap<String,Object>();  //status的map数据
        boolean finalResult;
        /*HttpSession session = httpServletRequest.getSession();
        Object userIdString = session.getAttribute("userId");*/
        if(true){
        /*if(userIdString!=null){
            session.setAttribute("userId",userIdString.toString());
            session.setMaxInactiveInterval(10*60);*/
            boolean isLogin = CookieUtil.checkAuth(httpServletRequest);//校验是否存在cookie
            if(!isLogin){//不存在cookie，返回1022重新登录
                finalResult = false;
                mapStatus.put("code", 1022);
                mapStatus.put("msg", "cookie中userId超时");
                map.put("status",mapStatus);
            }else{
                Long userId = CookieUtil.getUserIdFromCookie(httpServletRequest);//获取cookie中的userId
                ErpUserPOJO user = erpUserService.queryUserById(userId);//根据cookie中的userId查询用户
                if(user==null){  //用户user为空，标识cookie中保存的是无效userId，返回1022重新登录
                    mapStatus.put("code", 1022);
                    mapStatus.put("msg", "cookie中userId无效");
                    map.put("status",mapStatus);
                    finalResult = false;
                }else{
                    finalResult = true;
                }
            }
        }else{//session校验不通过时，返回1022重新登录
            mapStatus.put("code", 1022);
            mapStatus.put("msg", "session中userId超时");
            map.put("status",mapStatus);
            finalResult = false;
        }
        if(!finalResult){//拦截器不通过时，map中的信息转为json输出
            JSONObject responseJSONObject = JSONObject.fromObject(map);
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json; charset=utf-8");
            PrintWriter out = null;
            try {
                out = httpServletResponse.getWriter();
                out.append(responseJSONObject.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        }
        return finalResult;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
