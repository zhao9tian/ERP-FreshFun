package com.quxin.freshfun.controller.erpuser;

import com.quxin.freshfun.model.erpuser.ErpUserJurisdictionPOJO;
import com.quxin.freshfun.model.erpuser.ErpUserPOJO;
import com.quxin.freshfun.service.erpuser.ErpUserJurisdictionService;
import com.quxin.freshfun.service.erpuser.ErpUserService;
import com.quxin.freshfun.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static javax.management.Query.value;

/**
 * Created by Ziming on 2016/10/31.
 * 后台用户controller
 */
@RequestMapping("/erpUser")
@Controller
public class ErpUserController {
    @Autowired
    private ErpUserService erpUserService;
    @Autowired
    private ErpUserJurisdictionService erpUserJurisdictionService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 后台用户注册
     * @return
     */
    @RequestMapping(value = "/erpUserRegiste", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> erpUserRegiste(HttpServletRequest request, @RequestBody ErpUserPOJO user) {
        if (user == null) {
            logger.warn("后台用户注册时，user对象为空");
            return ResultUtil.fail(1010, "用户参数为空！");
        }
        if (user.getAccount() == null || "".equals(user.getAccount())) {
            logger.warn("用户参数帐号为空");
            return ResultUtil.fail(1010, "用户帐号不能为空！");

        }else if( erpUserService.erpUserLogin(user.getAccount())!=null){
            logger.warn("该帐号已被注册！帐号："+user.getAccount());
            return ResultUtil.fail(1005, "该帐号已被注册！");
        }
        if (user.getPassword() == null || "".equals(user.getPassword())) {
            logger.warn("用户参数密码为空");
            return ResultUtil.fail(1010, "用户密码不能为空");
        }
        if(user.getAppId()==null || user.getAppId()==0){
            logger.warn("用户参数appId为空");
            return ResultUtil.fail(1010, "本次注册入口有误");
        }
        user.setCreated(System.currentTimeMillis()/1000);
        user.setUpdated(System.currentTimeMillis()/1000);

        Integer result = erpUserService.addErpUser(user);
        if (result > 0) {
            Map<String, Object> resultMap = new HashMap<String, Object>();
            resultMap.put("nickName",user.getNickName());
            resultMap.put("isAdmin",0);
            return ResultUtil.success(resultMap);//根据前端需要的信息进行组装
        } else {
            return ResultUtil.fail(1024, "用户注册失败");
        }
    }

    /**
     * 后台用户登录
     * @return
     */
    @ResponseBody
    @RequestMapping("/erpUserLogin")
    public Map<String, Object> erpUserLogin(String userAccount, String password) {
        if (userAccount == null || "".equals(userAccount)) {
            logger.warn("后台用户登录时，帐号为空");
            return ResultUtil.fail(1010, "登录帐号为空");
        } else if (password == null || "".equals(password)) {
            logger.warn("后台用户登录时，密码为空");
            return ResultUtil.fail(1010, "登录密码为空");
        } else {
            ErpUserPOJO erpUser = erpUserService.erpUserLogin(userAccount);
            if (erpUser == null) {
                logger.warn("后台用户登录时，根据帐号" + userAccount + "未查询出用户信息");
                return ResultUtil.fail(1005, "用户不存在");
            } else {
                if (password.equals(erpUser.getPassword())) {
                    //查询权限
                    Map<String, Object> resultMap = new HashMap<String, Object>();
                    resultMap.put("nickName",erpUser.getNickName());
                    resultMap.put("isAdmin",erpUser.getIsAdmin());
                    return ResultUtil.success(resultMap);//根据前端需要的信息进行组装
                } else {
                    logger.warn("后台用户帐号" + userAccount + "登录时，密码有误");
                    return ResultUtil.fail(1006, "密码有误");
                }
            }
        }
    }

    /**
     * 用户授权
     * @param userId 用户id
     * @param roles 角色id，多个逗号隔开
     * @return 结果
     */
    @RequestMapping("/authority")
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
    }

}
