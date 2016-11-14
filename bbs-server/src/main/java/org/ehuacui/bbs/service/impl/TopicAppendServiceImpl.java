package org.ehuacui.bbs.service.impl;

import org.ehuacui.bbs.mapper.TopicAppendMapper;
import org.ehuacui.bbs.model.TopicAppend;
import org.ehuacui.bbs.service.TopicAppendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@Service
public class TopicAppendServiceImpl implements TopicAppendService {

    @Autowired
    private TopicAppendMapper topicAppendMapper;

    @Override
    public TopicAppend findById(Integer id) {
        return topicAppendMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询话题追加内容
     *
     * @param tid
     * @return
     */
    @Override
    public List<TopicAppend> findByTid(Integer tid) {
        return topicAppendMapper.selectByTid(tid);
    }

    /**
     * 删除话题追加内容
     *
     * @param tid
     */
    @Override
    public void deleteByTid(Integer tid) {
        topicAppendMapper.deleteByTid(tid);
    }

    @Override
    public void save(TopicAppend topicAppend) {
        topicAppendMapper.insert(topicAppend);
    }

    @Override
    public void update(TopicAppend topicAppend) {
        topicAppendMapper.updateByPrimaryKey(topicAppend);
    }
}
