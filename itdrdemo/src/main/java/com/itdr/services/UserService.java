package com.itdr.services;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.Users;
import org.apache.ibatis.annotations.Mapper;

/**
 * User: Jwen
 * Date: 2019/9/6
 * Time: 18:04
 */
public interface UserService {

    /*后端*/
//    登录
    ServerResponse selectOne(String username, String password);

    //    用户列表
    ServerResponse selectAll();


    /*前端*/
//    用户登录
    ServerResponse<Users> login(String username, String password);

    //    用户注册
    ServerResponse<Users> register(Users u);

    //    检查用户名/邮箱是否有效
    ServerResponse<Users> check_valid(String str, String type);

    //    登录状态更新个人信息
    ServerResponse<Users> update_information(Users users);

    //    获取当前登录用户详细信息
    ServerResponse<Users> getInforamtion(Users users);

    //    忘记密码
    ServerResponse<Users> forgetGetQuestion(String username);

    //    提交问题答案
    ServerResponse<Users> forgetCheckAnswer(String username, String question, String answer);

    //    忘记密码的重设密码
    ServerResponse<Users> forgetResetPassword(String username, String passwordNew, String forgetToken);

    //    登录中状态重置密码
    ServerResponse<Users> resetPassword(Users users, String passwordOld, String passwordNew);
}
