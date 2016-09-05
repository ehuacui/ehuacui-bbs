package org.ehuacui.bbs.model;

import java.util.List;

/**
 * [STRATO MyBatis Generator]
 * Table: ehuacui_permission
 *
 * @mbggenerated do_not_delete_during_merge 2016-08-16 13:07:45
 */
public class Permission {
    /**
     * Column: ehuacui_permission.id
     *
     * @mbggenerated 2016-08-16 13:07:45
     */
    private Integer id;

    /**
     * 权限名称
     * Column: ehuacui_permission.name
     *
     * @mbggenerated 2016-08-16 13:07:45
     */
    private String name;

    /**
     * 授权路径
     * Column: ehuacui_permission.url
     *
     * @mbggenerated 2016-08-16 13:07:45
     */
    private String url;

    /**
     * 权限描述
     * Column: ehuacui_permission.description
     *
     * @mbggenerated 2016-08-16 13:07:45
     */
    private String description;

    /**
     * 父节点0
     * Column: ehuacui_permission.pid
     *
     * @mbggenerated 2016-08-16 13:07:45
     */
    private Integer pid;

    private List<Permission> childPermissions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public List<Permission> getChildPermissions() {
        return childPermissions;
    }

    public void setChildPermissions(List<Permission> childPermissions) {
        this.childPermissions = childPermissions;
    }
}