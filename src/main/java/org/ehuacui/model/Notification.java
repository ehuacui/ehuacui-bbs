package org.ehuacui.model;

import java.util.Date;

/**
 * [STRATO MyBatis Generator]
 * Table: ehuacui_notification
@mbggenerated do_not_delete_during_merge 2016-08-16 13:07:44
 */
public class Notification {
    /**
     * Column: ehuacui_notification.id
    @mbggenerated 2016-08-16 13:07:44
     */
    private Integer id;

    /**
     *   是否已读：0默认 1已读
     * Column: ehuacui_notification.read
    @mbggenerated 2016-08-16 13:07:44
     */
    private Boolean read;

    /**
     *   发起通知用户昵称
     * Column: ehuacui_notification.author
    @mbggenerated 2016-08-16 13:07:44
     */
    private String author;

    /**
     *   要通知用户的昵称
     * Column: ehuacui_notification.target_author
    @mbggenerated 2016-08-16 13:07:44
     */
    private String targetAuthor;

    /**
     *   录入时间
     * Column: ehuacui_notification.in_time
    @mbggenerated 2016-08-16 13:07:44
     */
    private Date inTime;

    /**
     *   通知动作
     * Column: ehuacui_notification.action
    @mbggenerated 2016-08-16 13:07:44
     */
    private String action;

    /**
     *   话题id
     * Column: ehuacui_notification.tid
    @mbggenerated 2016-08-16 13:07:44
     */
    private Integer tid;

    /**
     * Column: ehuacui_notification.content
    @mbggenerated 2016-08-16 13:07:44
     */
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public String getTargetAuthor() {
        return targetAuthor;
    }

    public void setTargetAuthor(String targetAuthor) {
        this.targetAuthor = targetAuthor == null ? null : targetAuthor.trim();
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action == null ? null : action.trim();
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

}