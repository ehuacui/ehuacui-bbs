package org.ehuacui.bbs.service;


import org.ehuacui.bbs.dto.PageDataBody;
import org.ehuacui.bbs.model.User;

import java.util.List;

/**
 * UserService
 * Created by jianwei.zhou on 2016/8/15.
 */
public interface UserService {

    User findById(Integer id);

    void update(User user);

    void save(User user);

    /**
     * access_token查询并缓存用户信息
     */
    User findByAccessToken(String accessToken);

    /**
     * 根据昵称查询并缓存用户信息
     */
    User findByNickname(String nickname);

    /**
     * 根据Email查询用户信息
     */
    User findByEmail(String email);

    User findByNickNameAndPassword(String nickname, String password);

    User findByEmailAndPassword(String email, String password);

    /**
     * 分页查询所有用户，倒序
     */
    PageDataBody<User> page(Integer pageNumber, Integer pageSize);

    /**
     * 根据昵称删除用户
     */
    void deleteByNickname(String nickname);

    void deleteById(Integer id);

    /**
     * 用户勾选角色关联处理
     */
    void correlationRole(Integer userId, Integer[] roles);

    /**
     * 根据权限id查询拥有这个权限的用户列表
     */
    List<User> findByPermissionId(Integer id);

    /**
     * 积分榜用户
     */
    List<User> scores(Integer limit);
}
