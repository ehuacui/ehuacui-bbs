package org.ehuacui.bbs.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author jianwei.zhou on 2016/5/3.
 */
public class ShiroRealm extends AuthorizingRealm implements Serializable {

    /**
     * 登陆后的授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        List<String> roleCodes = new LinkedList<>();
        List<String> permissionCodes = new LinkedList<>();
        simpleAuthorizationInfo.addRoles(roleCodes);
        simpleAuthorizationInfo.addStringPermissions(permissionCodes);
        return simpleAuthorizationInfo;
    }

    /**
     * 登录回调
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        return new SimpleAuthenticationInfo(new Object(), token.getCredentials(), getName());
    }
}
