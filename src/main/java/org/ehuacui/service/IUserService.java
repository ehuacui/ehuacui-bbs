package org.ehuacui.service;


import org.ehuacui.common.Page;
import org.ehuacui.model.User;

import java.util.List;

/**
 * Created by jianwei.zhou on 2016/8/15.
 */
public interface IUserService {

    User findById(Integer id);

    void update(User user);

    void save(User user);

    /**
     * 根据Github_access_token查询用户信息
     *
     * @param thirdId
     * @return
     */
    User findByThirdId(String thirdId);

    /**
     * 更新access_token查询并缓存用户信息
     *
     * @param accessToken
     * @return
     */
    User findByAccessToken(String accessToken);

    /**
     * 根据昵称查询并缓存用户信息
     *
     * @param nickname
     * @return
     */
    User findByNickname(String nickname);

    /**
     * 分页查询所有用户，倒序
     *
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Page<User> page(Integer pageNumber, Integer pageSize);

    /**
     * 根据昵称删除用户
     *
     * @param nickname
     */
    void deleteByNickname(String nickname);

    void deleteById(Integer id);

    /**
     * 用户勾选角色关联处理
     *
     * @param userId
     * @param roles
     */
    void correlationRole(Integer userId, Integer[] roles);

    /**
     * 根据权限id查询拥有这个权限的用户列表
     *
     * @param id
     * @return
     */
    List<User> findByPermissionId(Integer id);

    /**
     * 积分榜用户
     *
     * @return
     */
    List<User> scores(Integer limit);
}
