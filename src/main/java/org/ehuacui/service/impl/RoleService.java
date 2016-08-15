package org.ehuacui.service.impl;

import com.jfinal.plugin.activerecord.Page;
import org.ehuacui.common.DaoHolder;
import org.ehuacui.module.Role;
import org.ehuacui.service.IRoleService;

import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class RoleService implements IRoleService {

    @Override
    public Page<Role> page(Integer pageNumber, Integer pageSize) {
        return DaoHolder.roleDao.page(pageNumber, pageSize);
    }

    /**
     * 根据角色名称查询
     *
     * @param name
     * @return
     */
    @Override
    public Role findByName(String name) {
        return DaoHolder.roleDao.findByName(name);
    }

    @Override
    public List<Role> findAll() {
        return DaoHolder.roleDao.findAll();
    }

    @Override
    public void correlationPermission(Integer roleId, Integer[] permissionIds) {
        DaoHolder.roleDao.correlationPermission(roleId, permissionIds);
    }

    @Override
    public void deleteById(Integer id) {
        DaoHolder.roleDao.deleteById(id);
    }

    @Override
    public Role findById(Integer id) {
        return DaoHolder.roleDao.findById(id);
    }
}
