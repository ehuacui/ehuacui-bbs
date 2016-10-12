package org.ehuacui.bbs.model;

/**
 * Table: tb_role_permission
 */
public class RolePermission {

    /**
     * Column: tb_role_permission.id
     */
    private Integer id;
    /**
     * Column: tb_role_permission.rid
     */
    private Integer rid;

    /**
     * Column: tb_role_permission.pid
     */
    private Integer pid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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