package org.ehuacui.bbs.model;

import java.util.Date;

/**
 * Table: tb_reply
 */
public class Reply {
    /**
     * Column: tb_reply.id
     */
    private Integer id;

    /**
     * 话题id
     * Column: tb_reply.tid
     */
    private Integer tid;

    /**
     * 录入时间
     * Column: tb_reply.in_time
     */
    private Date inTime;

    /**
     * 当前回复用户id
     * Column: tb_reply.author
     */
    private String author;

    /**
     * 是否删除0 默认 1删除
     * Column: tb_reply.is_delete
     */
    private Boolean isDelete;

    /**
     * 回复内容
     * Column: tb_reply.content
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
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