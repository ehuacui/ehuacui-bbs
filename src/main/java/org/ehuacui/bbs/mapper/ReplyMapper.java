package org.ehuacui.bbs.mapper;

import org.apache.ibatis.annotations.Param;
import org.ehuacui.bbs.model.Reply;

import java.util.List;

public interface ReplyMapper {
    int deleteByPrimaryKey(@Param("id") Integer id);

    int insert(Reply record);

    int insertSelective(Reply record);

    Reply selectByPrimaryKey(@Param("id") Integer id);

    int updateByPrimaryKeySelective(Reply record);

    int updateByPrimaryKeyWithBLOBs(Reply record);

    int updateByPrimaryKey(Reply record);

    List<Reply> selectByTid(@Param("tid") Integer tid);

    List<Reply> selectByTid(@Param("tid") Integer tid, @Param("start") Integer start, @Param("limit") Integer limit);

    int countByTid(@Param("tid") Integer tid);

    List<Reply> selectAll(@Param("start") Integer start, @Param("limit") Integer limit);

    int countAll();

    List<Reply> selectByAuthor(@Param("author") String author, @Param("start") Integer start, @Param("limit") Integer limit);

    int countByAuthor(@Param("author") String author);

    int deleteById(@Param("id") Integer id);

    int deleteByTid(@Param("tid") Integer tid);
}