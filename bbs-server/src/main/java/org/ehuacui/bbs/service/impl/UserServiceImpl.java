package org.ehuacui.bbs.service.impl;

import org.ehuacui.bbs.dto.PageDataBody;
import org.ehuacui.bbs.mapper.UserMapper;
import org.ehuacui.bbs.mapper.UserRoleMapper;
import org.ehuacui.bbs.model.User;
import org.ehuacui.bbs.model.UserRole;
import org.ehuacui.bbs.service.UserService;
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
public class UserServiceImpl implements UserService {

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
     * access_token查询并缓存用户信息
     */
    @Override
    public User findByAccessToken(String accessToken) {
        return userMapper.selectByAccessToken(accessToken, new Date());
    }

    /**
     * 根据昵称查询并缓存用户信息
     */
    @Override
    public User findByNickname(String nickname) {
        return userMapper.selectByNickName(nickname);
    }

    @Override
    public User findByEmail(String email) {
        return userMapper.selectByEmail(email);
    }

    @Override
    public User findByNickNameAndPassword(String nickname, String password) {
        return userMapper.selectByNickNameAndPassword(nickname, password);
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        return userMapper.selectByEmailAndPassword(email, password);
    }

    /**
     * 分页查询所有用户，倒序
     */
    @Override
    public PageDataBody<User> page(Integer pageNumber, Integer pageSize) {
        List<User> list = userMapper.selectAll((pageNumber - 1) * pageSize, pageSize);
        int total = userMapper.countAll();
        return new PageDataBody<>(list, pageNumber, pageSize, total);
    }

    /**
     * 根据昵称删除用户
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
     */
    @Override
    public List<User> findByPermissionId(Integer pid) {
        return userMapper.selectByPermissionId(pid);
    }

    /**
     * 积分榜用户
     */
    @Override
    public List<User> scores(Integer limit) {
        return userMapper.selectUserScores(limit);
    }

}