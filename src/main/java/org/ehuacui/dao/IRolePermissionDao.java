package org.ehuacui.dao;

import org.ehuacui.module.RolePermission;

import java.util.List;

/**
 * Created by jianwei.zhou on 2016/8/15.
 */
public interface IRolePermissionDao {

    List<RolePermission> findByRoleId(Integer roleId);

    void deleteByPermissionId(Integer permissionId);

    void deleteByRoleId(Integer roleId);
}
