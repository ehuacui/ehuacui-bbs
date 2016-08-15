package org.ehuacui.service.impl;

import com.jfinal.plugin.activerecord.Page;
import org.ehuacui.common.DaoHolder;
import org.ehuacui.module.Reply;
import org.ehuacui.service.IReplyService;

import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class ReplyService implements IReplyService {

    /**
     * 根据话题id查询回复数量
     *
     * @param tid
     * @return
     */
    @Override
    public int findCountByTid(Integer tid) {
        return DaoHolder.replyDao.findCountByTid(tid);
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
        return DaoHolder.replyDao.findAll(pageNumber, pageSize);
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
        return DaoHolder.replyDao.page(pageNumber, pageSize, tid);
    }

    /**
     * 根据话题id查询回复列表
     *
     * @param topicId
     * @return
     */
    @Override
    public List<Reply> findByTopicId(Integer topicId) {
        return DaoHolder.replyDao.findByTopicId(topicId);
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
        return DaoHolder.replyDao.pageByAuthor(pageNumber, pageSize, author);
    }

    /**
     * 删除话题回复内容
     *
     * @param tid
     */
    @Override
    public void deleteByTid(Integer tid) {
        DaoHolder.replyDao.deleteByTid(tid);
    }

    @Override
    public void deleteById(Integer id) {
        DaoHolder.replyDao.deleteById(id);
    }

    @Override
    public Reply findById(Integer id) {
        return DaoHolder.replyDao.findById(id);
    }
}
