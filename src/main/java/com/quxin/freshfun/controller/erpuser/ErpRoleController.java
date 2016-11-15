package com.quxin.freshfun.controller.erpuser;

import com.quxin.freshfun.model.erpuser.ErpRolePOJO;
import com.quxin.freshfun.service.erpuser.ErpRoleService;
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
 * Created by Ziming on 2016/11/3.
 */
@Controller
@RequestMapping("/erpRole")
public class ErpRoleController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ErpRoleService erpRoleService;

    @RequestMapping("/getErpRole")
    @ResponseBody
    public Map<String, Object> getErpRoles() {
        List<ErpRolePOJO> roles = erpRoleService.queryErpRole();
        if(roles==null||roles.size()==0){
            logger.warn("角色列表为空！");
            return ResultUtil.fail(1001, "请求成功，角色列表为空！");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("erpRoles", roles);
        return ResultUtil.success(map);
    }

    @ResponseBody
    @RequestMapping("/addErpRole")
    public Map<String,Object> addErpRole(String roleName,String roleDes){
        if(roleName==null||"".equals(roleName)){
            logger.warn("新增角色时，角色名称为空！");
            return ResultUtil.fail(1001, "角色名称不能为空！");
        }
        ErpRolePOJO role = new ErpRolePOJO();
        role.setRoleName(roleName);
        role.setRoleDes(roleDes==null?"":roleDes);
        Integer result = erpRoleService.addErpRole(role);
        if(result==1){
            return  ResultUtil.success("角色新增成功！");
        }else{
            logger.warn("角色新增失败，受影响行数为："+result+",角色名称为："+roleName+",角色描述为："+roleDes);
            return ResultUtil.fail(1001,"角色新增失败！");
        }
    }

    /**
     * 修改角色信息
     *
     * @param roleName 角色名称
     * @param roleDes  角色描述
     * @param roleId   角色id
     */
    @ResponseBody
    @RequestMapping("/changeErpRole")
    public Map<String, Object> changeErpRole(String roleName, String roleDes, String roleId) {
        if (roleId == null || "".equals(roleId)) {
            logger.warn("修改角色信息时，入参有误，角色id为空");
            return ResultUtil.fail(1001, "角色修改失败！");
        }
        Long erpRoleId = Long.parseLong(roleId);
        if ((roleName == null || "".equals(roleName)) && (roleDes == null || "".equals(roleDes))) {  //待修改&& or  ||
            logger.warn("修改角色信息时，入参有误，角色名称和角色描述都为空");
            return ResultUtil.fail(1001, "角色修改失败！");
        }
        ErpRolePOJO role = new ErpRolePOJO();
        role.setRoleId(erpRoleId);
        role.setRoleName(roleName);
        role.setRoleDes(roleDes);
        Integer result = erpRoleService.addErpRole(role);
        if (result == 1) {
            return ResultUtil.success("角色修改成功！");
        } else {
            return ResultUtil.fail(1001, "角色修改失败！");
        }
    }
}
