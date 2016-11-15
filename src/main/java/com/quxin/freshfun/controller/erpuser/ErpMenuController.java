package com.quxin.freshfun.controller.erpuser;

import com.quxin.freshfun.model.erpuser.ErpMenuPOJO;
import com.quxin.freshfun.service.erpuser.ErpMenuService;
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
 * Created by Ziming on 2016/11/2.
 */
@Controller
@RequestMapping("/erpMenu")
public class ErpMenuController {
    @Autowired
    private ErpMenuService erpMenuService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 获取菜单列表
     */
    @RequestMapping("/getErpMenu")
    @ResponseBody
    public Map<String, Object> getErpMenus() {
        List<ErpMenuPOJO> menus = erpMenuService.queryErpMenu();
        if (menus == null || menus.size() == 0) {
            logger.warn("菜单列表为空！");
            return ResultUtil.fail(1004, "请求成功，菜单列表为空！");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("erpMenus", menus);
        return ResultUtil.success(map);
    }

    /**
     * 新增菜单
     * @param menuName 菜单名称
     * @param menuUrl 菜单请求地址
     * @param menuDes 菜单描述
     */
    @RequestMapping("/addErpMenu")
    @ResponseBody
    public Map<String, Object> addErpMenu(String menuName, String menuUrl, String menuDes) {
        if (menuName == null || "".equals(menuName)) {
            logger.warn("新增菜单时，菜单名称为空");
            return ResultUtil.fail(1004, "菜单名称不能为空！");
        }
        if (menuUrl == null || "".equals(menuUrl)) {
            logger.warn("新增菜单时，菜单请求地址为空");
            return ResultUtil.fail(1004, "菜单请求地址不能为空！");
        }
        ErpMenuPOJO menu = new ErpMenuPOJO();
        menu.setMenuName(menuName);
        menu.setMenuUrl(menuUrl);
        menu.setMenuDes(menuDes == null ? "" : menuDes);
        Integer result = erpMenuService.addErpMenu(menu);
        if (result > 0)
            return ResultUtil.success("新增菜单成功");
        else {
            logger.warn("新增菜单失败，受影响行数为" + result + ",菜单名称：" + menuName + ",菜单链接地址：" + menuUrl + "，菜单描述：" + menuDes);
            return ResultUtil.fail(1004, "新增菜单失败");
        }
    }


    /**
     * 修改菜单信息
     *  @param menuUrl 菜单链接
     * @param menuName 菜单名称
     * @param menuDes  菜单描述
     * @param menuId   菜单id
     */
    @ResponseBody
    @RequestMapping("/changeErpMenu")
    public Map<String, Object> changeErpMenu(String menuUrl, String menuName, String menuDes, String menuId) {
        if (menuId == null || "".equals(menuId)) {
            logger.warn("修改菜单信息时，入参有误，菜单id为空");
            return ResultUtil.fail(1004, "菜单修改失败！");
        }
        Long erpRoleId = Long.parseLong(menuId);
        if ((menuName == null || "".equals(menuName)) && (menuDes == null || "".equals(menuDes)) && (menuUrl == null || "".equals(menuUrl))) {  //待修改&& or  ||
            logger.warn("修改菜单信息时，入参有误，菜单名称和菜单描述都为空");
            return ResultUtil.fail(1004, "菜单修改失败！");
        }
        ErpMenuPOJO menu = new ErpMenuPOJO();
        menu.setMenuId(erpRoleId);
        menu.setMenuName(menuName);
        menu.setMenuUrl(menuUrl);
        menu.setMenuDes(menuDes);
        Integer result = erpMenuService.addErpMenu(menu);
        if (result == 1) {
            return ResultUtil.success("菜单修改成功！");
        } else {
            return ResultUtil.fail(1004, "菜单修改失败！");
        }
    }
}
