package org.ehuacui.dao.impl;

import com.jfinal.plugin.activerecord.Db;
import org.ehuacui.dao.IUserRoleDao;
import org.ehuacui.module.UserRole;

import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class UserRoleDao implements IUserRoleDao {

    private UserRole me = new UserRole();

    @Override
    public List<UserRole> findByUserId(Integer userId) {
        return me.find("select * from ehuacui_user_role where uid = ?", userId);
    }

    @Override
    public List<UserRole> findByRoleId(Integer roleId) {
        return me.find("select * from ehuacui_user_role where rid = ?", roleId);
    }

    @Override
    public void deleteByUserId(Integer userId) {
        Db.update("delete from ehuacui_user_role where uid = ?", userId);
    }

    @Override
    public void deleteByRoleId(Integer roleId) {
        Db.update("delete from ehuacui_user_role where rid = ?", roleId);
    }

}
