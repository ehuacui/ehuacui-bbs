package org.ehuacui.bbs.service.impl;

import org.ehuacui.bbs.mapper.RolePermissionMapper;
import org.ehuacui.bbs.model.RolePermission;
import org.ehuacui.bbs.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@Service
public class RolePermissionServiceImpl implements RolePermissionService {

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
