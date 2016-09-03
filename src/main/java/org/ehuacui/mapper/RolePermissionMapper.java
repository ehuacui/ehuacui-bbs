package org.ehuacui.mapper;

import org.ehuacui.model.RolePermission;

import java.util.List;

public interface RolePermissionMapper {

    List<RolePermission> selectByRoleId(Integer rid);

    int insert(RolePermission record);

    int insertSelective(RolePermission record);

    int deleteByRoleId(Integer rid);

    int deleteByPermissionId(Integer pid);
}