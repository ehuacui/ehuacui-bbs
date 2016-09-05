package org.ehuacui.bbs.model;

/**
 * [STRATO MyBatis Generator]
 * Table: ehuacui_section
@mbggenerated do_not_delete_during_merge 2016-08-16 13:07:45
 */
public class Section {
    /**
     * Column: ehuacui_section.id
    @mbggenerated 2016-08-16 13:07:45
     */
    private Integer id;

    /**
     *   板块名称
     * Column: ehuacui_section.name
    @mbggenerated 2016-08-16 13:07:45
     */
    private String name;

    /**
     *   板块标签
     * Column: ehuacui_section.tab
    @mbggenerated 2016-08-16 13:07:45
     */
    private String tab;

    /**
     *   是否显示，0不显示1显示
     * Column: ehuacui_section.show_status
    @mbggenerated 2016-08-16 13:07:45
     */
    private Boolean showStatus;

    /**
     *   板块排序
     * Column: ehuacui_section.display_index
    @mbggenerated 2016-08-16 13:07:45
     */
    private Integer displayIndex;

    /**
     *   默认显示板块 0默认，1显示
     * Column: ehuacui_section.default_show
    @mbggenerated 2016-08-16 13:07:45
     */
    private Boolean defaultShow;

    /**
     *   模块父节点
     * Column: ehuacui_section.pid
    @mbggenerated 2016-08-16 13:07:45
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

    public String showStatus(Section section) {
        if (showStatus) {
            return "true";
        } else {
            return "false";
        }
    }
}