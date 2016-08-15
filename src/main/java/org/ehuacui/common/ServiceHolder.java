package org.ehuacui.common;

import org.ehuacui.service.*;
import org.ehuacui.service.impl.*;

/**
 * 服务调用
 * Created by jianwei.zhou on 2016/8/15.
 */
public class ServiceHolder {

    public final static ICollect collectService = new CollectService();

    public final static INotification notificationService = new NotificationService();

    public final static IPermission permissionService = new PermissionService();

    public final static IReply replyService = new ReplyService();

    public final static IRole roleService = new RoleService();

    public final static IRolePermission rolePermissionService = new RolePermissionService();

    public final static ISection sectionService = new SectionService();

    public final static ITopic topicService = new TopicService();

    public final static ITopicAppend topicAppendService = new TopicAppendService();

    public final static IUser userService = new UserService();

    public final static IUserRole userRoleService = new UserRoleService();
}
