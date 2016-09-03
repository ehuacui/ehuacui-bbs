package org.ehuacui.service;

import org.ehuacui.model.UserRole;

import java.util.List;

/**
 * Created by jianwei.zhou on 2016/8/15.
 */
public interface IUserRoleService {

    List<UserRole> findByUserId(Integer userId);

    List<UserRole> findByRoleId(Integer roleId);

    void deleteByUserId(Integer userId);

    void deleteByRoleId(Integer roleId);
}
