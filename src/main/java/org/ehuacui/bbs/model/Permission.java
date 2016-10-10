package org.ehuacui.bbs.model;

import java.util.List;

/**
 * Table: tb_permission
 */
public class Permission {
    /**
     * Column: tb_permission.id
     */
    private Integer id;

    /**
     * 权限名称
     * Column: tb_permission.name
     */
    private String name;

    /**
     * 授权路径
     * Column: tb_permission.url
     */
    private String url;

    /**
     * 权限描述
     * Column: tb_permission.description
     */
    private String description;

    /**
     * 父节点0
     * Column: tb_permission.pid
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