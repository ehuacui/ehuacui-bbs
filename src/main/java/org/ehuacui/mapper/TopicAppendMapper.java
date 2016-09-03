package org.ehuacui.mapper;

import org.ehuacui.model.TopicAppend;

import java.util.List;

public interface TopicAppendMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TopicAppend record);

    int insertSelective(TopicAppend record);

    TopicAppend selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TopicAppend record);

    int updateByPrimaryKeyWithBLOBs(TopicAppend record);

    int updateByPrimaryKey(TopicAppend record);

    List<TopicAppend> selectByTid(Integer tid);

    int deleteByTid(Integer tid);
}