package org.ehuacui.service.impl;

import org.ehuacui.common.Page;
import org.ehuacui.mapper.UserMapper;
import org.ehuacui.mapper.UserRoleMapper;
import org.ehuacui.model.User;
import org.ehuacui.model.UserRole;
import org.ehuacui.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@Service
public class UserService implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public User findById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    public void save(User user) {
        userMapper.insert(user);
    }

    /**
     * 根据Github_access_token查询用户信息
     *
     * @param thirdId
     * @return
     */
    @Override
    public User findByThirdId(String thirdId) {
        return userMapper.selectByThirdId(thirdId);
    }

    /**
     * 更新access_token查询并缓存用户信息
     *
     * @param accessToken
     * @return
     */
    @Override
    public User findByAccessToken(String accessToken) {
        return userMapper.selectByAccessToken(accessToken, new Date());
    }

    /**
     * 根据昵称查询并缓存用户信息
     *
     * @param nickname
     * @return
     */
    @Override
    public User findByNickname(String nickname) {
        return userMapper.selectByNickName(nickname);
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
        List<User> list = userMapper.selectAll((pageNumber - 1) * pageSize, pageSize);
        int total = userMapper.countAll();
        return new Page<>(list, pageNumber, pageSize, total);
    }

    /**
     * 根据昵称删除用户
     *
     * @param nickname
     */
    @Override
    public void deleteByNickname(String nickname) {
        userMapper.deleteByNickName(nickname);
    }

    @Override
    public void deleteById(Integer id) {
        userMapper.deleteByPrimaryKey(id);
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
        userRoleMapper.deleteByUserId(userId);
        //建立新的关联关系
        if (roles != null) {
            for (Integer rid : roles) {
                UserRole userRole = new UserRole();
                userRole.setUid(userId);
                userRole.setRid(rid);
                userRoleMapper.insert(userRole);
            }
        }
    }

    /**
     * 根据权限id查询拥有这个权限的用户列表
     *
     * @param pid
     * @return
     */
    @Override
    public List<User> findByPermissionId(Integer pid) {
        return userMapper.selectByPermissionId(pid);
    }

    /**
     * 积分榜用户
     *
     * @return
     */
    @Override
    public List<User> scores(Integer limit) {
        return userMapper.selectUserScores(limit);
    }

}