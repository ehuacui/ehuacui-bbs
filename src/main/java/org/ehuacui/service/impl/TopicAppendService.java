package org.ehuacui.service.impl;

import org.ehuacui.mapper.TopicAppendMapper;
import org.ehuacui.model.TopicAppend;
import org.ehuacui.service.ITopicAppendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@Service
public class TopicAppendService implements ITopicAppendService {

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
}
