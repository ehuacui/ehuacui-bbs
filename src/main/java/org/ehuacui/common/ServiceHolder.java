package org.ehuacui.common;

import org.ehuacui.service.*;
import org.ehuacui.service.impl.*;

/**
 * 服务调用
 * Created by jianwei.zhou on 2016/8/15.
 */
public class ServiceHolder {

    public final static ICollectService collectService = SpringContextHolder.getBean(CollectService.class);

    public final static INotificationService notificationService = SpringContextHolder.getBean(NotificationService.class);

    public final static IPermissionService permissionService = SpringContextHolder.getBean(PermissionService.class);

    public final static IReplyService replyService = SpringContextHolder.getBean(ReplyService.class);

    public final static IRoleService roleService = SpringContextHolder.getBean(RoleService.class);

    public final static IRolePermissionService rolePermissionService = SpringContextHolder.getBean(RolePermissionService.class);

    public final static ISectionService sectionService = SpringContextHolder.getBean(SectionService.class);

    public final static ITopicService topicService = SpringContextHolder.getBean(TopicService.class);

    public final static ITopicAppendService topicAppendService = SpringContextHolder.getBean(TopicAppendService.class);

    public final static IUserService userService = SpringContextHolder.getBean(UserService.class);

    public final static IUserRoleService userRoleService = SpringContextHolder.getBean(UserRoleService.class);
}
