package org.ehuacui.bbs.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.ehuacui.bbs.config.BusinessException;
import org.ehuacui.bbs.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ShiroHandle
 * Created by jianwei.zhou on 2016/9/30.
 */
public final class ShiroHandle {

    private final static Logger logger = LoggerFactory.getLogger(ShiroHandle.class);

    /**
     * 返回当前操作对象（相当于用户）
     */
    private static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static void login(String username, String password) throws BusinessException {
        String message;
        try {
            Subject currentUser = getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            currentUser.login(token);
        } catch (UnknownAccountException ex) {
            message = "用户不存在,请重新输入";
            logger.warn("未知用户!用户名:" + username);
            throw new BusinessException(message, ex);
        } catch (IncorrectCredentialsException ex) {
            message = "密码不正确,请重新输入";
            logger.warn("密码不匹配!用户名:" + username);
            throw new BusinessException(message, ex);
        } catch (LockedAccountException ex) {
            message = "帐号被锁定!请联系管理员";
            logger.warn("帐号被锁定!用户名:" + username);
            throw new BusinessException(message, ex);
        } catch (AuthenticationException ex) {
            message = "登录失败,请重新输入";
            logger.warn("认证失败!用户名:" + username);
            throw new BusinessException(message, ex);
        } catch (BusinessException ex) {
            message = "登录信息异常,请重新输入";
            logger.warn("登录信息异常!用户名:" + username);
            throw new BusinessException(message, ex);
        }
    }

    /**
     * 返回当前会话
     *
     * @return 当前会话对象
     */
    public static Session getSession() {
        Session session = null;
        Subject subject = getSubject();
        if (subject != null) {
            session = subject.getSession();
        }
        return session;
    }

    /**
     * 返回当前登录用户UmsUserDTO信息
     *
     * @return 当前登录用户
     */
    public static User getUmsUserFromShiro() {
        return (User) getSubject().getPrincipal();
    }

    /**
     * 检查角色
     *
     * @param roleIdentifier 角色编码
     */
    public static boolean hasRole(String roleIdentifier) {
        return getSubject().hasRole(roleIdentifier);
    }

    /**
     * 检查权限
     *
     * @param permission 权限编码
     */
    public static boolean isPermitted(String permission) {
        return getSubject().isPermitted(permission);
    }

}
