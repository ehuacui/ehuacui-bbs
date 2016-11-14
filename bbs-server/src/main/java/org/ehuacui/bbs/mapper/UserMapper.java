package org.ehuacui.bbs.mapper;

import org.apache.ibatis.annotations.Param;
import org.ehuacui.bbs.model.User;

import java.util.Date;
import java.util.List;

public interface UserMapper {

    int deleteByPrimaryKey(@Param("id") Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(@Param("id") Integer id);

    void updateUserPwd(@Param("id") Integer id, @Param("password") String password);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectAll(@Param("start") Integer start, @Param("limit") Integer limit);

    int countAll();

    User selectByNickName(@Param("nickname") String nickname);

    User selectByEmail(@Param("email") String email);

    User selectByNickNameAndPassword(@Param("nickname") String nickname, @Param("password") String password);

    User selectByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    List<User> selectByPermissionId(@Param("pid") Integer pid);

    List<User> selectUserScores(@Param("limit") Integer limit);

    User selectByAccessToken(@Param("accessToken") String accessToken, @Param("expireTime") Date expireTime);

    int deleteByNickName(@Param("nickname") String nickname);
}