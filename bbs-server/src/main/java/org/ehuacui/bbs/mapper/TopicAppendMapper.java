package org.ehuacui.bbs.mapper;

import org.apache.ibatis.annotations.Param;
import org.ehuacui.bbs.model.TopicAppend;

import java.util.List;

public interface TopicAppendMapper {
    int deleteByPrimaryKey(@Param("id") Integer id);

    int insert(TopicAppend record);

    int insertSelective(TopicAppend record);

    TopicAppend selectByPrimaryKey(@Param("id") Integer id);

    int updateByPrimaryKeySelective(TopicAppend record);

    int updateByPrimaryKeyWithBLOBs(TopicAppend record);

    int updateByPrimaryKey(TopicAppend record);

    List<TopicAppend> selectByTid(@Param("tid") Integer tid);

    int deleteByTid(@Param("tid") Integer tid);
}