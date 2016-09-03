package org.ehuacui.service.impl;

import org.ehuacui.mapper.RolePermissionMapper;
import org.ehuacui.model.RolePermission;
import org.ehuacui.service.IRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@Service
public class RolePermissionService implements IRolePermissionService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public List<RolePermission> findByRoleId(Integer roleId) {
        return rolePermissionMapper.selectByRoleId(roleId);
    }

    @Override
    public void deleteByPermissionId(Integer permissionId) {
        rolePermissionMapper.deleteByPermissionId(permissionId);
    }

    @Override
    public void deleteByRoleId(Integer roleId) {
        rolePermissionMapper.deleteByRoleId(roleId);
    }
}
