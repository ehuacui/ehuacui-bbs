package org.ehuacui.bbs.service;

import org.ehuacui.bbs.model.Permission;

import java.util.List;
import java.util.Map;

/**
 * Created by jianwei.zhou on 2016/8/15.
 */
public interface PermissionService {

    /**
     * 根据父节点查询权限列表
     *
     * @param pid
     * @return
     */
    List<Permission> findByPid(Integer pid);

    /**
     * 查询所有权限（不包括父节点）
     *
     * @return
     */
    List<Permission> findAll();

    /**
     * 查询所有权限，结构是父节点下是子节点的权限列表
     *
     * @return
     */
    List<Permission> findWithChild();

    /**
     * 删除父节点下所有的话题
     *
     * @param pid
     */
    void deleteByPid(Integer pid);

    /**
     * 根据用户id查询所拥有的权限
     *
     * @param userId
     * @return
     */
    Map<String, String> findPermissions(Integer userId);

    Permission findById(Integer id);

    void deleteById(Integer id);

    void save(Permission permission);

    void update(Permission permission);
}
