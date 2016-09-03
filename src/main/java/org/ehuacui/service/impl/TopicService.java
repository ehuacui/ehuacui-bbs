package org.ehuacui.service.impl;

import org.ehuacui.common.Page;
import org.ehuacui.common.ServiceHolder;
import org.ehuacui.mapper.TopicAppendMapper;
import org.ehuacui.mapper.TopicMapper;
import org.ehuacui.model.Topic;
import org.ehuacui.model.TopicAppend;
import org.ehuacui.service.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@Service
public class TopicService implements ITopicService {

    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private TopicAppendMapper topicAppendMapper;

    /**
     * 根据tab分页查询话题列表
     *
     * @param pageNumber
     * @param pageSize
     * @param tab
     * @return
     */
    @Override
    public Page<Topic> page(Integer pageNumber, Integer pageSize, String tab) {
        if (tab.equals("all")) {
            return pageAll(pageNumber, pageSize);
        } else if (tab.equals("good")) {
            return pageGood(pageNumber, pageSize);
        } else if (tab.equals("noreply")) {
            return pageNoReply(pageNumber, pageSize);
        } else {
            List<Topic> list = topicMapper.selectByTab(tab, pageNumber, pageSize);
            int total = topicMapper.countByTab(tab);
            return new Page<>(list, pageNumber, pageSize, total);
        }
    }

    /**
     * 分页查询所有话题
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public Page<Topic> pageAll(Integer pageNumber, Integer pageSize) {
        List<Topic> list = topicMapper.selectAll(pageNumber, pageSize);
        int total = topicMapper.countAll();
        return new Page<>(list, pageNumber, pageSize, total);
    }

    /**
     * 分页查询精华话题
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public Page<Topic> pageGood(Integer pageNumber, Integer pageSize) {
        List<Topic> list = topicMapper.selectAllGood(pageNumber, pageSize);
        int total = topicMapper.countAllGood();
        return new Page<>(list, pageNumber, pageSize, total);
    }

    /**
     * 分页查询无人回复话题
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public Page<Topic> pageNoReply(Integer pageNumber, Integer pageSize) {
        List<Topic> list = topicMapper.selectAllNoReply(pageNumber, pageSize);
        int total = topicMapper.countAllNoReply();
        return new Page<>(list, pageNumber, pageSize, total);
    }

    /**
     * 根据id查询话题内容并缓存
     *
     * @param id
     * @return
     */
    @Override
    public Topic findById(Integer id) {
        return topicMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询当前作者其他话题
     *
     * @param currentTopicId
     * @param author
     * @param limit
     * @return
     */
    @Override
    public List<Topic> findOtherTopicByAuthor(Integer currentTopicId, String author, Integer limit) {
        return topicMapper.selectOtherTopicByAuthor(currentTopicId, author, 0, limit);
    }

    /**
     * 查询用户
     *
     * @param pageNumber
     * @param pageSize
     * @param author
     * @return
     */
    @Override
    public Page<Topic> pageByAuthor(Integer pageNumber, Integer pageSize, String author) {
        List<Topic> list = topicMapper.selectByAuthor(author, pageNumber, pageSize);
        int total = topicMapper.countByAuthor(author);
        return new Page<>(list, pageNumber, pageSize, total);
    }

    /**
     * 查询所有话题
     *
     * @return
     */
    @Override
    public List<Topic> findAll() {
        List<Topic> topics = topicMapper.selectAll();
        for (Topic topic : topics) {
            List<TopicAppend> topicAppends = topicAppendMapper.selectByTid(topic.getId());
            topic.setTopicAppends(topicAppends);
        }
        return topics;
    }

    /**
     * 删除话题
     *
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
        topicMapper.deleteById(id);
    }

    /**
     * 话题置顶
     *
     * @param id
     */
    @Override
    public void top(Integer id) {
        topicMapper.updateTopById(id);
    }

    /**
     * 话题加精
     *
     * @param id
     */
    @Override
    public void good(Integer id) {
        topicMapper.updateGoodById(id);
    }

    @Override
    public void save(Topic topic) {
        topicMapper.insert(topic);
    }

    @Override
    public void update(Topic topic) {
        topicMapper.updateByPrimaryKey(topic);
    }
}
