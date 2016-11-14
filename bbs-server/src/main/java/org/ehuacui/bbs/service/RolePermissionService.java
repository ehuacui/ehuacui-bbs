package org.ehuacui.bbs.service;

import org.ehuacui.bbs.model.RolePermission;

import java.util.List;

/**
 * Created by jianwei.zhou on 2016/8/15.
 */
public interface RolePermissionService {

    List<RolePermission> findByRoleId(Integer roleId);

    void deleteByPermissionId(Integer permissionId);

    void deleteByRoleId(Integer roleId);
}
