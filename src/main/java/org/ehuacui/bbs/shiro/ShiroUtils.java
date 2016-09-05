package org.ehuacui.bbs.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * @author jianwei.zhou on 2016/5/4.
 */
public class ShiroUtils {

    /**
     * 从Session里面获取用户信息
     */
    public static final String SESSION_KEY_USER_INFO = "userInfo";

    /**
     * 返回当前操作对象（相当于用户）
     */
    private static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 返回当前会话
     *
     * @return 当前会话对象
     */
    private static Session getSession() {
        Session session = null;
        Subject subject = getSubject();
        if (subject != null) {
            session = subject.getSession(false);
        }
        return session;
    }

    /**
     * 返回当前登录用户ShiroUser信息
     *
     * @return 当前登录用户
     */
    public static Object getShiroUser() {
        return getSubject().getPrincipal();
    }

    /**
     * 检查角色
     *
     * @param roleIdentifier 角色编码
     */
    public static boolean hasRole(String roleIdentifier) {
        return ShiroUtils.getSubject().hasRole(roleIdentifier);
    }

    /**
     * 检查权限
     *
     * @param permission 权限编码
     */
    public static boolean isPermitted(String permission) {
        return ShiroUtils.getSubject().isPermitted(permission);
    }

}
