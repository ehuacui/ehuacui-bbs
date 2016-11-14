package org.ehuacui.bbs.service.impl;

import org.ehuacui.bbs.mapper.PermissionMapper;
import org.ehuacui.bbs.model.Permission;
import org.ehuacui.bbs.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 根据父节点查询权限列表
     *
     * @param pid
     * @return
     */
    @Override
    public List<Permission> findByPid(Integer pid) {
        return permissionMapper.selectByPid(pid);
    }

    /**
     * 查询所有权限（不包括父节点）
     *
     * @return
     */
    @Override
    public List<Permission> findAll() {
        return permissionMapper.selectAllChild();
    }

    /**
     * 查询所有权限，结构是父节点下是子节点的权限列表
     *
     * @return
     */
    @Override
    public List<Permission> findWithChild() {
        List<Permission> permissions = permissionMapper.selectByPid(0);
        for (Permission p : permissions) {
            p.setChildPermissions(permissionMapper.selectByPid(p.getId()));
        }
        return permissions;
    }

    /**
     * 删除父节点下所有的话题
     *
     * @param pid
     */
    @Override
    public void deleteByPid(Integer pid) {
        permissionMapper.deleteByPid(pid);
    }

    /**
     * 根据用户id查询所拥有的权限
     *
     * @param userId
     * @return
     */
    @Override
    public Map<String, String> findPermissions(Integer userId) {
        Map<String, String> map = new HashMap<>();
        if (userId == null) return map;
        List<Permission> permissions = permissionMapper.selectByUid(userId);
        for (Permission p : permissions) {
            map.put(p.getName(), p.getUrl());
        }
        return map;
    }

    @Override
    public Permission findById(Integer id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public void deleteById(Integer id) {
        permissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void save(Permission permission) {
        permissionMapper.insert(permission);
    }

    @Override
    public void update(Permission permission) {
        permissionMapper.updateByPrimaryKey(permission);
    }
}
