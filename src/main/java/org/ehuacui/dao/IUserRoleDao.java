package org.ehuacui.dao;

import org.ehuacui.module.UserRole;

import java.util.List;

/**
 * Created by jianwei.zhou on 2016/8/15.
 */
public interface IUserRoleDao {

    List<UserRole> findByUserId(Integer userId);

    List<UserRole> findByRoleId(Integer roleId);

    void deleteByUserId(Integer userId);

    void deleteByRoleId(Integer roleId);
}
