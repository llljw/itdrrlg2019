package com.itdr.controller.portal;

import com.itdr.common.Const;
import com.itdr.common.ServerResponse;
import com.itdr.pojo.Users;
import com.itdr.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * User: Jwen
 * Date: 2019/9/10
 * Time: 15:57
 */


@RestController
@RequestMapping("/portal/user/")
public class UserController {

    @Autowired
    UserService userService;

    //    用户登录
    @RequestMapping("login.do")
    public ServerResponse<Users> login(String username, String password, HttpSession session) {
        ServerResponse<Users> sr = userService.login(username, password);

        //当返回的是成功状态才执行
        if (sr.isSuccess()) {
            Users users = sr.getData();
            session.setAttribute(Const.User.LOGINUSER, sr.getData());

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
    @RequestMapping("register.do")
    public ServerResponse<Users> register(Users u) {
        ServerResponse<Users> sr = userService.register(u);
        return sr;
    }

    //    检查用户名/邮箱是否有效
    @RequestMapping("check_valid.do")
    public ServerResponse<Users> checkValid(String str, String type) {
        ServerResponse<Users> sr = userService.check_valid(str, type);
        return sr;
    }

    //    获取用户登录信息
    @RequestMapping("get_user_info.do")
    public ServerResponse getUserInfo(HttpSession session) {
        Users user = (Users) session.getAttribute(Const.User.LOGINUSER);
        if (user == null) {
            return ServerResponse.defeatedRS(Const.UserEnum.NOT_LOGIN.getCode(), Const.UserEnum.NOT_LOGIN.getDesc());
        }
        return ServerResponse.successRS(user);
    }

    //    登录状态更新个人信息
    @RequestMapping("update_information.do")
    public ServerResponse<Users> updateInformation(Users users, HttpSession session) {
        Users users2 = (Users) session.getAttribute(Const.User.LOGINUSER);
        if (users2 == null) {
            return ServerResponse.defeatedRS(Const.UserEnum.NOT_LOGIN.getCode(), Const.UserEnum.NOT_LOGIN.getDesc());
        }
        users.setId(users2.getId());
        users.setUsername(users2.getUsername());
        ServerResponse sr = userService.update_information(users);
//        将更新后的用户存入session
        session.setAttribute(Const.User.LOGINUSER, users);
        return sr;
    }

    //    获取当前登录用户的详细信息
    @RequestMapping("get_inforamtion.do")
    public ServerResponse<Users> getInforamtion(HttpSession session) {
        Users users = (Users) session.getAttribute(Const.User.LOGINUSER);
        if (users == null) {
            return ServerResponse.defeatedRS(Const.UserEnum.NOT_LOGIN.getCode(), Const.UserEnum.NOT_LOGIN.getDesc());
        }
        ServerResponse<Users> sr = userService.getInforamtion(users);
        return sr;
    }

    //    退出登录
    @RequestMapping("logout.do")
    public ServerResponse<Users> logout(HttpSession session) {
        session.removeAttribute(Const.User.LOGINUSER);
        return ServerResponse.successRS(Const.UserEnum.LOGIN_OUT_SUCCESS.getCode(),Const.UserEnum.LOGIN_OUT_SUCCESS.getDesc());
    }

    //    忘记密码
    @RequestMapping("forget_get_question.do")
    public ServerResponse<Users> forgetGetQuestion(String username) {
        ServerResponse<Users> sr = userService.forgetGetQuestion(username);
        return sr;
    }

    //    提交问题答案
    @RequestMapping("forget_check_answer.do")
    public ServerResponse<Users> forgetCheckAnswer(String username, String question, String answer) {
        ServerResponse<Users> sr = userService.forgetCheckAnswer(username, question, answer);
        return sr;
    }

    //    忘记密码的重设密码
    @RequestMapping("forget_reset_password.do")
    public ServerResponse<Users> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        ServerResponse<Users> sr = userService.forgetResetPassword(username, passwordNew, forgetToken);
        return sr;
    }

    //    登录中状态重置密码
    @RequestMapping("reset_password.do")
    public ServerResponse<Users> resetPassword(String passwordOld, String passwordNew,HttpSession session) {
        Users users = (Users) session.getAttribute(Const.User.LOGINUSER);
        if(users == null){
            return ServerResponse.defeatedRS(Const.UserEnum.NOT_LOGIN_BUT_HAVE.getCode(),Const.UserEnum.NOT_LOGIN_BUT_HAVE.getDesc());
        }else {
            return userService.resetPassword(users,passwordOld,passwordNew);
        }
    }

}