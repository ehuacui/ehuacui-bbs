package org.ehuacui.bbs.model;

/**
 * [STRATO MyBatis Generator]
 * Table: ehuacui_role_permission
@mbggenerated do_not_delete_during_merge 2016-08-16 13:07:45
 */
public class RolePermission {
    /**
     * Column: ehuacui_role_permission.rid
    @mbggenerated 2016-08-16 13:07:45
     */
    private Integer rid;

    /**
     * Column: ehuacui_role_permission.pid
    @mbggenerated 2016-08-16 13:07:45
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