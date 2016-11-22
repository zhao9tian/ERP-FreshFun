package com.quxin.freshfun.model.erpuser;

/**
 * Created by Ziming on 2016/10/27.
 */
public class ErpUserPOJO {
    private Long id;                    //主键自增id
    private Long userId;                //用户唯一标识
    private String userName;         //用户帐号
    private String password;        //用户密码
    private Byte isAdmin;           //是否是悦选超管用户
    private String appName;            //用户平台名称
    private Long appId;           //用户平台appid
    private String appIdStr;           //用户平台appid字符串
    private Long created;
    private Long updated;
    private Byte isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAppIdStr() {
        return appIdStr;
    }

    public void setAppIdStr(String appIdStr) {
        this.appIdStr = appIdStr;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Byte getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Byte isAdmin) {
        this.isAdmin = isAdmin;
    }
}
