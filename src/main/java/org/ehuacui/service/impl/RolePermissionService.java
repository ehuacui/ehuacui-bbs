package org.ehuacui.service.impl;

import org.ehuacui.common.DaoHolder;
import org.ehuacui.module.RolePermission;
import org.ehuacui.service.IRolePermissionService;

import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class RolePermissionService implements IRolePermissionService {

    @Override
    public List<RolePermission> findByRoleId(Integer roleId) {
        return DaoHolder.rolePermissionDao.findByRoleId(roleId);
    }

    @Override
    public void deleteByPermissionId(Integer permissionId) {
        DaoHolder.rolePermissionDao.deleteByPermissionId(permissionId);
    }

    @Override
    public void deleteByRoleId(Integer roleId) {
        DaoHolder.rolePermissionDao.deleteByRoleId(roleId);
    }
}
