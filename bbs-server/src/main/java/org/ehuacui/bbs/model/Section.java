package org.ehuacui.bbs.model;

/**
 * Table: tb_section
 */
public class Section {
    /**
     * Column: tb_section.id
     */
    private Integer id;

    /**
     * 板块名称
     * Column: tb_section.name
     */
    private String name;

    /**
     * 板块标签
     * Column: tb_section.tab
     */
    private String tab;

    /**
     * 是否显示，0不显示1显示
     * Column: tb_section.show_status
     */
    private Boolean showStatus;

    /**
     * 板块排序
     * Column: tb_section.display_index
     */
    private Integer displayIndex;

    /**
     * 默认显示板块 0默认，1显示
     * Column: tb_section.default_show
     */
    private Boolean defaultShow;

    /**
     * 模块父节点
     * Column: tb_section.pid
     */
    private Integer pid;

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

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab == null ? null : tab.trim();
    }

    public Boolean getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(Boolean showStatus) {
        this.showStatus = showStatus;
    }

    public Integer getDisplayIndex() {
        return displayIndex;
    }

    public void setDisplayIndex(Integer displayIndex) {
        this.displayIndex = displayIndex;
    }

    public Boolean getDefaultShow() {
        return defaultShow;
    }

    public void setDefaultShow(Boolean defaultShow) {
        this.defaultShow = defaultShow;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

}