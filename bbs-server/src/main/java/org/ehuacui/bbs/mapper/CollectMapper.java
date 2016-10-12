package org.ehuacui.bbs.mapper;

import org.apache.ibatis.annotations.Param;
import org.ehuacui.bbs.model.Collect;

public interface CollectMapper {

    int deleteByPrimaryKey(@Param("id") Integer id);

    int insert(Collect record);

    int insertSelective(Collect record);

    Collect selectByPrimaryKey(@Param("id") Integer id);

    int updateByPrimaryKeySelective(Collect record);

    int updateByPrimaryKey(Collect record);

    Collect selectByTidAndUid(@Param("tid") Integer tid, @Param("uid") Integer uid);

    long countByTid(@Param("tid") Integer tid);
}