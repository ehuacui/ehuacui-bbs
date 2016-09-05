package org.ehuacui.bbs.model;

import java.util.Date;

/**
 * [STRATO MyBatis Generator]
 * Table: ehuacui_topic_append
@mbggenerated do_not_delete_during_merge 2016-08-16 13:07:45
 */
public class TopicAppend {
    /**
     * Column: ehuacui_topic_append.id
    @mbggenerated 2016-08-16 13:07:45
     */
    private Integer id;

    /**
     * Column: ehuacui_topic_append.tid
    @mbggenerated 2016-08-16 13:07:45
     */
    private Integer tid;

    /**
     * Column: ehuacui_topic_append.in_time
    @mbggenerated 2016-08-16 13:07:45
     */
    private Date inTime;

    /**
     * Column: ehuacui_topic_append.is_delete
    @mbggenerated 2016-08-16 13:07:45
     */
    private Boolean isDelete;

    /**
     * Column: ehuacui_topic_append.content
    @mbggenerated 2016-08-16 13:07:45
     */
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}