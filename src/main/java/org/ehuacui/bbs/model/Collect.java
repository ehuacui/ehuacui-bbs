package org.ehuacui.bbs.model;

import java.util.Date;

/**
 * [STRATO MyBatis Generator]
 * Table: ehuacui_collect
@mbggenerated do_not_delete_during_merge 2016-08-16 13:07:44
 */
public class Collect {
    /**
     * Column: ehuacui_collect.id
    @mbggenerated 2016-08-16 13:07:44
     */
    private Integer id;

    /**
     * Column: ehuacui_collect.tid
    @mbggenerated 2016-08-16 13:07:44
     */
    private Integer tid;

    /**
     * Column: ehuacui_collect.uid
    @mbggenerated 2016-08-16 13:07:44
     */
    private Integer uid;

    /**
     * Column: ehuacui_collect.in_time
    @mbggenerated 2016-08-16 13:07:44
     */
    private Date inTime;

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

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }
}