package org.ehuacui.dao;

import com.jfinal.plugin.activerecord.Page;
import org.ehuacui.module.Role;

import java.util.List;

/**
 * Created by jianwei.zhou on 2016/8/15.
 */
public interface IRoleDao {

    Page<Role> page(Integer pageNumber, Integer pageSize);

    /**
     * 根据角色名称查询
     * @param name
     * @return
     */
    Role findByName(String name);

    List<Role> findAll();

    void correlationPermission(Integer roleId, Integer[] permissionIds);

    void deleteById(Integer id);

    Role findById(Integer id);
}
