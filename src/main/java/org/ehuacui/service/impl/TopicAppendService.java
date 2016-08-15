package org.ehuacui.service.impl;

import org.ehuacui.common.DaoHolder;
import org.ehuacui.module.TopicAppend;
import org.ehuacui.service.ITopicAppendService;

import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class TopicAppendService implements ITopicAppendService {

    @Override
    public TopicAppend findById(Integer id) {
        return DaoHolder.topicAppendDao.findById(id);
    }

    /**
     * 查询话题追加内容
     *
     * @param tid
     * @return
     */
    @Override
    public List<TopicAppend> findByTid(Integer tid) {
        return DaoHolder.topicAppendDao.findByTid(tid);
    }

    /**
     * 删除话题追加内容
     *
     * @param tid
     */
    @Override
    public void deleteByTid(Integer tid) {
        DaoHolder.topicAppendDao.deleteByTid(tid);
    }
}
