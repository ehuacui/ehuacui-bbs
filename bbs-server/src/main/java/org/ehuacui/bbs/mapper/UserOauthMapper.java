package org.ehuacui.bbs.mapper;

import org.ehuacui.bbs.model.UserOauth;

public interface UserOauthMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserOauth record);

    int insertSelective(UserOauth record);

    UserOauth selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserOauth record);

    int updateByPrimaryKey(UserOauth record);
}