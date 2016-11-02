package com.quxin.freshfun.test;

import com.quxin.freshfun.controller.goods.GoodsController;
import com.quxin.freshfun.model.erpuser.ErpMenuPOJO;
import com.quxin.freshfun.model.erpuser.ErpRolePOJO;
import com.quxin.freshfun.model.erpuser.ErpUserJurisdictionPOJO;
import com.quxin.freshfun.model.erpuser.ErpUserPOJO;
import com.quxin.freshfun.service.erpuser.ErpMenuService;
import com.quxin.freshfun.service.erpuser.ErpRoleService;
import com.quxin.freshfun.service.erpuser.ErpUserJurisdictionService;
import com.quxin.freshfun.service.erpuser.ErpUserService;
import org.junit.*;
import org.junit.Test;

import java.util.List;

/**
 * Created by Ziming on 2016/11/1.
 */
public class ErpTest extends TestBase {

    private ErpUserService erpUserService;
    private ErpMenuService erpMenuService;
    private ErpRoleService erpRoleService;
    private ErpUserJurisdictionService erpUserJurisdictionService;
    @Before
    public void setUp() throws Exception {
        erpUserService = getContext().getBean("erpUserService", ErpUserService.class);
        erpMenuService = getContext().getBean("erpMenuService", ErpMenuService.class);
        erpRoleService = getContext().getBean("erpRoleService", ErpRoleService.class);
        erpUserJurisdictionService = getContext().getBean("erpUserJurisdictionService", ErpUserJurisdictionService.class);
    }

    @After
    public void tearDown() throws Exception {
        getContext().close();
    }

    @org.junit.Test
    public void testRegister(){
        ErpUserPOJO user = new ErpUserPOJO();
        user.setUserAppId("testAppId4asd486");
        user.setCreated(System.currentTimeMillis()/1000);
        user.setUpdated(System.currentTimeMillis()/1000);
        user.setUserPassword("testPwd4654asd645a41");
        user.setUserName("testUserName1");
        user.setUserAccount("testUserAccount1");
        user.setUserEmail("testEmail1@163.com");
        user.setUserPhoneNumber("15038292018");
        user.setUserHeadImg("testHeadImgUrl1");
        Integer result = erpUserService.addErpUser(user);
        if(result==1){
            System.out.println("注册成功！");
        }else{
            System.out.println("注册失败！");
        }
    }
    @Test
    public void testLogin(){
        String userAccount = "testUserAccount";
        String userPwd = "testPwd4654asd645a4";
        ErpUserPOJO user = erpUserService.erpUserLogin(userAccount);
        if(user!=null){
            if(user.getUserPassword()!=null&&userPwd.equals(user.getUserPassword())){
                System.out.println("登录成功！用户昵称："+user.getUserName());
            }else{
                System.out.println("登录失败！密码错误");
            }
        }else{
            System.out.println("登录失败！没有该用户");
        }
    }

    @Test
    public void testFindUsersByAppId() {
        String appId = "testAppId4asd486";
        List<ErpUserPOJO> userList = erpUserService.queryErpUserByAppId(appId);
        if (userList!=null&&userList.size()>0) {
            for(ErpUserPOJO user : userList){
                System.out.println("用户昵称："+user.getUserName());
            }
        }else{
            System.out.println("查询结果为空！");
        }
    }

    @Test
    public void testAddMenu(){
        ErpMenuPOJO menu = new ErpMenuPOJO();
        menu.setCreated(System.currentTimeMillis()/1000);
        menu.setUpdated(System.currentTimeMillis()/1000);
        menu.setMenuName("订单管理");
        menu.setMenuUrl("/order/orderManager.html");
        menu.setMenuDes("订单管理菜单，支持对订单的各种操作");
        Integer result = erpMenuService.addErpMenu(menu);
        if(result==1){
            System.out.println("菜单新增成功！");
        }else{
            System.out.println("菜单新增失败！");
        }
    }

    @Test
    public void testAddRole(){
        ErpRolePOJO role = new ErpRolePOJO();
        role.setCreated(System.currentTimeMillis()/1000);
        role.setUpdated(System.currentTimeMillis()/1000);
        role.setRoleName("商品管理员");
        role.setRoleDes("具有商品管理权限，可操作商品管理菜单");
        Integer result = erpRoleService.addErpRole(role);
        if(result==1){
            System.out.println("角色新增成功！");
        }else{
            System.out.println("角色新增失败！");
        }
    }

    @Test
    public void testFindMenus(){
        List<ErpMenuPOJO> menus = erpMenuService.queryErpMenu();
        for(ErpMenuPOJO menu : menus){
            System.out.println("菜单名称："+menu.getMenuName()+",请求地址是："+menu.getMenuUrl());
        }
    }

    @Test
    public void testAuthority(){
        ErpUserJurisdictionPOJO erpUserJuris = new ErpUserJurisdictionPOJO();
        erpUserJuris.setUpdated(System.currentTimeMillis()/1000);
        erpUserJuris.setCreated(System.currentTimeMillis()/1000);
        erpUserJuris.setOperation("");
        erpUserJuris.setRoleId(1l);
        erpUserJuris.setUserId(2l);
        Integer result = erpUserJurisdictionService.addErpUserJuris(erpUserJuris);
        if(result==1){
            System.out.println("授权成功！");
        }else{
            System.out.println("授权失败！");
        }
    }

    @Test
    public void testFindUserOperation(){
        Long userId = 1l;
        List<ErpUserJurisdictionPOJO> jurisList = erpUserJurisdictionService.queryErpUserJurisByUserId(userId);
        if(jurisList!=null&&jurisList.size()>0){
            for(ErpUserJurisdictionPOJO judis : jurisList){
                System.out.println("该用户的角色id："+judis.getRoleId());
            }
        }
    }
}
