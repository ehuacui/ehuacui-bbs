package org.ehuacui.service.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import org.ehuacui.common.BaseModel;
import org.ehuacui.common.Constants.CacheEnum;
import org.ehuacui.module.TopicAppend;
import org.ehuacui.service.ITopicAppend;

import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class TopicAppendService implements ITopicAppend {

    private TopicAppend me = new TopicAppend();

    @Override
    public TopicAppend findById(Integer id) {
        return me.findById(id);
    }

    /**
     * 查询话题追加内容
     * @param tid
     * @return
     */
    @Override
    public List<TopicAppend> findByTid(Integer tid) {
        Cache cache = Redis.use();
        List list = cache.get(CacheEnum.topicappends.name() + tid);
        if(list == null) {
            list = me.find(
                    "select * from ehuacui_topic_append where is_delete = ? and tid = ? order by in_time",
                    false,
                    tid);
            cache.set(CacheEnum.topicappends.name() + tid, list);
        }
        return list;
    }

    /**
     * 删除话题追加内容
     * @param tid
     */
    @Override
    public void deleteByTid(Integer tid) {
        Db.update("update ehuacui_topic_append set is_delete = 1 where tid = ?", tid);
    }
}
