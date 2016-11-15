package com.quxin.freshfun.service.erpuser.impl;

import com.quxin.freshfun.dao.ErpMenuMapper;
import com.quxin.freshfun.model.erpuser.ErpMenuPOJO;
import com.quxin.freshfun.service.erpuser.ErpMenuService;
import net.sf.json.JSON;
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

    /**
     * 新增菜单信息
     * @param erpMenu 菜单信息
     * @return 受影响行数
     */
    @Override
    public Integer addErpMenu(ErpMenuPOJO erpMenu) {
        if(erpMenu==null){
            logger.warn("新增菜单信息，入参有误");
            return 0;
        }
        erpMenu.setCreated(System.currentTimeMillis()/1000);
        erpMenu.setUpdated(System.currentTimeMillis()/1000);
        Integer result = erpMenuMapper.insertErpMenu(erpMenu);
        if(result==1){
            result = erpMenuMapper.updateErpMenuIdById(erpMenu.getId());
        }
        return result;
    }

    /**
     * 查询菜单列表
     * @return 菜单列表
     */
    @Override
    public List<ErpMenuPOJO> queryErpMenu() {
        return erpMenuMapper.selectErpMenu();
    }

    @Override
    public Integer modifyErpMenu(ErpMenuPOJO menu) {
        if(menu==null||menu.getMenuId()==null||menu.getMenuId()==0){
            logger.warn("修改菜单信息时，入参有误！");
            return 0;
        }
        menu.setUpdated(System.currentTimeMillis()/1000);
        Integer result = erpMenuMapper.updateErpMenuByMenuId(menu);
        if(result==0){
            logger.warn("修改菜单信息时，受影响行数为0");
        }
        return result;
    }
}
