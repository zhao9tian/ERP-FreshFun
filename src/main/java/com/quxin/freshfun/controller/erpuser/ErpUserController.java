package com.quxin.freshfun.controller.erpuser;

import com.quxin.freshfun.model.erpuser.ErpAppInfoPOJO;
import com.quxin.freshfun.model.erpuser.ErpUserJurisdictionPOJO;
import com.quxin.freshfun.model.erpuser.ErpUserPOJO;
import com.quxin.freshfun.service.erpuser.ErpAppInfoService;
import com.quxin.freshfun.service.erpuser.ErpUserJurisdictionService;
import com.quxin.freshfun.service.erpuser.ErpUserService;
import com.quxin.freshfun.utils.CookieUtil;
import com.quxin.freshfun.utils.FreshFunEncoder;
import com.quxin.freshfun.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.management.Query.value;

/**
 * Created by Ziming on 2016/10/31.
 * 后台用户controller
 */
@RequestMapping("/crmUser")
@Controller
public class ErpUserController {
    @Autowired
    private ErpUserService erpUserService;
    @Autowired
    private ErpAppInfoService erpAppInfoService;
    @Autowired
    private ErpUserJurisdictionService erpUserJurisdictionService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 后台用户注册
     * @return
     */
    @RequestMapping(value = "/crmUserRegister", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> crmUserRegister(@RequestBody Map<String, Object> userInfo) {
        if (userInfo == null) {
            logger.warn("后台用户注册时，user对象为空");
            return ResultUtil.fail(1010, "用户参数为空！");
        }
        ErpUserPOJO user = new ErpUserPOJO();
        if(userInfo.get("appIdStr")==null||"".equals(userInfo.get("appIdStr").toString())){
            if(userInfo.get("appName")==null||"".equals(userInfo.get("appName").toString())){
                logger.warn("创建平台商城，名称为空！");
                return ResultUtil.fail(1010, "创建平台商城，名称为空！");
            }else{
                if(erpAppInfoService.queryAppByName(userInfo.get("appName").toString())!=null){
                    logger.warn("该商城已被注册"+userInfo.get("appName").toString());
                    return ResultUtil.fail(1004, "该商城已被注册");
                }
                Long appId = erpAppInfoService.addErpAppInfo(userInfo.get("appName").toString());
                user.setAppId(appId);
            }
        }else{
            user.setAppId(FreshFunEncoder.urlToId(userInfo.get("appIdStr").toString()));
        }
        if (userInfo.get("userName") == null || "".equals(userInfo.get("userName").toString())) {
            logger.warn("用户参数帐号为空");
            return ResultUtil.fail(1010, "用户帐号不能为空！");
        }else if( erpUserService.erpUserLogin(userInfo.get("userName").toString())!=null){
            logger.warn("该帐号已被注册！帐号："+userInfo.get("userName").toString());
            return ResultUtil.fail(1005, "该帐号已被注册！");
        }
        if (userInfo.get("passWord") == null || "".equals(userInfo.get("passWord").toString())) {
            logger.warn("用户参数密码为空");
            return ResultUtil.fail(1010, "用户密码不能为空");
        }
        user.setUserName(userInfo.get("userName").toString());
        user.setPassword(userInfo.get("passWord").toString());
        user.setCreated(System.currentTimeMillis()/1000);
        user.setUpdated(System.currentTimeMillis()/1000);

        Integer result = erpUserService.addErpUser(user);
        ErpAppInfoPOJO erpAppInfoPOJO = erpAppInfoService.queryAppById(user.getAppId());
        if (result > 0) {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("appName",erpAppInfoPOJO.getAppName());
            resultMap.put("userName",user.getUserName());
            resultMap.put("isAdmin",0);
            return ResultUtil.success(resultMap);//根据前端需要的信息进行组装
        } else {
            return ResultUtil.fail(1024, "用户注册失败");
        }
    }

    /**
     * 后台用户登录
     */
    @ResponseBody
    @RequestMapping("/crmUserLogin")
    public Map<String, Object> crmUserLogin(HttpServletRequest request,HttpServletResponse response,String userName, String password) {
        if (userName == null || "".equals(userName)) {
            logger.warn("后台用户登录时，帐号为空");
            return ResultUtil.fail(1010, "登录帐号为空");
        } else if (password == null || "".equals(password)) {
            logger.warn("后台用户登录时，密码为空");
            return ResultUtil.fail(1010, "登录密码为空");
        } else {
            ErpUserPOJO erpUser = erpUserService.erpUserLogin(userName);
            if (erpUser == null) {
                logger.warn("后台用户登录时，根据帐号" + userName + "未查询出用户信息");
                return ResultUtil.fail(1005, "用户不存在");
            } else {
                if (password.equals(erpUser.getPassword())) {
                    //查询权限
                    Cookie cookie = new Cookie("userId",CookieUtil.getCookieValueByUserId(erpUser.getUserId()));
                    cookie.setMaxAge(CookieUtil.getCookieMaxAge());
                    cookie.setDomain(".freshfun365.com");
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    HttpSession session = request.getSession();
                    session.setMaxInactiveInterval(10*60);//以秒为单位
                    session.setAttribute("userId",CookieUtil.getCookieValueByUserId(erpUser.getUserId()));
                    ErpAppInfoPOJO erpAppInfoPOJO = erpAppInfoService.queryAppById(erpUser.getAppId());
                    Map<String, Object> resultMap = new HashMap<String, Object>();
                    resultMap.put("appName",erpAppInfoPOJO.getAppName());
                    resultMap.put("appId",FreshFunEncoder.idToUrl(erpUser.getAppId()));
                    resultMap.put("userName",erpUser.getUserName());
                    resultMap.put("isAdmin",erpUser.getIsAdmin());
                    return ResultUtil.success(resultMap);//根据前端需要的信息进行组装
                } else {
                    logger.warn("后台用户帐号" + userName + "登录时，密码有误");
                    return ResultUtil.fail(1006, "密码有误");
                }
            }
        }
    }

    /**
     * 后台用户注销
     */
    @ResponseBody
    @RequestMapping("/crmUserLogout")
    public Map<String,Object> crmUserLogout(HttpServletRequest request,HttpServletResponse response){
        Cookie cookie = new Cookie("userId", null);
        cookie.setMaxAge(0);
        cookie.setDomain(".freshfun365.com");
        cookie.setPath("/");
        response.addCookie(cookie);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("status",1001);
        resultMap.put("msg","请求成功");
        request.getSession().setMaxInactiveInterval(0);
        return resultMap;//根据前端需要的信息进行组装
    }

    /**
     * 后台用户注销
     */
    @ResponseBody
    @RequestMapping("/getMenu")
    public Map<String,Object> getMenu(HttpServletRequest request,HttpServletResponse response){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<String> list = new ArrayList<String>();
        list.add("/index");
        list.add("/order");
        resultMap.put("menuList",list);
        return ResultUtil.success(resultMap);//根据前端需要的信息进行组装
    }

    /**
     * 用户授权
     * @param userId 用户id
     * @param roles 角色id，多个逗号隔开
     * @return 结果
     */
    /*@RequestMapping("/authority")
    @ResponseBody
    public Map<String,Object> authority(String userId,String roles){
        if (userId == null || "".equals(userId)) {
            logger.warn("授权参数有误,userId为空");
            return null;
        }
        if (roles == null || "".equals(roles)) {
            logger.warn("授权参数有误,roles为空");
            return null;
        }
        ErpUserJurisdictionPOJO erpUserJuris = new ErpUserJurisdictionPOJO();
        erpUserJuris.setRoleId(Long.parseLong(userId));
        String[] rolesArr = roles.split(",");
        for (String role : rolesArr) {
            erpUserJuris.setCreated(System.currentTimeMillis() / 1000);
            erpUserJuris.setUpdated(System.currentTimeMillis() / 1000);
            erpUserJuris.setRoleId(Long.parseLong(role));
            erpUserJurisdictionService.addErpUserJuris(erpUserJuris);
        }
        return ResultUtil.success("授权成功");
    }*/

}
