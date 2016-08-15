package org.ehuacui.dao;

import com.jfinal.plugin.activerecord.Page;
import org.ehuacui.module.Notification;

/**
 * Created by jianwei.zhou on 2016/8/15.
 */
public interface INotificationDao {
    /**
     * 查询未读通知数量
     * @param author
     * @return
     */
    int findNotReadCount(String author);

    /**
     * 查询通知列表
     * @param pageNumber
     * @param pageSize
     * @param author
     * @return
     */
    Page<Notification> pageByAuthor(Integer pageNumber, Integer pageSize, String author);

    /**
     * 将用户的通知都置为已读
     * @param author
     */
    void makeUnreadToRead(String author);

    /**
     * 判断通知是否已读
     * @param notification
     * @return
     */
    String isRead(Notification notification);

    /**
     * 启动线程发送通知
     * @param author
     * @param targetAuthor
     * @param action
     * @param tid
     * @param content
     */
    void sendNotification(String author, String targetAuthor, String action, Integer tid, String content);
}
