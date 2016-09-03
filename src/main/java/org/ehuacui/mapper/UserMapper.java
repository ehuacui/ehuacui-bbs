package org.ehuacui.mapper;

import org.apache.ibatis.annotations.Param;
import org.ehuacui.model.User;

import java.util.Date;
import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(@Param("id") Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(@Param("id") Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectAll(@Param("start") Integer start, @Param("limit") Integer limit);

    int countAll();

    User selectByThirdId(@Param("thirdId") String thirdId);

    User selectByNickName(@Param("nickname") String nickname);

    List<User> selectByPermissionId(@Param("pid") Integer pid);

    List<User> selectUserScores(@Param("limit") Integer limit);

    User selectByAccessToken(@Param("accessToken") String accessToken, @Param("expireTime") Date expireTime);

    int deleteByNickName(@Param("nickname") String nickname);
}