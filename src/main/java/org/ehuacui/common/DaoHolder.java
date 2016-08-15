package org.ehuacui.common;


import org.ehuacui.dao.*;
import org.ehuacui.dao.impl.*;

/**
 * 数据访问层调用
 * Created by jianwei.zhou on 2016/8/15.
 */
public class DaoHolder {

    public final static ICollectDao collectDao = new CollectDao();

    public final static INotificationDao notificationDao = new NotificationDao();

    public final static IPermissionDao permissionDao = new PermissionDao();

    public final static IReplyDao replyDao = new ReplyDao();

    public final static IRoleDao roleDao = new RoleDao();

    public final static IRolePermissionDao rolePermissionDao = new RolePermissionDao();

    public final static ISectionDao sectionDao = new SectionDao();

    public final static ITopicDao topicDao = new TopicDao();

    public final static ITopicAppendDao topicAppendDao = new TopicAppendDao();

    public final static IUserDao userDao = new UserDao();

    public final static IUserRoleDao userRoleDao = new UserRoleDao();
}
