package org.ehuacui.service.impl;

import com.jfinal.plugin.activerecord.Page;
import org.ehuacui.common.DaoHolder;
import org.ehuacui.module.Notification;
import org.ehuacui.service.INotificationService;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class NotificationService implements INotificationService {

    private static final Notification me = new Notification();

    /**
     * 查询未读通知数量
     *
     * @param author
     * @return
     */
    @Override
    public int findNotReadCount(String author) {
        return DaoHolder.notificationDao.findNotReadCount(author);
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
        return DaoHolder.notificationDao.pageByAuthor(pageNumber, pageSize, author);
    }

    /**
     * 将用户的通知都置为已读
     *
     * @param author
     */
    @Override
    public void makeUnreadToRead(String author) {
        DaoHolder.notificationDao.makeUnreadToRead(author);
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
        DaoHolder.notificationDao.sendNotification(author, targetAuthor, action, tid, content);
    }
}
