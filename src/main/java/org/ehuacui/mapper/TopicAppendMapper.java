package org.ehuacui.mapper;

import org.ehuacui.model.TopicAppend;

public interface TopicAppendMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TopicAppend record);

    int insertSelective(TopicAppend record);

    TopicAppend selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TopicAppend record);

    int updateByPrimaryKeyWithBLOBs(TopicAppend record);

    int updateByPrimaryKey(TopicAppend record);
}