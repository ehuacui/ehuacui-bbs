package org.ehuacui.mapper;

import org.ehuacui.model.Collect;

import java.util.List;

public interface CollectMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Collect record);

    int insertSelective(Collect record);

    Collect selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Collect record);

    int updateByPrimaryKey(Collect record);

    List<Collect> selectByUid(Integer uid, Integer start, Integer limit);

    Collect selectByTidAndUid(Integer tid, Integer uid);

    long countByTid(Integer tid);

    long countByUid(Integer uid);
}