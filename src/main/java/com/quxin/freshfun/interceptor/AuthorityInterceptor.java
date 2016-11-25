package com.quxin.freshfun.interceptor;

import com.quxin.freshfun.model.erpuser.ErpUserPOJO;
import com.quxin.freshfun.service.erpuser.ErpUserService;
import com.quxin.freshfun.utils.CookieUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ziming on 2016/11/21.
 */
public class AuthorityInterceptor implements HandlerInterceptor {
    @Autowired
    private ErpUserService erpUserService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();//返回的map数据
        Map<String,Object> mapStatus = new HashMap<String,Object>();//status的map数据
        boolean result = false;   //拦截器处理结果
        ErpUserPOJO user = erpUserService.queryUserById(CookieUtil.getUserIdFromCookie(httpServletRequest)); //根据cookie中的userId查询当前登录用户
        Byte isAdmin = 0;
        if(user!=null){
            isAdmin = user.getIsAdmin();
        }else{
            mapStatus.put("code", 1022);
            mapStatus.put("msg", "当前cookie无效");
            map.put("status", mapStatus);
            result= false;
        }
        if(isAdmin==0) {
            mapStatus.put("code", 1044);
            mapStatus.put("msg", "该用户没有操作权限");
            map.put("status", mapStatus);
            result = false;
        }
        else {
            result = true;
        }
        if(!result){
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
        return result;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
