package org.ehuacui.service;

import com.jfinal.plugin.activerecord.Page;
import org.ehuacui.module.Topic;
import org.ehuacui.service.impl.TopicService;

import java.util.List;

/**
 * Created by jianwei.zhou on 2016/8/15.
 */
public interface ITopicService {

    /**
     * 根据tab分页查询话题列表
     *
     * @param pageNumber
     * @param pageSize
     * @param tab
     * @return
     */
    Page<Topic> page(Integer pageNumber, Integer pageSize, String tab);

    /**
     * 分页查询所有话题
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Page<Topic> pageAll(Integer pageNumber, Integer pageSize);

    /**
     * 分页查询精华话题
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Page<Topic> pageGood(Integer pageNumber, Integer pageSize);

    /**
     * 分页查询无人回复话题
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Page<Topic> pageNoReply(Integer pageNumber, Integer pageSize);

    /**
     * 根据id查询话题内容并缓存
     *
     * @param id
     * @return
     */
    Topic findById(Integer id);

    /**
     * 查询当前作者其他话题
     * @param currentTopicId
     * @param author
     * @param limit
     * @return
     */
    List<Topic> findOtherTopicByAuthor(Integer currentTopicId, String author, Integer limit);

    /**
     * 查询用户
     * @param pageNumber
     * @param pageSize
     * @param author
     * @return
     */
    Page<Topic> pageByAuthor(Integer pageNumber, Integer pageSize, String author);

    /**
     * 查询所有话题
     * @return
     */
    List<Topic> findAll();

    /**
     * 删除话题
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 话题置顶
     * @param id
     */
    void top(Integer id);

    /**
     * 话题加精
     * @param id
     */
    void good(Integer id);

}
