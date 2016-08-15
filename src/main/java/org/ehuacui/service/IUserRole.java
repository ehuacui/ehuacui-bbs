package org.ehuacui.service;

import org.ehuacui.module.UserRole;
import org.ehuacui.service.impl.UserRoleService;

import java.util.List;

/**
 * Created by jianwei.zhou on 2016/8/15.
 */
public interface IUserRole {

    List<UserRole> findByUserId(Integer userId);

    List<UserRole> findByRoleId(Integer roleId);

    void deleteByUserId(Integer userId);

    void deleteByRoleId(Integer roleId);
}
