package org.ehuacui.bbs.model;

import java.util.Date;

/**
 * Table: tb_notification
 */
public class Notification {
    /**
     * Column: tb_notification.id
     */
    private Integer id;

    /**
     * 是否已读：0默认 1已读
     * Column: tb_notification.is_read
     */
    private Boolean isRead;

    /**
     * 发起通知用户昵称
     * Column: tb_notification.author
     */
    private String author;

    /**
     * 要通知用户的昵称
     * Column: tb_notification.target_author
     */
    private String targetAuthor;

    /**
     * 录入时间
     * Column: tb_notification.in_time
     */
    private Date inTime;

    /**
     * 通知动作
     * Column: tb_notification.action
     */
    private String action;

    /**
     * 话题id
     * Column: tb_notification.tid
     */
    private Integer tid;

    /**
     * Column: tb_notification.content
     */
    private String content;

    /**
     * 话题的标题
     */
    private String title;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}