package org.ehuacui.service.impl;

import org.ehuacui.common.DaoHolder;
import org.ehuacui.module.UserRole;
import org.ehuacui.service.IUserRoleService;

import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class UserRoleService implements IUserRoleService {

    @Override
    public List<UserRole> findByUserId(Integer userId) {
        return DaoHolder.userRoleDao.findByUserId(userId);
    }

    @Override
    public List<UserRole> findByRoleId(Integer roleId) {
        return DaoHolder.userRoleDao.findByRoleId(roleId);
    }

    @Override
    public void deleteByUserId(Integer userId) {
        DaoHolder.userRoleDao.deleteByUserId(userId);
    }

    @Override
    public void deleteByRoleId(Integer roleId) {
        DaoHolder.userRoleDao.deleteByRoleId(roleId);
    }

}
