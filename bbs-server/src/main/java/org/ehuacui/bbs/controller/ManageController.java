package org.ehuacui.bbs.controller;

import org.ehuacui.bbs.interceptor.BeforeAdviceController;
import org.ehuacui.bbs.interceptor.PermissionInterceptor;
import org.ehuacui.bbs.interceptor.UserInterceptor;
import org.ehuacui.bbs.model.Permission;
import org.ehuacui.bbs.model.Role;
import org.ehuacui.bbs.model.User;
import org.ehuacui.bbs.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ehuacui.
 * Copyright (c) 2016, All Rights Reserved.
 * http://www.ehuacui.org
 */
@Controller
@RequestMapping("/manage")
@BeforeAdviceController({UserInterceptor.class, PermissionInterceptor.class})
public class ManageController extends BaseController {

    @Value("${pageSize}")
    private Integer pageSize;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RolePermissionService rolePermissionService;

    /**
     * 用户列表
     */
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String users(@RequestParam(value = "p", defaultValue = "1") Integer p, HttpServletRequest request) {
        request.setAttribute("page", userService.page(p, pageSize));
        return "system/users";
    }

    /**
     * 删除用户
     */
    @RequestMapping(value = "/delete-user", method = RequestMethod.GET)
    public String deleteUser(@RequestParam("id") Integer id) {
        //删除与用户关联的角色
        userRoleService.deleteByUserId(id);
        //删除用户
        userService.deleteById(id);
        return redirect("/manage/users");
    }

    /**
     * 角色列表
     */
    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    public String roles(HttpServletRequest request) {
        request.setAttribute("roles", roleService.findAll());
        return "system/roles";
    }

    /**
     * 权限列表
     */
    @RequestMapping(value = "/permissions", method = RequestMethod.GET)
    public String permissions(@RequestParam(value = "pid", required = false, defaultValue = "0") Integer pid, HttpServletRequest request) {
        request.setAttribute("permissions", permissionService.findByPid(pid));
        request.setAttribute("childPermissions", permissionService.findByPid(pid));
        request.setAttribute("pid", pid);
        return "system/permissions";
    }

    /**
     * 处理用户与角色关联
     */
    @RequestMapping(value = "/user-role", method = RequestMethod.GET)
    public String userRole(@RequestParam("id") Integer id, HttpServletRequest request) {
        request.setAttribute("user", userService.findById(id));
        //查询所有的权限
        request.setAttribute("roles", roleService.findAll());
        //当前用户已经存在的角色
        request.setAttribute("_roles", userRoleService.findByUserId(id));
        return "system/user_role";
    }

    /**
     * 处理用户与角色关联
     */
    @RequestMapping(value = "/user-role", method = RequestMethod.POST)
    public String userRole(@RequestParam("id") Integer id, @RequestParam("roles") Integer[] roles) {
        userService.correlationRole(id, roles);
        return redirect("/manage/users");
    }

    /**
     * 禁用账户
     */
    @RequestMapping(value = "/user-block", method = RequestMethod.GET)
    public String userBlock(@RequestParam("id") Integer id) {
        User user = userService.findById(id);
        user.setIsBlock(!user.getIsBlock());
        userService.update(user);
        return redirect("/manage/users");
    }

    /**
     * 添加角色
     */
    @RequestMapping(value = "/add-role", method = RequestMethod.GET)
    public String addRole(HttpServletRequest request) {
        //查询所有的权限
        request.setAttribute("permissions", permissionService.findWithChild());
        return "system/add_role";
    }

    /**
     * 添加角色
     */
    @RequestMapping(value = "/add-role", method = RequestMethod.POST)
    public String addRole(@RequestParam("name") String name, @RequestParam("description") String description,
                          @RequestParam(value = "roles", required = false) Integer[] roles) {
        Role role = new Role();
        role.setName(name);
        role.setDescription(description);
        roleService.save(role);
        //保存关联数据
        roleService.correlationPermission(role.getId(), roles);
        return redirect("/manage/roles");
    }

    /**
     * 添加权限
     */
    @RequestMapping(value = "/add-permission", method = RequestMethod.GET)
    public String addPermission(@RequestParam("pid") Integer pid, HttpServletRequest request) {
        request.setAttribute("pid", pid);
        request.setAttribute("permissions", permissionService.findByPid(0));
        return "system/add_permission";
    }

    @RequestMapping(value = "/add-permission", method = RequestMethod.POST)
    public String addPermission(@RequestParam("pid") Integer pid, @RequestParam("name") String name,
                                @RequestParam("url") String url, @RequestParam("description") String description) {
        Permission permission = new Permission();
        permission.setName(name);
        permission.setUrl(pid == 0 ? "" : url);
        permission.setDescription(description);
        permission.setPid(pid);
        permissionService.save(permission);
        String _url = "/manage/permissions?pid=" + pid;
        if (pid == 0) {
            _url = "/manage/permissions";
        }
        return redirect(_url);
    }

    /**
     * 编辑权限
     */
    @RequestMapping(value = "/edit-permission", method = RequestMethod.GET)
    public String editPermission(@RequestParam("id") Integer id, HttpServletRequest request) {
        request.setAttribute("_permission", permissionService.findById(id));
        request.setAttribute("permissions", permissionService.findByPid(0));
        return "system/edit_permission";
    }

    @RequestMapping(value = "/edit-permission", method = RequestMethod.POST)
    public String editPermission(@RequestParam("id") Integer id, @RequestParam("pid") Integer pid,
                                 @RequestParam("name") String name, @RequestParam("url") String url,
                                 @RequestParam("description") String description) {
        Permission permission = permissionService.findById(id);
        permission.setName(name);
        permission.setUrl(url);
        permission.setDescription(description);
        permission.setPid(pid);
        permissionService.update(permission);
        return redirect("/manage/permissions?pid=" + pid);
    }

    /**
     * 处理角色与权限关系
     */
    @RequestMapping(value = "/role-permission", method = RequestMethod.GET)
    public String rolePermission(@RequestParam("id") Integer id, HttpServletRequest request) {
        Role role = roleService.findById(id);
        request.setAttribute("role", role);
        //查询所有的权限
        request.setAttribute("permissions", permissionService.findWithChild());
        //查询角色已经配置的权限
        request.setAttribute("_permissions", rolePermissionService.findByRoleId(id));
        return "system/role_permission";
    }

    @RequestMapping(value = "/role-permission", method = RequestMethod.POST)
    public String rolePermission(@RequestParam("id") Integer id, @RequestParam("name") String name,
                                 @RequestParam("description") String description,
                                 @RequestParam(value = "permissions", required = false) Integer[] permissions) {
        Role role = roleService.findById(id);
        role.setName(name);
        role.setDescription(description);
        roleService.update(role);
        roleService.correlationPermission(id, permissions);
        return redirect("/manage/roles");
    }

    /**
     * 删除角色
     */
    @RequestMapping(value = "/delete-role", method = RequestMethod.GET)
    public String deleteRole(@RequestParam("id") Integer id) {
        userRoleService.deleteByRoleId(id);
        rolePermissionService.deleteByRoleId(id);
        roleService.deleteById(id);
        return redirect("/manage/roles");
    }

    /**
     * 删除权限
     */
    @RequestMapping(value = "/delete-permission", method = RequestMethod.GET)
    public String deletePermission(@RequestParam("id") Integer id) {
        Permission permission = permissionService.findById(id);
        Integer pid = permission.getPid();
        String url = "/manage/permissions?pid=" + pid;
        //如果是父节点，就删除父节点下的所有权限
        if (pid == 0) {
            permissionService.deleteByPid(id);
            url = "/manage/permissions";
        }
        //删除与角色关联的数据
        rolePermissionService.deleteByPermissionId(id);
        permissionService.deleteById(id);
        return redirect(url);
    }
}
