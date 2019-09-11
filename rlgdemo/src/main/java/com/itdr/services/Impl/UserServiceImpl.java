package com.itdr.services.Impl;

import com.itdr.common.Const;
import com.itdr.common.ResponseCode;
import com.itdr.common.ServerResponse;
import com.itdr.common.TokenCache;
import com.itdr.mappers.UsersMapper;
import com.itdr.pojo.Users;
import com.itdr.services.UserService;
import com.itdr.utils.MD5Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

/**
 * User: Jwen
 * Date: 2019/9/6
 * Time: 18:04
 */

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    /*后端*/
//    登录
    @Override
    public ServerResponse selectOne(String username, String password) {
        ServerResponse sr = null;
        if (username == null || username.equals("") || password == null || password.equals("")) {
            sr = ServerResponse.defeatedRS(1, Const.USER_NAMEORPSD_NOT_NULL);
            return sr;
        }
        Users users = usersMapper.selectByNameAndPassword(username, password);
        if (users == null) {
            sr = ServerResponse.defeatedRS(Const.USER_CUO_MSG);
            return sr;
        }
        sr = ServerResponse.successRS(users);
        return sr;
    }

    //    用户列表
    @Override
    public ServerResponse selectAll() {
        List<Users> li = usersMapper.selectAll();
        ServerResponse rc = ServerResponse.successRS(li);
        return rc;
    }


    /*前端*/
//    用户登录
    @Override
    public ServerResponse<Users> login(String username, String password) {
        if (username == null || username.equals("")) {
            return ServerResponse.defeatedRS("用户名不能为空");
        }
        if (password == null || password.equals("")) {
            return ServerResponse.defeatedRS("密码不能为空");
        }

//        根据用户名查找是否存在该用户
        int i = usersMapper.selectByUserNameOrEmail(username, "username");
        if (i <= 0) {
            return ServerResponse.defeatedRS(101, "用户不存在");
        }

//        md5加密
        String md5Code = MD5Utils.getMD5Code(password);


//        根据用户名和密码查询用户是否存在
        Users users = usersMapper.selectByUsernameAndPassword(username, md5Code);

        if (users == null) {
            return ServerResponse.defeatedRS("账户或密码错误");
        }


//        封装数据并返回
        ServerResponse sr = ServerResponse.successRS(users);
        return sr;
    }

    //    用户注册
    @Override
    public ServerResponse<Users> register(Users u) {
        if (u.getUsername() == null || u.getUsername().equals("")) {
            return ServerResponse.defeatedRS(1, "账户名不能为空");
        }
        if (u.getPassword() == null || u.getPassword().equals("")) {
            return ServerResponse.defeatedRS(1, "密码不能为空");
        }

        //检查注册用户名是否存在
        int i2 = usersMapper.selectByUserNameOrEmail(u.getUsername(), "username");
        if (i2 > 0) {
            return ServerResponse.defeatedRS(1, "用户已存在");
        }

        //检查注册邮箱是否存在
        int i3 = usersMapper.selectByUserNameOrEmail(u.getEmail(), "email");
        if (i3 > 0) {
            return ServerResponse.defeatedRS(2, "邮箱已注册");
        }

//        md5加密
        u.setPassword(MD5Utils.getMD5Code(u.getPassword()));

        //注册用户
        int i = usersMapper.insert(u);
        if (i <= 0) {
            return ServerResponse.defeatedRS(1, "注册失败");
        }
        return ServerResponse.successRS(null, "用户注册成功");
    }

    //    检查用户名/邮箱是否有效
    @Override
    public ServerResponse<Users> check_valid(String str, String type) {
        //判断
        if (str == null || str.equals("")) {
            return ServerResponse.defeatedRS("参数不能为空");
        }
        if (type == null || type.equals("")) {
            return ServerResponse.defeatedRS("参数类型不能为空");
        }

        int i = usersMapper.selectByUserNameOrEmail(str, type);
        if (i > 0 && type.equals("username")) {
            return ServerResponse.defeatedRS(1, "用户名已存在");
        }
        if (i > 0 && type.equals("email")) {
            return ServerResponse.defeatedRS(2, "邮箱已注册");
        }
        return ServerResponse.successRS(null, "校验成功");
    }

    //    登录状态更新个人信息
    @Override
    public ServerResponse update_information(Users users) {
        int i2 = usersMapper.selectByEmailAndId(users.getEmail(), users.getId());
        if (i2 > 0) {
            return ServerResponse.defeatedRS("要更新的用户邮箱已存在");
        }
        int i = usersMapper.updateByPrimaryKeySelective(users);
        if (i <= 0) {
            return ServerResponse.defeatedRS("更新失败");
        }
        return ServerResponse.successRS("更新个人信息成功");
    }

    //    获取当前登录用户详细信息
    @Override
    public ServerResponse<Users> getInforamtion(Users users) {
        Users u = usersMapper.selectByPrimaryKey(users.getId());
        if (u == null) {
            return ServerResponse.defeatedRS("用户不存在");
        }
        u.setPassword("");
        return ServerResponse.successRS(u);
    }

    //    忘记密码
    @Override
    public ServerResponse<Users> forgetGetQuestion(String username) {
        if (username == null || username.equals("")) {
            return ServerResponse.defeatedRS(100, "用户名不能为空");
        }
        int i = usersMapper.selectByUserNameOrEmail(username, Const.USERNAME);
        if (i <= 0) {
            return ServerResponse.defeatedRS(101, "用户名不存在");
        }
        String s = usersMapper.selectByUserName(username);
        if (s == null || "".equals(s)) {
            return ServerResponse.defeatedRS(1, "该用户未设置找回密码问题");
        }
        ServerResponse sr = ServerResponse.successRS(s);
        return sr;
    }

    //    提交问题答案
    @Override
    public ServerResponse<Users> forgetCheckAnswer(String username, String question, String answer) {
        if (username == null || username.equals("")) {
            return ServerResponse.defeatedRS(100, "用户名不能为空");
        }
        if (question == null || question.equals("")) {
            return ServerResponse.defeatedRS(101, "问题不能为空");
        }
        if (answer == null || answer.equals("")) {
            return ServerResponse.defeatedRS(101, "答案不能为空");
        }

        //判断用户是否存在
        String s = usersMapper.selectByUserName(username);
        if(s == null || "".equals(s)){
            return ServerResponse.defeatedRS("用户不存在");
        }


        int i = usersMapper.selectByUsernameAndQuestionAndAnswer(username, question, answer);
        if (i <= 0) {
            return ServerResponse.defeatedRS(1, "问题答案错误");
        }
        //    产生随机字符令牌
        String token = UUID.randomUUID().toString();

        //    把令牌放入缓存中
        TokenCache.set("token_" + username, token);

        return ServerResponse.successRS(token);
    }

    //    忘记密码的重设密码
    @Override
    public ServerResponse<Users> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        if (username == null || "".equals(username)) {
            return ServerResponse.defeatedRS(100, "用户名不能为空");
        }
        if (passwordNew == null || "".equals(passwordNew)) {
            return ServerResponse.defeatedRS(100, "新密码不能为空");
        }
        if (forgetToken == null || "".equals(forgetToken)) {
            return ServerResponse.defeatedRS(104, "非法的token");
        }

        //    判断缓存中的token
        String token = TokenCache.get("token_" + username);
        if (token == null || "".equals(token)) {
            return ServerResponse.defeatedRS(103, "token已失效");
        }
        //    不成功的情况下
        if (!token.equals(forgetToken)) {
            return ServerResponse.defeatedRS(104, "非法的token");
        }

        //    md5加密
        String md5Code = MD5Utils.getMD5Code(passwordNew);

        int i = usersMapper.updateByUsernameAndPassword(username, md5Code);
        if (i <= 0) {
            return ServerResponse.defeatedRS(100, "修改密码失败");
        }
        return ServerResponse.successRS("修改密码成功");
    }

    //    登录状态修改密码
    @Override
    public ServerResponse<Users> resetPassword(Users users, String passwordOld, String passwordNew) {
        if (passwordOld == null || "".equals(passwordOld)) {
            return ServerResponse.defeatedRS(100, "参数不能为空");
        }
        if (passwordNew == null || "".equals(passwordNew)) {
            return ServerResponse.defeatedRS(100, "参数不能为空");
        }

        //    md5加密
        String md5CodeOld = MD5Utils.getMD5Code(passwordOld);

        int i = usersMapper.selectByIdAndPassword(users.getId(), md5CodeOld);
        if (i <= 0) {
            return ServerResponse.defeatedRS("旧密码输入错误");
        }

        //    md5加密
        String md5CodeNew = MD5Utils.getMD5Code(passwordNew);

        int i1 = usersMapper.updateByUsernameAndPassword(users.getUsername(), md5CodeNew);
        if (i1 <= 0) {
            return ServerResponse.defeatedRS("旧密码更新失败");
        }
        return ServerResponse.successRS("旧密码更新成功");
    }
}
