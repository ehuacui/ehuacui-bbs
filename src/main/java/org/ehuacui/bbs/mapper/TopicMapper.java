package org.ehuacui.bbs.mapper;

import org.apache.ibatis.annotations.Param;
import org.ehuacui.bbs.model.Topic;

import java.util.List;

public interface TopicMapper {
    int deleteByPrimaryKey(@Param("id") Integer id);

    int insert(Topic record);

    int insertSelective(Topic record);

    Topic selectByPrimaryKey(@Param("id") Integer id);

    int updateByPrimaryKeySelective(Topic record);

    int updateByPrimaryKeyWithBLOBs(Topic record);

    int updateByPrimaryKey(Topic record);

    List<Topic> selectAll();

    List<Topic> selectAll(@Param("start") Integer start, @Param("limit") Integer limit);

    int countAll();

    List<Topic> selectByTab(@Param("tab") String tab, @Param("start") Integer start, @Param("limit") Integer limit);

    int countByTab(@Param("tab") String tab);

    List<Topic> selectAllGood(@Param("start") Integer start, @Param("limit") Integer limit);

    int countAllGood();

    List<Topic> selectAllNoReply(@Param("start") Integer start, @Param("limit") Integer limit);

    int countAllNoReply();

    List<Topic> selectOtherTopicByAuthor(@Param("id") Integer id, @Param("author") String author, @Param("start") Integer start, @Param("limit") Integer limit);

    List<Topic> selectByAuthor(@Param("author") String author, @Param("start") Integer start, @Param("limit") Integer limit);

    int countByAuthor(@Param("author") String author);

    int deleteById(@Param("id") Integer id);

    int updateTopById(@Param("id") Integer id);

    int updateGoodById(@Param("id") Integer id);

    List<Topic> selectByUid(@Param("uid") Integer uid, @Param("start") Integer start, @Param("limit") Integer limit);

    long countByUid(@Param("uid") Integer uid);
}