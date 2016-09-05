package org.ehuacui.bbs.service.impl;

import org.ehuacui.bbs.common.Page;
import org.ehuacui.bbs.mapper.NotificationMapper;
import org.ehuacui.bbs.model.Notification;
import org.ehuacui.bbs.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@Service
public class NotificationService implements INotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    /**
     * 查询未读通知数量
     *
     * @param author
     * @return
     */
    @Override
    public int findNotReadCount(String author) {
        return notificationMapper.countNotReadByAuthor(author);
    }

    /**
     * 查询通知列表
     *
     * @param pageNumber
     * @param pageSize
     * @param author
     * @return
     */
    @Override
    public Page<Notification> pageByAuthor(Integer pageNumber, Integer pageSize, String author) {
        List<Notification> list = notificationMapper.selectByAuthor(author, (pageNumber - 1) * pageSize, pageSize);
        long total = notificationMapper.countByAuthor(author);
        return new Page<>(list, pageNumber, pageSize, total);
    }

    /**
     * 将用户的通知都置为已读
     *
     * @param author
     */
    @Override
    public void makeUnreadToRead(String author) {
        notificationMapper.updateUnreadToRead(author);
    }

    /**
     * 启动线程发送通知
     *
     * @param author
     * @param targetAuthor
     * @param action
     * @param tid
     * @param content
     */
    @Override
    public void sendNotification(String author, String targetAuthor, String action, Integer tid, String content) {
        Notification notification = new Notification();
        notification.setAuthor(author);
        notification.setTargetAuthor(targetAuthor);
        notification.setAction(action);
        notification.setTid(tid);
        notification.setContent(content);
        notificationMapper.insert(notification);
    }
}
