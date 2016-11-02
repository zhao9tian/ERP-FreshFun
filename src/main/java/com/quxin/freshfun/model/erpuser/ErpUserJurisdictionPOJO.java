package com.quxin.freshfun.model.erpuser;

/**
 * Created by Ziming on 2016/10/31.
 */
public class ErpUserJurisdictionPOJO {
    private Long id;                //主键id，自增长
    private Long userId;            //用户id
    private Long roleId;            //角色id
    private String operation;       //操作权限
    private Long created;           //创建时间
    private Long updated;           //更新时间
    private Byte isDeleted;         //是否删除，0 未删除，1 已删除

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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
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
}
