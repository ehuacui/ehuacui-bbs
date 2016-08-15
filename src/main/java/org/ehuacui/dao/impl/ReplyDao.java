package org.ehuacui.dao.impl;

import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import org.ehuacui.dao.IReplyDao;
import org.ehuacui.module.Reply;

import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class ReplyDao implements IReplyDao {

    private Reply me = new Reply();

    /**
     * 根据话题id查询回复数量
     *
     * @param tid
     * @return
     */
    @Override
    public int findCountByTid(Integer tid) {
        return me.find("select id from ehuacui_reply where is_delete = ? and tid = ?", false, tid).size();
    }

    /**
     * 分页查询全部话题
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public Page<Reply> findAll(Integer pageNumber, Integer pageSize) {
        return me.paginate(
                pageNumber,
                pageSize,
                "select r.author as replyAuthor, r.content, r.in_time, t.id as tid, t.author as topicAuthor, t.title ",
                "from ehuacui_reply r left join ehuacui_topic t on r.tid = t.id order by r.in_time desc"
        );
    }

    /**
     * 分页查询话题的回复列表
     *
     * @param pageNumber
     * @param pageSize
     * @param tid
     * @return
     */
    @Override
    public Page<Reply> page(Integer pageNumber, Integer pageSize, Integer tid) {
        int count = this.findCountByTid(tid);
        pageNumber = pageNumber == null ? (count / PropKit.getInt("replyPageSize")) + 1 : pageNumber;
        return me.paginate(
                pageNumber,
                pageSize,
                "select * ", "from ehuacui_reply where is_delete = ? and tid = ?",
                false,
                tid
        );
    }

    /**
     * 根据话题id查询回复列表
     *
     * @param topicId
     * @return
     */
    @Override
    public List<Reply> findByTopicId(Integer topicId) {
        return me.find("select * from ehuacui_reply where is_delete = ? and tid = ?", false, topicId);
    }

    /**
     * 分页查询回复列表
     *
     * @param pageNumber
     * @param pageSize
     * @param author
     * @return
     */
    @Override
    public Page<Reply> pageByAuthor(Integer pageNumber, Integer pageSize, String author) {
        return me.paginate(
                pageNumber,
                pageSize,
                "select t.title, t.author as topicAuthor, t.in_time, r.tid, r.content ",
                "from ehuacui_topic t, ehuacui_reply r where t.is_delete = ? and r.is_delete = ? and t.id = r.tid and r.author = ? order by r.in_time desc",
                false,
                false,
                author
        );
    }

    /**
     * 删除话题回复内容
     *
     * @param tid
     */
    @Override
    public void deleteByTid(Integer tid) {
        Db.update("update ehuacui_reply set is_delete = ? where tid = ?", true, tid);
    }

    @Override
    public void deleteById(Integer id) {
        Db.update("update ehuacui_reply set is_delete = ? where id = ?", true, id);
    }

    @Override
    public Reply findById(Integer id) {
        return me.findById(id);
    }
}
