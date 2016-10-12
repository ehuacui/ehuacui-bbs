package org.ehuacui.bbs.model;

/**
 * Table: tb_user_role
 */
public class UserRole {
    /**
     * Column: tb_user_role.id
     */
    private Integer id;
    /**
     * Column: tb_user_role.uid
     */
    private Integer uid;

    /**
     * Column: tb_user_role.rid
     */
    private Integer rid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }
}