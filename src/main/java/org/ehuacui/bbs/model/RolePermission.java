package org.ehuacui.bbs.model;

/**
 * Table: ehuacui_role_permission
 */
public class RolePermission {
    /**
     * Column: ehuacui_role_permission.rid
     */
    private Integer rid;

    /**
     * Column: ehuacui_role_permission.pid
     */
    private Integer pid;

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
}