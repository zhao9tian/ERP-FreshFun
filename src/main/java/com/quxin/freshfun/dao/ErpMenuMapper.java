package com.quxin.freshfun.dao;

import com.quxin.freshfun.model.erpuser.ErpMenuPOJO;

import java.util.List;
import java.util.Map;

/**
 * Created by Ziming on 2016/10/27.
 */
public interface ErpMenuMapper {
    /**
     * 插入菜单信息
     * @param menu 菜单信息
     * @return 受影响行数
     */
    Integer insertErpMenu(ErpMenuPOJO menu);

    /**
     * 更新roleId
     * @param id id
     * @return 受影响行数
     */
    Integer updateErpMenuIdById(Long id);

    /**
     * 查询菜单列表
     * @return  菜单列表
     */
    List<ErpMenuPOJO> selectErpMenu();

    /**
     * 更新菜单信息
     * @param menu 菜单信息
     * @return 受影响行数
     */
    Integer updateErpMenuByMenuId(ErpMenuPOJO menu);
}
