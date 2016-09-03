package org.ehuacui.mapper;

import org.ehuacui.model.Permission;

import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    List<Permission> selectByPid(Integer pid);

    List<Permission> selectAllChild();

    List<Permission> selectByUid(Integer uid);

    int deleteByPid(Integer pid);
}