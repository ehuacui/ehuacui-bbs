package org.ehuacui.service.impl;

import org.ehuacui.common.Page;
import org.ehuacui.mapper.ReplyMapper;
import org.ehuacui.model.Reply;
import org.ehuacui.service.IReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@Service
public class ReplyService implements IReplyService {

    @Autowired
    private ReplyMapper replyMapper;

    /**
     * 根据话题id查询回复数量
     *
     * @param tid
     * @return
     */
    @Override
    public int findCountByTid(Integer tid) {
        return replyMapper.countByTid(tid);
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
        int total = replyMapper.countAll();
        List<Reply> list = replyMapper.selectAll(pageNumber, pageSize);
        return new Page<>(list, pageNumber, pageSize, total);
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
        int total = replyMapper.countByTid(tid);
        List<Reply> list = replyMapper.selectByTid(tid, pageNumber, pageSize);
        return new Page<>(list, pageNumber, pageSize, total);
    }

    /**
     * 根据话题id查询回复列表
     *
     * @param topicId
     * @return
     */
    @Override
    public List<Reply> findByTopicId(Integer topicId) {
        return replyMapper.selectByTid(topicId);
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
        int total = replyMapper.countByAuthor(author);
        List<Reply> list = replyMapper.selectByAuthor(author, pageNumber, pageSize);
        return new Page<>(list, pageNumber, pageSize, total);
    }

    /**
     * 删除话题回复内容
     *
     * @param tid
     */
    @Override
    public void deleteByTid(Integer tid) {
        replyMapper.deleteByTid(tid);
    }

    @Override
    public void deleteById(Integer id) {
        replyMapper.deleteById(id);
    }

    @Override
    public Reply findById(Integer id) {
        return replyMapper.selectByPrimaryKey(id);
    }

    @Override
    public void save(Reply reply) {
        replyMapper.insert(reply);
    }

    @Override
    public void update(Reply reply) {
        replyMapper.updateByPrimaryKey(reply);
    }
}
