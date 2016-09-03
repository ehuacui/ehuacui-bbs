package org.ehuacui.mapper;

import org.ehuacui.model.User;

import java.util.Date;
import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectAll(Integer start, Integer limit);

    int countAll();

    User selectByThirdId(String thirdId);

    User selectByNickName(String nickname);

    List<User> selectByPermissionId(Integer pid);

    List<User> selectUserScores(Integer limit);

    User selectByAccessToken(String accessToken, Date expireTime);

    int deleteByNickName(String nickname);
}