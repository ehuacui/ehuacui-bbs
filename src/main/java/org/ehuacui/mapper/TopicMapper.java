package org.ehuacui.mapper;

import org.ehuacui.model.Topic;

import java.util.List;

public interface TopicMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Topic record);

    int insertSelective(Topic record);

    Topic selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Topic record);

    int updateByPrimaryKeyWithBLOBs(Topic record);

    int updateByPrimaryKey(Topic record);

    List<Topic> selectAll();

    List<Topic> selectAll(Integer start, Integer limit);

    int countAll();

    List<Topic> selectByTab(String tab, Integer start, Integer limit);

    int countByTab(String tab);

    List<Topic> selectAllGood(Integer start, Integer limit);

    int countAllGood();

    List<Topic> selectAllNoReply(Integer start, Integer limit);

    int countAllNoReply();

    List<Topic> selectOtherTopicByAuthor(Integer id, String author, Integer start, Integer limit);

    List<Topic> selectByAuthor(String author, Integer start, Integer limit);

    int countByAuthor(String author);

    int deleteById(Integer id);

    int updateTopById(Integer id);

    int updateGoodById(Integer id);
}