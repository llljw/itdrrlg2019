package com.itdr.mappers;

import com.itdr.pojo.Users;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UsersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    //    登录
    Users selectByNameAndPassword(String username, String password);

    //    用户列表
    List<Users> selectAll();


    /*前端*/
//    根据用户名和密码查找用户
    Users selectByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    //    检查用户名/邮箱是否有效
    int selectByUserNameOrEmail(@Param("str") String str, @Param("type") String type);

    //    根据邮箱和id查询
    int selectByEmailAndId(@Param("email") String email, @Param("id") Integer id);

    //    根据用户名查找用户密码问题
    String selectByUserName(String username);

    int selectByUsernameAndQuestionAndAnswer(@Param("username") String username,
                                             @Param("question") String question,
                                             @Param("answer") String answer);

    //    根据用户名去更新密码
    int updateByUsernameAndPassword(@Param("username") String username, @Param("passwordNew") String passwordNew);

    //    登录中状态重置密码
    int selectByIdAndPassword(@Param("id") Integer id, @Param("passwordOld") String passwordOld);
}