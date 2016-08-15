package org.ehuacui.service.impl;

import org.ehuacui.common.DaoHolder;
import org.ehuacui.module.Permission;
import org.ehuacui.service.IPermissionService;

import java.util.List;
import java.util.Map;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class PermissionService implements IPermissionService {

    /**
     * 根据父节点查询权限列表
     *
     * @param pid
     * @return
     */
    @Override
    public List<Permission> findByPid(Integer pid) {
        return DaoHolder.permissionDao.findByPid(pid);
    }

    /**
     * 查询所有权限（不包括父节点）
     *
     * @return
     */
    @Override
    public List<Permission> findAll() {
        return DaoHolder.permissionDao.findAll();
    }

    /**
     * 查询所有权限，结构是父节点下是子节点的权限列表
     *
     * @return
     */
    @Override
    public List<Permission> findWithChild() {
        return DaoHolder.permissionDao.findWithChild();
    }

    /**
     * 删除父节点下所有的话题
     *
     * @param pid
     */
    @Override
    public void deleteByPid(Integer pid) {
        DaoHolder.permissionDao.deleteByPid(pid);
    }

    /**
     * 根据用户id查询所拥有的权限
     *
     * @param userId
     * @return
     */
    @Override
    public Map<String, String> findPermissions(Integer userId) {
        return DaoHolder.permissionDao.findPermissions(userId);
    }

    @Override
    public Permission findById(Integer id) {
        return DaoHolder.permissionDao.findById(id);
    }

    @Override
    public void deleteById(Integer id) {
        DaoHolder.permissionDao.deleteById(id);
    }
}
