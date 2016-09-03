package org.ehuacui.common;

import org.ehuacui.service.*;

/**
 * 服务调用
 * Created by jianwei.zhou on 2016/8/15.
 */
public class ServiceHolder {

    public final static ICollectService collectService = SpringContextHolder.getBean(ICollectService.class);

    public final static INotificationService notificationService = SpringContextHolder.getBean(INotificationService.class);

    public final static IPermissionService permissionService = SpringContextHolder.getBean(IPermissionService.class);

    public final static IReplyService replyService = SpringContextHolder.getBean(IReplyService.class);

    public final static IRoleService roleService = SpringContextHolder.getBean(IRoleService.class);

    public final static IRolePermissionService rolePermissionService = SpringContextHolder.getBean(IRolePermissionService.class);

    public final static ISectionService sectionService = SpringContextHolder.getBean(ISectionService.class);

    public final static ITopicService topicService = SpringContextHolder.getBean(ITopicService.class);

    public final static ITopicAppendService topicAppendService = SpringContextHolder.getBean(ITopicAppendService.class);

    public final static IUserService userService = SpringContextHolder.getBean(IUserService.class);

    public final static IUserRoleService userRoleService = SpringContextHolder.getBean(IUserRoleService.class);
}
