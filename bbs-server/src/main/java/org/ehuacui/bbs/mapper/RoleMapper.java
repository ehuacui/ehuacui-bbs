package org.ehuacui.bbs.mapper;

import org.apache.ibatis.annotations.Param;
import org.ehuacui.bbs.model.Role;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(@Param("id") Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(@Param("id") Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectAll(@Param("start") Integer start, @Param("limit") Integer limit);

    List<Role> selectAll();

    List<Role> selectByUid(@Param("uid") Integer uid);

    int countAll();

    Role selectByName(@Param("name") String name);
}