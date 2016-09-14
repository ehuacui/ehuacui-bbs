package org.ehuacui.bbs.mapper;

import org.apache.ibatis.annotations.Param;
import org.ehuacui.bbs.model.UserRole;

import java.util.List;

public interface UserRoleMapper {
    int insert(UserRole record);

    int insertSelective(UserRole record);

    List<UserRole> selectByUserId(@Param("uid") Integer uid);

    List<UserRole> selectByRoleId(@Param("rid") Integer rid);

    int deleteByUserId(@Param("uid") Integer uid);

    int deleteByRoleId(@Param("rid") Integer rid);
}