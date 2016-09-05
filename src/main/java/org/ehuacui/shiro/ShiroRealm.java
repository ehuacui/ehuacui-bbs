package org.ehuacui.shiro;

import com.quanshi.boss.security.ShiroUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.io.Serializable;
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
        SimpleAuthorizationInfo simpleAuthorizationInfo = null;
        ShiroUser shiroUser = ShiroUtils.getShiroUser();
        if (shiroUser != null) {
            simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            List<String> roleCodes = shiroUser.getRoleCodes();
            List<String> permissionCodes = shiroUser.getPermissionCodes();
            simpleAuthorizationInfo.addRoles(roleCodes);
            simpleAuthorizationInfo.addStringPermissions(permissionCodes);
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 登录回调
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        AuthenticationInfo authenticationInfo = null;
        ShiroUser shiroUser = ShiroUtils.getShiroUser();
        if (shiroUser != null) {
            authenticationInfo = new SimpleAuthenticationInfo(shiroUser, token.getCredentials(), getName());
        }
        return authenticationInfo;
    }

}
