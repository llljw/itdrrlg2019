package com.itdr.controller.portal;

import com.itdr.common.Const;
import com.itdr.common.ServerResponse;
import com.itdr.pojo.Users;
import com.itdr.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * User: Jwen
 * Date: 2019/9/10
 * Time: 15:57
 */

@Controller
@ResponseBody
@RequestMapping("/user/")

public class UserController {

    @Autowired
    UserService userService;

    //    用户登录
    @PostMapping("login.do")
    public ServerResponse<Users> login(String username, String password, HttpSession session) {
        ServerResponse<Users> sr = userService.login(username, password);
        //当返回的是成功状态才执行
        if (sr.isSuccess()) {
            Users users = sr.getData();
            session.setAttribute(Const.LOGINUSER, sr.getData());

            Users users2 = new Users();

            users2.setId(users.getId());
            users2.setUsername(users.getUsername());
            users2.setEmail(users.getEmail());
            users2.setPhone(users.getPhone());
            users2.setCreateTime(users.getCreateTime());
            users2.setUpdateTime(users.getUpdateTime());
            users.setPassword("");

            sr.setData(users2);
        }
        return sr;
    }

    //    用户注册
    @PostMapping("register.do")
    public ServerResponse<Users> register(Users u) {
        ServerResponse<Users> sr = userService.register(u);
        return sr;
    }

    //    检查用户名/邮箱是否有效
    @PostMapping("check_valid.do")
    public ServerResponse<Users> checkValid(String str, String type) {
        ServerResponse<Users> sr = userService.check_valid(str, type);
        return sr;
    }

    //    获取用户登录信息
    @GetMapping("get_user_info.do")
    public ServerResponse getUserInfo(HttpSession session) {
        Users user = (Users) session.getAttribute(Const.LOGINUSER);
        if (user == null) {
            return ServerResponse.defeatedRS(Const.UserEnum.NO_LOGIN.getCode(), Const.UserEnum.NO_LOGIN.getDesc());
        }
        return ServerResponse.successRS(user);
    }

    //    登录状态更新个人信息
    @PostMapping("update_information.do")
    public ServerResponse<Users> updateInformation(Users users, HttpSession session) {
        Users users2 = (Users) session.getAttribute(Const.LOGINUSER);
        if (users2 == null) {
            return ServerResponse.defeatedRS(Const.UserEnum.NO_LOGIN.getCode(), Const.UserEnum.NO_LOGIN.getDesc());
        }
        users.setId(users2.getId());
        users.setUsername(users2.getUsername());
        ServerResponse sr = userService.update_information(users);
//        将更新后的用户存入session
        session.setAttribute(Const.LOGINUSER, users);
        return sr;
    }

    //    获取当前登录用户的详细信息
    @GetMapping("get_inforamtion.do")
    public ServerResponse<Users> getInforamtion(HttpSession session) {
        Users users = (Users) session.getAttribute(Const.LOGINUSER);
        if (users == null) {
            return ServerResponse.defeatedRS(10, Const.USER_NO_LOGIN_NO_MSG);
        }
        ServerResponse<Users> sr = userService.getInforamtion(users);
        return sr;
    }

    //    退出登录
    @GetMapping("logout.do")
    public ServerResponse<Users> logout(HttpSession session) {
        session.removeAttribute(Const.LOGINUSER);
        return ServerResponse.successRS("退出成功");
    }

    //    忘记密码
    @PostMapping("forget_get_question.do")
    public ServerResponse<Users> forgetGetQuestion(String username) {
        ServerResponse<Users> sr = userService.forgetGetQuestion(username);
        return sr;
    }

    //    提交问题答案
    @PostMapping("forget_check_answer.do")
    public ServerResponse<Users> forgetCheckAnswer(String username, String question, String answer) {
        ServerResponse<Users> sr = userService.forgetCheckAnswer(username, question, answer);
        return sr;
    }

    //    忘记密码的重设密码
    @PostMapping("forget_reset_password.do")
    public ServerResponse<Users> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        ServerResponse<Users> sr = userService.forgetResetPassword(username, passwordNew, forgetToken);
        return sr;
    }

    //    登录中状态重置密码
    @PostMapping("reset_password.do")
    public ServerResponse<Users> resetPassword(String passwordOld, String passwordNew,HttpSession session) {
        Users users = (Users) session.getAttribute(Const.LOGINUSER);
        if(users == null){
            return ServerResponse.defeatedRS(Const.USER_NO_LOGIN_NO_MSG);
        }else {
            return userService.resetPassword(users,passwordOld,passwordNew);
        }
    }

}