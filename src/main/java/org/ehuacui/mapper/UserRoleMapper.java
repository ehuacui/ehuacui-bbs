package org.ehuacui.mapper;

import org.ehuacui.model.UserRole;

import java.util.List;

public interface UserRoleMapper {
    int insert(UserRole record);

    int insertSelective(UserRole record);

    List<UserRole> selectByUserId(Integer uid);

    List<UserRole> selectByRoleId(Integer rid);

    int deleteByUserId(Integer uid);

    int deleteByRoleId(Integer rid);
}