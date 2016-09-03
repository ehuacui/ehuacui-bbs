package org.ehuacui.mapper;

import org.ehuacui.model.Notification;

import java.util.List;

public interface NotificationMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Notification record);

    int insertSelective(Notification record);

    Notification selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Notification record);

    int updateByPrimaryKeyWithBLOBs(Notification record);

    int updateByPrimaryKey(Notification record);

    int countNotReadByAuthor(String author);

    int countByAuthor(String author);

    List<Notification> selectByAuthor(String author,Integer start,Integer limit);

    int updateUnreadToRead(String author);
}