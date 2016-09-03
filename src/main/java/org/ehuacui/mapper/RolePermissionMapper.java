package org.ehuacui.mapper;

import org.apache.ibatis.annotations.Param;
import org.ehuacui.model.RolePermission;

import java.util.List;

public interface RolePermissionMapper {

    List<RolePermission> selectByRoleId(@Param("rid")Integer rid);

    int insert(RolePermission record);

    int insertSelective(RolePermission record);

    int deleteByRoleId(@Param("rid")Integer rid);

    int deleteByPermissionId(@Param("pid")Integer pid);
}