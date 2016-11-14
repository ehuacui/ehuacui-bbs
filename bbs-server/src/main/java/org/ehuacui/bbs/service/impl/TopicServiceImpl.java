package org.ehuacui.bbs.service.impl;

import org.ehuacui.bbs.dto.PageDataBody;
import org.ehuacui.bbs.mapper.TopicAppendMapper;
import org.ehuacui.bbs.mapper.TopicMapper;
import org.ehuacui.bbs.model.Topic;
import org.ehuacui.bbs.model.TopicAppend;
import org.ehuacui.bbs.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@Service
public class TopicServiceImpl implements TopicService {

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
    public PageDataBody<Topic> page(Integer pageNumber, Integer pageSize, String tab) {
        if (tab.equals("all")) {
            return pageAll(pageNumber, pageSize);
        } else if (tab.equals("good")) {
            return pageGood(pageNumber, pageSize);
        } else if (tab.equals("noreply")) {
            return pageNoReply(pageNumber, pageSize);
        } else {
            List<Topic> list = topicMapper.selectByTab(tab, (pageNumber - 1) * pageSize, pageSize);
            int total = topicMapper.countByTab(tab);
            return new PageDataBody<>(list, pageNumber, pageSize, total);
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
    public PageDataBody<Topic> pageAll(Integer pageNumber, Integer pageSize) {
        List<Topic> list = topicMapper.selectAll((pageNumber - 1) * pageSize, pageSize);
        int total = topicMapper.countAll();
        return new PageDataBody<>(list, pageNumber, pageSize, total);
    }

    /**
     * 分页查询精华话题
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public PageDataBody<Topic> pageGood(Integer pageNumber, Integer pageSize) {
        List<Topic> list = topicMapper.selectAllGood((pageNumber - 1) * pageSize, pageSize);
        int total = topicMapper.countAllGood();
        return new PageDataBody<>(list, pageNumber, pageSize, total);
    }

    /**
     * 分页查询无人回复话题
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public PageDataBody<Topic> pageNoReply(Integer pageNumber, Integer pageSize) {
        List<Topic> list = topicMapper.selectAllNoReply((pageNumber - 1) * pageSize, pageSize);
        int total = topicMapper.countAllNoReply();
        return new PageDataBody<>(list, pageNumber, pageSize, total);
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
    public PageDataBody<Topic> pageByAuthor(Integer pageNumber, Integer pageSize, String author) {
        List<Topic> list = topicMapper.selectByAuthor(author, (pageNumber - 1) * pageSize, pageSize);
        int total = topicMapper.countByAuthor(author);
        return new PageDataBody<>(list, pageNumber, pageSize, total);
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
        Topic topic = topicMapper.selectByPrimaryKey(id);
        if (topic != null) {
            topic.setIsTop(!topic.getIsTop());
            topicMapper.updateByPrimaryKey(topic);
        }
    }

    /**
     * 话题加精
     *
     * @param id
     */
    @Override
    public void good(Integer id) {
        Topic topic = topicMapper.selectByPrimaryKey(id);
        if (topic != null) {
            topic.setIsGood(!topic.getIsGood());
            topicMapper.updateByPrimaryKey(topic);
        }
    }

    @Override
    public void save(Topic topic) {
        topicMapper.insert(topic);
    }

    @Override
    public void update(Topic topic) {
        topicMapper.updateByPrimaryKey(topic);
    }

    /**
     * 收藏话题列表
     *
     * @param pageNumber
     * @param pageSize
     * @param uid
     * @return
     */
    @Override
    public PageDataBody<Topic> findByUid(Integer pageNumber, Integer pageSize, Integer uid) {
        List<Topic> list = topicMapper.selectByUid(uid, (pageNumber - 1) * pageSize, pageSize);
        long total = topicMapper.countByUid(uid);
        return new PageDataBody<>(list, pageNumber, pageSize, total);
    }

    /**
     * 查询用户收藏的数量
     *
     * @param uid
     * @return
     */
    @Override
    public Long countByUid(Integer uid) {
        return topicMapper.countByUid(uid);
    }
}
