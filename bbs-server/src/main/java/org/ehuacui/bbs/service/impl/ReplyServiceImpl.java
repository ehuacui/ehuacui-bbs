package org.ehuacui.bbs.service.impl;

import org.ehuacui.bbs.dto.PageDataBody;
import org.ehuacui.bbs.mapper.ReplyMapper;
import org.ehuacui.bbs.model.Reply;
import org.ehuacui.bbs.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@Service
public class ReplyServiceImpl implements ReplyService {

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
    public PageDataBody<Reply> findAll(Integer pageNumber, Integer pageSize) {
        int total = replyMapper.countAll();
        List<Reply> list = replyMapper.selectAll((pageNumber - 1) * pageSize, pageSize);
        return new PageDataBody<>(list, pageNumber, pageSize, total);
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
    public PageDataBody<Reply> page(Integer pageNumber, Integer pageSize, Integer tid) {
        int total = replyMapper.countByTid(tid);
        List<Reply> list = replyMapper.selectByTid(tid, (pageNumber - 1) * pageSize, pageSize);
        return new PageDataBody<>(list, pageNumber, pageSize, total);
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
    public PageDataBody<Reply> pageByAuthor(Integer pageNumber, Integer pageSize, String author) {
        int total = replyMapper.countByAuthor(author);
        List<Reply> list = replyMapper.selectByAuthor(author, (pageNumber - 1) * pageSize, pageSize);
        return new PageDataBody<>(list, pageNumber, pageSize, total);
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
