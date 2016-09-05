package org.ehuacui.shiro;

import com.quanshi.boss.core.domain.Role;
import com.quanshi.boss.security.ShiroUser;
import com.quanshi.boss.security.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.ArrayList;
import java.util.List;

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
     * @return
     */
    public static UserInfo getUserInfo() {
        UserInfo userInfo = null;
        Session session = getSession();
        if (session != null) {
            Object object = session.getAttribute(SESSION_KEY_USER_INFO);
            if (object != null) {
                userInfo = (UserInfo) object;
            }
        }
        return userInfo;
    }

    /**
     * 返回当前登录用户ShiroUser信息
     *
     * @return 当前登录用户
     */
    public static ShiroUser getShiroUser() {
        return (ShiroUser) getSubject().getPrincipal();
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

    /**
     * 获取用户角色id列表
     *
     * @return
     */
    public static List<Integer> getUserRoleIds() {
        List<Integer> roleIds = new ArrayList<Integer>();
        List<Role> roleList = getShiroUser().getRoles();
        for (int i = 0; i < roleList.size(); i++) {
            Role role = roleList.get(i);
            roleIds.add(role.getId());
        }
        return roleIds;
    }

}
