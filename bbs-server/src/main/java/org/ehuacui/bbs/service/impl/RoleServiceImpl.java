package org.ehuacui.bbs.service.impl;

import org.ehuacui.bbs.dto.PageDataBody;
import org.ehuacui.bbs.mapper.RoleMapper;
import org.ehuacui.bbs.mapper.RolePermissionMapper;
import org.ehuacui.bbs.model.Role;
import org.ehuacui.bbs.model.RolePermission;
import org.ehuacui.bbs.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public PageDataBody<Role> page(Integer pageNumber, Integer pageSize) {
        int total = roleMapper.countAll();
        List<Role> list = roleMapper.selectAll((pageNumber - 1) * pageSize, pageSize);
        return new PageDataBody<>(list, pageNumber, pageSize, total);
    }

    /**
     * 根据角色名称查询
     *
     * @param name
     * @return
     */
    @Override
    public Role findByName(String name) {
        return roleMapper.selectByName(name);
    }

    @Override
    public List<Role> findAll() {
        return roleMapper.selectAll();
    }

    @Override
    public List<Role> findByUid(Integer uid) {
        return roleMapper.selectByUid(uid);
    }

    @Override
    public void correlationPermission(Integer roleId, Integer[] permissionIds) {
        //先删除已经存在的关联
        rolePermissionMapper.deleteByRoleId(roleId);
        //建立新的关联关系
        if (permissionIds != null) {
            for (Integer pid : permissionIds) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setPid(pid);
                rolePermission.setRid(roleId);
                rolePermissionMapper.insert(rolePermission);
            }
        }
    }

    @Override
    public void deleteById(Integer id) {
        roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Role findById(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public void save(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public void update(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }
}
