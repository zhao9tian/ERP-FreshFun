package com.quxin.freshfun.service.erpuser;


import com.quxin.freshfun.model.erpuser.ErpMenuPOJO;

import java.util.List;

/**
 * Created by Ziming on 2016/10/28.
 */
public interface ErpMenuService {
    /**
     * 新增菜单信息
     * @param erpMenu 菜单信息
     * @return 受影响行数
     */
    Integer addErpMenu(ErpMenuPOJO erpMenu);

    /**
     * 查询菜单列表
     * @return 菜单列表
     */
    List<ErpMenuPOJO> queryErpMenu();
}
