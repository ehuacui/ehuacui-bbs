package org.ehuacui.service.impl;

import com.jfinal.plugin.activerecord.Db;
import org.ehuacui.module.RolePermission;
import org.ehuacui.service.IRolePermission;

import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class RolePermissionService implements IRolePermission {

    private RolePermission me = new RolePermission();

    @Override
    public List<RolePermission> findByRoleId(Integer roleId) {
        return me.find("select * from ehuacui_role_permission where rid = ?", roleId);
    }

    @Override
    public void deleteByPermissionId(Integer permissionId) {
        Db.update("delete from ehuacui_role_permission where pid = ?", permissionId);
    }

    @Override
    public void deleteByRoleId(Integer roleId) {
        Db.update("delete from ehuacui_role_permission where rid = ?", roleId);
    }
}
