package org.ehuacui.bbs.model;

import java.util.Date;

/**
 * Table: tb_topic_append
 */
public class TopicAppend {
    /**
     * Column: tb_topic_append.id
     */
    private Integer id;

    /**
     * Column: tb_topic_append.tid
     */
    private Integer tid;

    /**
     * Column: tb_topic_append.in_time
     */
    private Date inTime;

    /**
     * Column: tb_topic_append.is_delete
     */
    private Boolean isDelete;

    /**
     * Column: tb_topic_append.content
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