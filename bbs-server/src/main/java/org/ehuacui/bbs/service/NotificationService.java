package org.ehuacui.bbs.service;

import org.ehuacui.bbs.dto.PageDataBody;
import org.ehuacui.bbs.model.Notification;

/**
 * Created by jianwei.zhou on 2016/8/15.
 */
public interface NotificationService {
    /**
     * 查询未读通知数量
     *
     * @param author
     * @return
     */
    int findNotReadCount(String author);

    /**
     * 查询通知列表
     *
     * @param pageNumber
     * @param pageSize
     * @param author
     * @return
     */
    PageDataBody<Notification> pageByAuthor(Integer pageNumber, Integer pageSize, String author);

    /**
     * 将用户的通知都置为已读
     *
     * @param author
     */
    void makeUnreadToRead(String author);

    /**
     * 启动线程发送通知
     *
     * @param author
     * @param targetAuthor
     * @param action
     * @param tid
     * @param content
     */
    void sendNotification(String author, String targetAuthor, String action, Integer tid, String content);
}
