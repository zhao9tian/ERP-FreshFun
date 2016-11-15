package com.quxin.freshfun.controller.erpuser;

import com.quxin.freshfun.model.erpuser.ErpAppInfoPOJO;
import com.quxin.freshfun.service.erpuser.ErpAppInfoService;
import com.quxin.freshfun.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ziming on 2016/11/7.
 */
@Controller
@RequestMapping("/erpAppInfo")
public class ErpAppInfoController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ErpAppInfoService erpAppInfoService;

    @RequestMapping("/getAppInfos")
    public Map<String, Object> getAppInfos() {
        Map<String,Object> map = new HashMap<String,Object>();
        List<ErpAppInfoPOJO> appInfoList = erpAppInfoService.queryErpAppInfo();
        if(appInfoList==null){
            return ResultUtil.fail(1004,"公司列表获取失败");
        }else if(appInfoList.size()<1){
            return ResultUtil.fail(1005,"公司列表获取为空");
        }else{
            return ResultUtil.success(map);
        }
    }

    @ResponseBody
    @RequestMapping("/addAppInfo")
    public Map<String, Object> addAppInfo(String appName) {
        if(appName==null||"".equals(appName)){
            logger.warn("参数有误！appName为空");
            return ResultUtil.fail(1010,"参数有误！appName为空");
        }
        Long appId = erpAppInfoService.addErpAppInfo(appName);
        if(appId==null){
            logger.warn("平台信息添加失败！appName="+appName);
            return ResultUtil.fail(1004,"平台信息添加失败！");
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("appId",appId);
        return ResultUtil.success(map);
    }

    public Map<String, Object> modifyAppInfo() {
        Map<String,Object> map = new HashMap<String,Object>();
        return null;
    }

    public Map<String, Object> removeAppInfo() {
        Map<String,Object> map = new HashMap<String,Object>();
        return null;
    }
}
