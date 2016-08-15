package org.ehuacui.service.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import org.ehuacui.common.DaoHolder;
import org.ehuacui.module.User;
import org.ehuacui.service.IUserService;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class UserService implements IUserService {

    @Override
    public User findById(Integer id) {
        return DaoHolder.userDao.findById(id);
    }

    /**
     * 根据Github_access_token查询用户信息
     *
     * @param thirdId
     * @return
     */
    @Override
    public User findByThirdId(String thirdId) {
        return DaoHolder.userDao.findByThirdId(thirdId);
    }

    /**
     * 更新access_token查询并缓存用户信息
     *
     * @param accessToken
     * @return
     */
    @Override
    public User findByAccessToken(String accessToken) {
        return DaoHolder.userDao.findByAccessToken(accessToken);
    }

    /**
     * 根据昵称查询并缓存用户信息
     *
     * @param nickname
     * @return
     */
    @Override
    public User findByNickname(String nickname) throws UnsupportedEncodingException {
        return DaoHolder.userDao.findByNickname(nickname);
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
        return DaoHolder.userDao.page(pageNumber, pageSize);
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
        DaoHolder.userDao.deleteById(id);
    }

    /**
     * 用户勾选角色关联处理
     *
     * @param userId
     * @param roles
     */
    @Override
    public void correlationRole(Integer userId, Integer[] roles) {
        DaoHolder.userDao.correlationRole(userId, roles);
    }

    /**
     * 根据权限id查询拥有这个权限的用户列表
     *
     * @param id
     * @return
     */
    @Override
    public List<User> findByPermissionId(Integer id) {
        return DaoHolder.userDao.findByPermissionId(id);
    }

    /**
     * 积分榜用户
     *
     * @return
     */
    @Override
    public List<User> scores(Integer limit) {
        return DaoHolder.userDao.scores(limit);
    }

}