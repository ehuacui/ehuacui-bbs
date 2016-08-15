package org.ehuacui.service.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.redis.Cache;
import com.jfinal.plugin.redis.Redis;
import org.ehuacui.common.Constants.CacheEnum;
import org.ehuacui.module.User;
import org.ehuacui.module.UserRole;
import org.ehuacui.service.IUser;
import org.ehuacui.utils.StrUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class UserService implements IUser {
    private User me = new User();

    @Override
    public User findById(Integer id) {
        return me.findById(id);
    }

    /**
     * 根据Github_access_token查询用户信息
     *
     * @param thirdId
     * @return
     */
    @Override
    public User findByThirdId(String thirdId) {
        return me.findFirst("select * from ehuacui_user where third_id = ?", thirdId);
    }

    /**
     * 更新access_token查询并缓存用户信息
     *
     * @param accessToken
     * @return
     */
    @Override
    public User findByAccessToken(String accessToken) {
        Cache cache = Redis.use();
        User user = cache.get(CacheEnum.useraccesstoken.name() + accessToken);
        if (user == null) {
            user = me.findFirst(
                    "select * from ehuacui_user where access_token = ? and expire_time > ?",
                    accessToken,
                    new Date());
            cache.set(CacheEnum.useraccesstoken.name() + accessToken, user);
        }
        return user;
    }

    /**
     * 根据昵称查询并缓存用户信息
     *
     * @param nickname
     * @return
     */
    @Override
    public User findByNickname(String nickname) throws UnsupportedEncodingException {
        if (StrUtil.isBlank(nickname)) {
            return null;
        }
        Cache cache = Redis.use();
        User user = cache.get(CacheEnum.usernickname.name() + URLEncoder.encode(nickname, "utf-8"));
        if (user == null) {
            user = me.findFirst(
                    "select * from ehuacui_user where nickname = ?",
                    URLDecoder.decode(nickname, "utf-8")
            );
            cache.set(CacheEnum.usernickname.name() + URLEncoder.encode(nickname, "utf-8"), user);
        }
        return user;
    }

    /**
     * 分页查询所有用户，倒序
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @Override
    public Page<User> page(Integer pageNumber, Integer pageSize) {
        return me.paginate(pageNumber, pageSize, "select * ", "from ehuacui_user order by in_time desc");
    }

    /**
     * 根据昵称删除用户
     *
     * @param nickname
     */
    @Override
    public void deleteByNickname(String nickname) {
        Db.update("delete from ehuacui_user where nickname = ?", nickname);
    }

    @Override
    public void deleteById(Integer id) {
        me.deleteById(id);
    }

    /**
     * 用户勾选角色关联处理
     *
     * @param userId
     * @param roles
     */
    @Override
    public void correlationRole(Integer userId, Integer[] roles) {
        //先删除已经存在的关联
        Db.update("delete from ehuacui_user_role where uid = ?", userId);
        //建立新的关联关系
        if (roles != null) {
            for (Integer rid : roles) {
                UserRole userRole = new UserRole();
                userRole.set("uid", userId)
                        .set("rid", rid)
                        .save();
            }
        }
    }

    /**
     * 根据权限id查询拥有这个权限的用户列表
     *
     * @param id
     * @return
     */
    @Override
    public List<User> findByPermissionId(Integer id) {
        return me.find("select u.* from ehuacui_user u, ehuacui_user_role ur, ehuacui_role r, ehuacui_role_permission rp, " +
                        "ehuacui_permission p where u.id = ur.uid and ur.rid = r.id and r.id = rp.rid and rp.pid = p.id and p.id = ?",
                id);
    }

    /**
     * 积分榜用户
     *
     * @return
     */
    @Override
    public List<User> scores(Integer limit) {
        return me.find(
                "select user.*, role.description from ehuacui_user user, ehuacui_role role, ehuacui_user_role ur " +
                        "where user.id = ur.uid and ur.rid = role.id order by score desc, in_time desc limit ?",
                limit
        );
    }

}