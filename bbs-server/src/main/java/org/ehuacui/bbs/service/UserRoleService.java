package org.ehuacui.bbs.service;

import org.ehuacui.bbs.model.UserRole;

import java.util.List;

/**
 * Created by jianwei.zhou on 2016/8/15.
 */
public interface UserRoleService {

    List<UserRole> findByUserId(Integer userId);

    List<UserRole> findByRoleId(Integer roleId);

    void deleteByUserId(Integer userId);

    void deleteByRoleId(Integer roleId);

    void save(UserRole userRole);

}
