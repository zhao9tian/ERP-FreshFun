package com.quxin.freshfun.service.erpuser;


import com.quxin.freshfun.model.erpuser.ErpMenuPOJO;

import java.util.List;

/**
 * Created by Ziming on 2016/10/28.
 */
public interface ErpMenuService {

    Integer addErpMenu(ErpMenuPOJO erpMenu);

    List<ErpMenuPOJO> queryErpMenu();
}
