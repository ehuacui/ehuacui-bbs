package org.ehuacui.mapper;

import org.ehuacui.model.Role;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectAll(Integer start, Integer limit);

    List<Role> selectAll();

    int countAll();

    Role selectByName(String name);
}