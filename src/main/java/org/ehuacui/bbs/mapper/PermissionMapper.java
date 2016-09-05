package org.ehuacui.bbs.mapper;

import org.apache.ibatis.annotations.Param;
import org.ehuacui.bbs.model.Permission;

import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(@Param("id") Integer id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(@Param("id") Integer id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    List<Permission> selectByPid(@Param("pid") Integer pid);

    List<Permission> selectAllChild();

    List<Permission> selectByUid(@Param("uid") Integer uid);

    int deleteByPid(@Param("pid") Integer pid);
}