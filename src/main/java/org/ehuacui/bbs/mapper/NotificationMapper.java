package org.ehuacui.bbs.mapper;

import org.apache.ibatis.annotations.Param;
import org.ehuacui.bbs.model.Notification;

import java.util.List;

public interface NotificationMapper {

    int deleteByPrimaryKey(@Param("id") Integer id);

    int insert(Notification record);

    int insertSelective(Notification record);

    Notification selectByPrimaryKey(@Param("id") Integer id);

    int updateByPrimaryKeySelective(Notification record);

    int updateByPrimaryKeyWithBLOBs(Notification record);

    int updateByPrimaryKey(Notification record);

    int countNotReadByAuthor(@Param("author") String author);

    int countByAuthor(@Param("author") String author);

    List<Notification> selectByAuthor(@Param("author") String author, @Param("start") Integer start, @Param("limit") Integer limit);

    int updateUnreadToRead(@Param("author") String author);
}