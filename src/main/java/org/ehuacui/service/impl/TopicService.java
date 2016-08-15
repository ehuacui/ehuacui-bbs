package org.ehuacui.service.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import org.ehuacui.common.Constants.CacheEnum;
import org.ehuacui.common.ServiceHolder;
import org.ehuacui.module.Topic;
import org.ehuacui.module.TopicAppend;
import org.ehuacui.service.ITopic;

import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class TopicService implements ITopic {

    private Topic me = new Topic();

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
            return me.paginate(
                    pageNumber,
                    pageSize,
                    "select t.* ",
                    "from ehuacui_topic t where t.is_delete = ? and t.tab = ? order by t.top desc, t.last_reply_time desc",
                    false,
                    tab
            );
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
        return me.paginate(
                pageNumber,
                pageSize,
                "select t.* ",
                "from ehuacui_topic t where t.is_delete = ? order by t.top desc, t.last_reply_time desc",
                false
        );
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
        return me.paginate(
                pageNumber,
                pageSize,
                "select t.* ",
                "from ehuacui_topic t where t.is_delete = ? and t.good = ? order by t.top desc, t.last_reply_time desc",
                false,
                true
        );
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
        return me.paginate(
                pageNumber,
                pageSize,
                "select t.* ",
                "from ehuacui_topic t where t.is_delete = ? and t.id not in (select r.tid from ehuacui_reply r) order by t.top desc, t.last_reply_time desc",
                false
        );
    }

    /**
     * 根据id查询话题内容并缓存
     *
     * @param id
     * @return
     */
    @Override
    public Topic findById(Integer id) {
        Cache cache = Redis.use();
        Topic topic = cache.get(CacheEnum.topic.name() + id);
        if (topic == null) {
            topic = me.findById(id);
            cache.set(CacheEnum.topic.name() + id, topic);
        }
        return topic;
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
        return me.find(
                "select * from ehuacui_topic where is_delete = ? and id <> ? and author = ? order by in_time desc limit ?",
                false,
                currentTopicId,
                author,
                limit
        );
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
        return me.paginate(
                pageNumber,
                pageSize,
                "select * ",
                "from ehuacui_topic where is_delete = ? and author = ? order by in_time desc",
                false,
                author
        );
    }

    /**
     * 查询所有话题
     *
     * @return
     */
    @Override
    public List<Topic> findAll() {
        List<Topic> topics = me.find("select * from ehuacui_topic where is_delete = ?", false);
        for (Topic topic : topics) {
            List<TopicAppend> topicAppends = ServiceHolder.topicAppendService.findByTid(topic.getInt("id"));
            topic.put("topicAppends", topicAppends);
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
        Db.update("update ehuacui_topic set is_delete = ? where id = ?", true, id);
    }

    /**
     * 话题置顶
     *
     * @param id
     */
    @Override
    public void top(Integer id) {
        Topic topic = findById(id);
        topic.set("top", !topic.getBoolean("top")).update();
    }

    /**
     * 话题加精
     *
     * @param id
     */
    @Override
    public void good(Integer id) {
        Topic topic = findById(id);
        topic.set("good", !topic.getBoolean("good")).update();
    }
    
}
