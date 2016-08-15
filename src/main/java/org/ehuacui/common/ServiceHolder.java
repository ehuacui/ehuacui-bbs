package org.ehuacui.common;

import org.ehuacui.service.*;
import org.ehuacui.service.impl.*;

/**
 * 服务调用
 * Created by jianwei.zhou on 2016/8/15.
 */
public class ServiceHolder {

    public final static ICollectService collectService = new CollectService();

    public final static INotificationService notificationService = new NotificationService();

    public final static IPermissionService permissionService = new PermissionService();

    public final static IReplyService replyService = new ReplyService();

    public final static IRoleService roleService = new RoleService();

    public final static IRolePermissionService rolePermissionService = new RolePermissionService();

    public final static ISectionService sectionService = new SectionService();

    public final static ITopicService topicService = new TopicService();

    public final static ITopicAppendService topicAppendService = new TopicAppendService();

    public final static IUserService userService = new UserService();

    public final static IUserRoleService userRoleService = new UserRoleService();
}
