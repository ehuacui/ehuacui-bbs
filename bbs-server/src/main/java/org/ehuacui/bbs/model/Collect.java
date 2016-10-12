package org.ehuacui.bbs.model;

import java.util.Date;

/**
 * Table: tb_collect
 */
public class Collect {
    /**
     * Column: tb_collect.id
     */
    private Integer id;

    /**
     * Column: tb_collect.tid
     */
    private Integer tid;

    /**
     * Column: tb_collect.uid
     */
    private Integer uid;

    /**
     * Column: tb_collect.in_time
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