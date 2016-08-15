package org.ehuacui.service.impl;

import com.jfinal.plugin.activerecord.Page;
import org.ehuacui.common.DaoHolder;
import org.ehuacui.module.Topic;
import org.ehuacui.service.ITopicService;

import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class TopicService implements ITopicService {

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
        return DaoHolder.topicDao.page(pageNumber, pageSize, tab);
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
        return DaoHolder.topicDao.pageAll(pageNumber, pageSize);
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
        return DaoHolder.topicDao.pageGood(pageNumber, pageSize);
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
        return DaoHolder.topicDao.pageNoReply(pageSize, pageSize);
    }

    /**
     * 根据id查询话题内容并缓存
     *
     * @param id
     * @return
     */
    @Override
    public Topic findById(Integer id) {
        return DaoHolder.topicDao.findById(id);
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
        return DaoHolder.topicDao.findOtherTopicByAuthor(currentTopicId, author, limit);
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
        return DaoHolder.topicDao.pageByAuthor(pageNumber, pageSize, author);
    }

    /**
     * 查询所有话题
     *
     * @return
     */
    @Override
    public List<Topic> findAll() {
        return DaoHolder.topicDao.findAll();
    }

    /**
     * 删除话题
     *
     * @param id
     */
    @Override
    public void deleteById(Integer id) {
        DaoHolder.topicDao.deleteById(id);
    }

    /**
     * 话题置顶
     *
     * @param id
     */
    @Override
    public void top(Integer id) {
        DaoHolder.topicDao.top(id);
    }

    /**
     * 话题加精
     *
     * @param id
     */
    @Override
    public void good(Integer id) {
        DaoHolder.topicDao.good(id);
    }

}
