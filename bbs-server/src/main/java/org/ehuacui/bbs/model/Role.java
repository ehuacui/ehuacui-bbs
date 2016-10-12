package org.ehuacui.bbs.model;

/**
 * Table: tb_role
 */
public class Role {
    /**
     * Column: tb_role.id
     */
    private Integer id;

    /**
     * Column: tb_role.name
     */
    private String name;

    /**
     * Column: tb_role.description
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