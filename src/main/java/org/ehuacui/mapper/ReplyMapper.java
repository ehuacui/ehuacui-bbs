package org.ehuacui.mapper;

import org.ehuacui.model.Reply;

import java.util.List;

public interface ReplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Reply record);

    int insertSelective(Reply record);

    Reply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Reply record);

    int updateByPrimaryKeyWithBLOBs(Reply record);

    int updateByPrimaryKey(Reply record);

    List<Reply> selectByTid(Integer tid);

    List<Reply> selectByTid(Integer tid, Integer start, Integer limit);

    int countByTid(Integer tid);

    List<Reply> selectAll(Integer start, Integer limit);

    int countAll();

    List<Reply> selectByAuthor(String author, Integer start, Integer limit);

    int countByAuthor(String author);

    int deleteById(Integer id);

    int deleteByTid(Integer tid);
}