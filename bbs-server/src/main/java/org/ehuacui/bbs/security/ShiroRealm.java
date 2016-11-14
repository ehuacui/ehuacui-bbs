package org.ehuacui.bbs.security;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.ehuacui.bbs.config.BusinessException;
import org.ehuacui.bbs.model.Role;
import org.ehuacui.bbs.model.User;
import org.ehuacui.bbs.service.PermissionService;
import org.ehuacui.bbs.service.RoleService;
import org.ehuacui.bbs.service.UserService;
import org.ehuacui.bbs.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.*;

/**
 * ShiroRealm
 * Created by jianwei.zhou on 2016/9/30.
 */
public class ShiroRealm extends AuthorizingRealm implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Set<String> roles = new HashSet<>();//角色编码
        Set<String> permissions = new HashSet<>();//权限编码
        User user = ShiroHandle.getUmsUserFromShiro();
        if (user != null) {
            List<Role> roleList = roleService.findByUid(user.getId());
            if (roleList != null && roleList.size() > 0) {
                for (Role role : roleList) {
                    if (role != null) {
                        roles.add(role.getName());
                    }
                }
            }
            //处理权限部分
            Map<String, String> permissionMap = permissionService.findPermissions(user.getId());
            if (permissionMap != null && permissionMap.size() > 0) {
                Iterator<Map.Entry<String, String>> iterator = permissionMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> entry = iterator.next();
                    String permissionName = entry.getKey();
                    String permissionUrl = entry.getValue();
                    permissions.add(permissionName);
                    permissions.add(permissionUrl);
                }
            }

        }
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    /**
     * 登录回调
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String userName = token.getUsername();//登录名
        String password = new String(token.getPassword());//密码
        try {
            User user = new User();
            if (StringUtil.notBlank(userName)) {
                if (StringUtil.isEmail(userName)) {
                    user = userService.findByEmailAndPassword(userName, password);
                } else {
                    user = userService.findByNickNameAndPassword(userName, password);
                }
            }
            return new SimpleAuthenticationInfo(user, token.getCredentials(), getName());
        } catch (BusinessException e) {
            logger.warn("Authentication failed {}", userName);
            throw new AuthenticationException(e);
        }
    }

}
