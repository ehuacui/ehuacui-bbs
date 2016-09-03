package org.ehuacui.service.impl;

import org.ehuacui.mapper.UserRoleMapper;
import org.ehuacui.model.UserRole;
import org.ehuacui.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@Service
public class UserRoleService implements IUserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<UserRole> findByUserId(Integer userId) {
        return userRoleMapper.selectByUserId(userId);
    }

    @Override
    public List<UserRole> findByRoleId(Integer roleId) {
        return userRoleMapper.selectByRoleId(roleId);
    }

    @Override
    public void deleteByUserId(Integer userId) {
        userRoleMapper.deleteByUserId(userId);
    }

    @Override
    public void deleteByRoleId(Integer roleId) {
        userRoleMapper.deleteByRoleId(roleId);
    }

    @Override
    public void save(UserRole userRole) {
        userRoleMapper.insert(userRole);
    }

}
