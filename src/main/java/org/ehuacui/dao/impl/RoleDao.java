package org.ehuacui.dao.impl;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import org.ehuacui.dao.IRoleDao;
import org.ehuacui.module.Role;
import org.ehuacui.module.RolePermission;

import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
public class RoleDao implements IRoleDao {

    private Role me = new Role();

    @Override
    public Page<Role> page(Integer pageNumber, Integer pageSize) {
        return me.paginate(pageNumber, pageSize, "select * ", "from ehuacui_role");
    }

    /**
     * 根据角色名称查询
     *
     * @param name
     * @return
     */
    @Override
    public Role findByName(String name) {
        return me.findFirst(
                "select * from ehuacui_role where name = ?",
                name
        );
    }

    @Override
    public List<Role> findAll() {
        return me.find("select * from ehuacui_role");
    }

    @Override
    public void correlationPermission(Integer roleId, Integer[] permissionIds) {
        //先删除已经存在的关联
        Db.update("delete from ehuacui_role_permission where rid = ?", roleId);
        //建立新的关联关系
        if (permissionIds != null) {
            for (Integer pid : permissionIds) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.set("rid", roleId)
                        .set("pid", pid)
                        .save();
            }
        }
    }

    @Override
    public void deleteById(Integer id) {
        me.deleteById(id);
    }

    @Override
    public Role findById(Integer id) {
        return me.findById(id);
    }
}
