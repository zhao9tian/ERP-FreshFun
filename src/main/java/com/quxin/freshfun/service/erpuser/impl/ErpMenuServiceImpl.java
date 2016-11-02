package com.quxin.freshfun.service.erpuser.impl;

import com.quxin.freshfun.dao.ErpMenuMapper;
import com.quxin.freshfun.model.erpuser.ErpMenuPOJO;
import com.quxin.freshfun.service.erpuser.ErpMenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Ziming on 2016/10/28.
 */
@Service("erpMenuService")
public class ErpMenuServiceImpl implements ErpMenuService{

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ErpMenuMapper erpMenuMapper;
    @Override
    public Integer addErpMenu(ErpMenuPOJO erpMenu) {
        Integer result = erpMenuMapper.insertErpMenu(erpMenu);
        if(result==1){
            result = erpMenuMapper.updateErpMenuIdById(erpMenu.getId());
        }
        return result;
    }

    @Override
    public List<ErpMenuPOJO> queryErpMenu() {
        return erpMenuMapper.selectErpMenu();
    }
}
