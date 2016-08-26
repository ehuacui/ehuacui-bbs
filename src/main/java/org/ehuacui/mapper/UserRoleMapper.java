package org.ehuacui.mapper;

import org.ehuacui.model.UserRole;

public interface UserRoleMapper {
    int insert(UserRole record);

    int insertSelective(UserRole record);
}