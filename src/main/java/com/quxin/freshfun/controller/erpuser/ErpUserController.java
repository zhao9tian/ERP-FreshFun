package com.quxin.freshfun.controller.erpuser;

import com.quxin.freshfun.model.erpuser.ErpAppInfoPOJO;
import com.quxin.freshfun.model.erpuser.ErpUserPOJO;
import com.quxin.freshfun.service.erpuser.ErpAppInfoService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 后台用户注册
     * 首先校验参数是否为空，校验是否传入商城id，没有则用appName添加商城信息返回appId保存到user中，有则直接校验其他参数。
     */
    @RequestMapping(value = "/crmUserRegister", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> crmUserRegister(@RequestBody Map<String, Object> userInfo) {
        ErpUserPOJO user = new ErpUserPOJO();    //实例化用户
        Integer checkResult = checkRegisterParams(userInfo,user);
        if(checkResult!=0)
            return  ResultUtil.fail(checkResult,"注册失败");

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

    private Integer checkRegisterParams(Map<String, Object> userInfo,ErpUserPOJO user){
        Integer result = 0;
        if (userInfo == null) {   //校验参数是否为空
            logger.warn("后台用户注册时，user对象为空");
            return result = 1023;
        }
        //參數是否带有appId，有直接赋值给user，没有则校验appName
        if(userInfo.get("appIdStr")==null||"".equals(userInfo.get("appIdStr").toString())){
            //没有appId时是否有appName，有则新增商城
            if(userInfo.get("appName")==null||"".equals(userInfo.get("appName").toString())){
                logger.warn("创建平台商城，名称为空！");
                return result = 1024;
            }else{
                if(erpAppInfoService.queryAppByName(userInfo.get("appName").toString())!=null){
                    logger.warn("该商城已被注册"+userInfo.get("appName").toString());
                    return result = 1025;
                }
                user.setAppName(userInfo.get("appName").toString());
            }
        }else{
            user.setAppId(FreshFunEncoder.urlToId(userInfo.get("appIdStr").toString()));
        }
        if (userInfo.get("userName") == null || "".equals(userInfo.get("userName").toString())) {
            logger.warn("用户参数帐号为空");
            return result = 1026;
        }else if (!ErpUserController.isRegularRptCode(userInfo.get("userName").toString())) {
            logger.warn("后台用户登录时，用户名格式有误");
            return result = 1027;
        }else if( erpUserService.erpUserLogin(userInfo.get("userName").toString())!=null){
            logger.warn("该帐号已被注册！帐号："+userInfo.get("userName").toString());
            return result = 1028;
        }
        if (userInfo.get("passWord") == null || "".equals(userInfo.get("passWord").toString())) {
            logger.warn("用户参数密码为空");
            return result = 1029;
        }
        return result;
    }

    /**
     * 后台用户登录
     */
    @ResponseBody
    @RequestMapping("/crmUserLogin")
    public Map<String, Object> crmUserLogin(HttpServletResponse response, String userName, String passWord, Integer remember) {
        //先校验入参非空和格式
        String checkResult = checkLoginParams(userName, passWord);
        if (checkResult != null) {
            return ResultUtil.fail(1010, checkResult);
        }
        //根据userName查询用户，校验密码是否正确
        ErpUserPOJO erpUser = erpUserService.erpUserLogin(userName);
        if (erpUser == null) {
            logger.warn("后台用户登录时，根据帐号" + userName + "未查询出用户信息");
            return ResultUtil.fail(1005, "用户不存在");
        } else {
            if (passWord.equals(erpUser.getPassword())) {
                //密码正确，保存cookie
                if (remember == null) //如果没有保存密码标记，默认为浏览器关闭
                    remember = 0;
                //创建cookie对象
                Cookie cookie = new Cookie("crmUserId", CookieUtil.getCookieValueByUserId(erpUser.getUserId(),erpUser.getPassword()));
                if (remember == 1)      //remember=1，表示记住密码，两周自动登录
                    cookie.setMaxAge(CookieUtil.getCookieMaxAge());
                else    //不记住密码，cookie的有效期直到浏览器关闭
                    cookie.setMaxAge(-1);
                cookie.setDomain(".freshfun365.com");  //cookie的作用域
                cookie.setPath("/");
                response.addCookie(cookie);     //种下cookie
                    /*HttpSession session = request.getSession();
                    session.setMaxInactiveInterval(10*60);//以秒为单位
                    session.setAttribute("userId",CookieUtil.getCookieValueByUserId(erpUser.getUserId()));*/
                //获取商城信息，用于参数返回
                ErpAppInfoPOJO erpAppInfoPOJO = erpAppInfoService.queryAppById(erpUser.getAppId());
                //组装返回参数
                Map<String, Object> resultMap = new HashMap<String, Object>();
                resultMap.put("appName", erpAppInfoPOJO.getAppName());
                resultMap.put("appId", FreshFunEncoder.idToUrl(erpUser.getAppId()));
                resultMap.put("userName", erpUser.getUserName());
                resultMap.put("isAdmin", erpUser.getIsAdmin());
                return ResultUtil.success(resultMap);//根据前端需要的信息进行组装
            } else {//用户名密码不一致
                logger.warn("后台用户帐号" + userName + "登录时，密码有误");
                return ResultUtil.fail(1006, "密码有误");
            }
        }
    }

    /**
     * 校验登录的入参
     * @param userName 用户名
     * @param passWord 密码
     * @return 为空则校验通过，否则失败，返回错误信息
     */
    private String checkLoginParams(String userName,String passWord){
        String result = null;
        if (userName == null || "".equals(userName)) {
            logger.warn("后台用户登录时，帐号为空");
            result = "用户登录用户名不能为空" ;
        } else if (!isRegularRptCode(userName)) {
            logger.warn("登录用户名格式有误");
            result = "登录用户名格式有误" ;
        } else if (passWord == null || "".equals(passWord)) {
            logger.warn("用户登录密码不能为空");
            result = "用户登录密码不能为空" ;
        }
        return result;
    }

    /**
     * 后台用户注销
     */
    @ResponseBody
    @RequestMapping("/crmUserLogout")
    public Map<String,Object> crmUserLogout(HttpServletRequest request,HttpServletResponse response){
        Map<String, Object> map = new HashMap<String, Object>();
        Cookie cookie = new Cookie("crmUserId", null);
        cookie.setMaxAge(0);
        cookie.setDomain(".freshfun365.com");
        cookie.setPath("/");
        response.addCookie(cookie);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("code",1001);
        resultMap.put("msg","请求成功");
        map.put("status",resultMap);
        //request.getSession().setMaxInactiveInterval(0);
        return map;//根据前端需要的信息进行组装
    }

    /**
     * 获取菜单
     */
    @ResponseBody
    @RequestMapping("/getMenu")
    public Map<String,Object> getMenu(HttpServletRequest request,HttpServletResponse response){
        setMenus();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<String> list = null;
        ErpUserPOJO user = erpUserService.queryUserById(CookieUtil.getUserIdFromCookie(request));
        if(user==null){
            logger.warn("getMenu-->获取用户信息失败，请重新登录");
            return ResultUtil.fail(1022,"获取用户信息失败，请重新登录");
        }
        if(user.getIsAdmin()==1)
            list=adminMenus;
        else
            list=userMenus;
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

    /**
     * 验证报表代码是否符合编码规则
     * @param rptCode 报表代码
     * @return 验证结果，验证通过返回true，失败返回false
     */
    private static boolean isRegularRptCode(String rptCode) {
        String regEx = "[A-Za-z0-9]{6,16}";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(rptCode);
        boolean rs = m.matches();
        return rs;
    }


    private static List<String> adminMenus = null;
    private static List<String> userMenus =  null;

    private void setMenus(){
        adminMenus = new ArrayList<String>();
        userMenus =  new ArrayList<String>();
        userMenus.add("/");
        userMenus.add("/service");
        userMenus.add("/login");
        userMenus.add("/register");
        userMenus.add("/withdraw");
        userMenus.add("/accountPandect");
        userMenus.add("/transactionFlow");
        adminMenus.add("/");
        adminMenus.add("/service");
        adminMenus.add("/login");
        adminMenus.add("/register");
        adminMenus.add("/goods/add");
        adminMenus.add("/order");
        adminMenus.add("/bannerEdit");
        adminMenus.add("/onloadImg");
        adminMenus.add("/goodsOrder");
        adminMenus.add("/goods/list");
        adminMenus.add("/themeOrder");
        adminMenus.add("/themeLists");
        adminMenus.add("/selectionOrder");
        adminMenus.add("/officialAccounts");
        adminMenus.add("/withdrawCtrl");
        adminMenus.add("/limitedNum");
        adminMenus.add("/brandFoods");
        adminMenus.add("/brandFoodsOrder");
    }
}
