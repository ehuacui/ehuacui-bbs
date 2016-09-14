package org.ehuacui.bbs.controller;

import com.jfinal.kit.PropKit;
import org.ehuacui.bbs.common.BaseController;
import org.ehuacui.bbs.common.Constants;
import org.ehuacui.bbs.common.Constants.CacheEnum;
import org.ehuacui.bbs.common.ServiceHolder;
import org.ehuacui.bbs.interceptor.BeforeAdviceController;
import org.ehuacui.bbs.interceptor.PermissionInterceptor;
import org.ehuacui.bbs.interceptor.UserInterceptor;
import org.ehuacui.bbs.model.Permission;
import org.ehuacui.bbs.model.Role;
import org.ehuacui.bbs.model.User;
import org.ehuacui.bbs.model.UserRole;
import org.ehuacui.bbs.route.ControllerBind;

import java.util.List;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@BeforeAdviceController({UserInterceptor.class, PermissionInterceptor.class})
@ControllerBind(controllerKey = "/manage", viewPath = "WEB-INF/ftl")
public class ManageController extends BaseController {

    /**
     * 用户列表
     */
    public void users() {
        setAttr("page", ServiceHolder.userService.page(getParaToInt("p", 1), PropKit.getInt("pageSize")));
        render("system/users.ftl");
    }

    /**
     * 删除用户
     */
    public void deleteuser() {
        Integer id = getParaToInt("id");
        //删除与用户关联的角色
        ServiceHolder.userRoleService.deleteByUserId(id);
        //删除用户
        ServiceHolder.userService.deleteById(id);
        redirect("/manage/users");
    }

    /**
     * 角色列表
     */
    public void roles() {
        setAttr("roles", ServiceHolder.roleService.findAll());
        render("system/roles.ftl");
    }

    /**
     * 权限列表
     */
    public void permissions() {
        Integer pid = getParaToInt("pid");
        if (pid == null) {
            setAttr("permissions", ServiceHolder.permissionService.findByPid(0));
            setAttr("childPermissions", ServiceHolder.permissionService.findAll());
        } else {
            setAttr("permissions", ServiceHolder.permissionService.findByPid(0));
            setAttr("childPermissions", ServiceHolder.permissionService.findByPid(pid));
            setAttr("pid", pid);
        }
        render("system/permissions.ftl");
    }

    /**
     * 处理用户与角色关联
     */
    public void userrole() {
        Integer id = getParaToInt("id");
        String method = getRequest().getMethod();
        if (method.equals("GET")) {
            setAttr("user", ServiceHolder.userService.findById(id));
            //查询所有的权限
            setAttr("roles", ServiceHolder.roleService.findAll());
            //当前用户已经存在的角色
            setAttr("_roles", ServiceHolder.userRoleService.findByUserId(id));
            render("system/userrole.ftl");
        } else if (method.equals("POST")) {
            Integer[] roles = getParaValuesToInt("roles");
            ServiceHolder.userService.correlationRole(id, roles);
            //清除缓存
            clearCache(CacheEnum.userpermissions.name() + id);
            redirect("/manage/users");
        }
    }

    /**
     * 禁用账户
     */
    public void userblock() {
        Integer id = getParaToInt("id");
        User user = ServiceHolder.userService.findById(id);
        user.setIsBlock(!user.getIsBlock());
        ServiceHolder.userService.update(user);
        clearCache(CacheEnum.usernickname.name() + user.getNickname());
        clearCache(CacheEnum.useraccesstoken.name() + user.getAccessToken());
        redirect("/manage/users");
    }

    /**
     * 添加角色
     */
    public void addrole() {
        String method = getRequest().getMethod();
        if (method.equals("GET")) {
            //查询所有的权限
            setAttr("permissions", ServiceHolder.permissionService.findWithChild());
            render("system/addrole.ftl");
        } else if (method.equals("POST")) {
            String name = getPara("name");
            String description = getPara("description");
            Role role = new Role();
            role.setName(name);
            role.setDescription(description);
            ServiceHolder.roleService.save(role);
            //保存关联数据
            Integer[] roles = getParaValuesToInt("roles");
            ServiceHolder.roleService.correlationPermission(role.getId(), roles);
            redirect("/manage/roles");
        }
    }

    /**
     * 添加权限
     */
    public void addpermission() {
        Integer pid = getParaToInt("pid");
        String method = getRequest().getMethod();
        if (method.equals("GET")) {
            setAttr("pid", pid);
            setAttr("permissions", ServiceHolder.permissionService.findByPid(0));
            render("system/addpermission.ftl");
        } else if (method.equals("POST")) {
            String name = getPara("name");
            String url = getPara("url");
            String description = getPara("description");
            Permission permission = new Permission();
            permission.setName(name);
            permission.setUrl(pid == 0 ? "" : url);
            permission.setDescription(description);
            permission.setPid(pid);
            ServiceHolder.permissionService.save(permission);
            String _url = "/manage/permissions?pid=" + pid;
            if (pid == 0) {
                _url = "/manage/permissions";
            }
            redirect(_url);
        }
    }

    /**
     * 编辑权限
     */
    public void editpermission() {
        Integer id = getParaToInt("id");
        String method = getRequest().getMethod();
        if (method.equals("GET")) {
            setAttr("_permission", ServiceHolder.permissionService.findById(id));
            setAttr("permissions", ServiceHolder.permissionService.findByPid(0));
            render("system/editpermission.ftl");
        } else if (method.equals("POST")) {
            Integer pid = getParaToInt("pid");
            String name = getPara("name");
            String url = getPara("url");
            String description = getPara("description");
            Permission permission = ServiceHolder.permissionService.findById(id);
            permission.setName(name);
            permission.setUrl(url);
            permission.setDescription(description);
            permission.setPid(pid);
            ServiceHolder.permissionService.update(permission);
            //清除缓存
            List<User> userpermissions = ServiceHolder.userService.findByPermissionId(id);
            for (User u : userpermissions) {
                clearCache(CacheEnum.userpermissions.name() + u.getId());
            }
            redirect("/manage/permissions?pid=" + pid);
        }
    }

    /**
     * 处理角色与权限关系
     */
    public void rolepermission() {
        Integer roleId = getParaToInt("id");
        String method = getRequest().getMethod();
        Role role = ServiceHolder.roleService.findById(roleId);
        if (method.equals("GET")) {
            setAttr("role", role);
            //查询所有的权限
            setAttr("permissions", ServiceHolder.permissionService.findWithChild());
            //查询角色已经配置的权限
            setAttr("_permissions", ServiceHolder.rolePermissionService.findByRoleId(roleId));
            render("system/rolepermission.ftl");
        } else if (method.equals("POST")) {
            String name = getPara("name");
            String description = getPara("description");
            role.setName(name);
            role.setDescription(description);
            ServiceHolder.roleService.update(role);
            Integer[] permissions = getParaValuesToInt("permissions");
            ServiceHolder.roleService.correlationPermission(roleId, permissions);
            //清除缓存
            List<UserRole> userRoles = ServiceHolder.userRoleService.findByRoleId(roleId);
            for (UserRole ur : userRoles) {
                clearCache(CacheEnum.userpermissions.name() + ur.getUid());
            }
            redirect("/manage/roles");
        }
    }

    /**
     * 删除角色
     */
    public void deleterole() {
        Integer id = getParaToInt("id");
        if (id == null) {
            renderText(Constants.OP_ERROR_MESSAGE);
        } else {
            ServiceHolder.userRoleService.deleteByRoleId(id);
            ServiceHolder.rolePermissionService.deleteByRoleId(id);
            ServiceHolder.roleService.deleteById(id);
            //清除缓存
            List<UserRole> userRoles = ServiceHolder.userRoleService.findByRoleId(id);
            for (UserRole ur : userRoles) {
                clearCache(CacheEnum.userpermissions.name() + ur.getUid());
            }
            redirect("/manage/roles");
        }
    }

    /**
     * 删除权限
     */
    public void deletepermission() {
        Integer id = getParaToInt("id");
        if (id == null) {
            renderText(Constants.OP_ERROR_MESSAGE);
        } else {
            Permission permission = ServiceHolder.permissionService.findById(id);
            Integer pid = permission.getPid();
            String url = "/manage/permissions?pid=" + pid;
            //如果是父节点，就删除父节点下的所有权限
            if (pid == 0) {
                ServiceHolder.permissionService.deleteByPid(id);
                url = "/manage/permissions";
            }
            //删除与角色关联的数据
            ServiceHolder.rolePermissionService.deleteByPermissionId(id);
            ServiceHolder.permissionService.deleteById(id);
            //清除缓存
            List<User> userpermissions = ServiceHolder.userService.findByPermissionId(id);
            for (User u : userpermissions) {
                clearCache(CacheEnum.userpermissions.name() + u.getId());
            }
            redirect(url);
        }
    }
}
