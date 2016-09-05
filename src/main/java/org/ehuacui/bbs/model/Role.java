package org.ehuacui.bbs.model;

/**
 * [STRATO MyBatis Generator]
 * Table: ehuacui_role
@mbggenerated do_not_delete_during_merge 2016-08-16 13:07:45
 */
public class Role {
    /**
     * Column: ehuacui_role.id
    @mbggenerated 2016-08-16 13:07:45
     */
    private Integer id;

    /**
     * Column: ehuacui_role.name
    @mbggenerated 2016-08-16 13:07:45
     */
    private String name;

    /**
     * Column: ehuacui_role.description
    @mbggenerated 2016-08-16 13:07:45
     */
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}