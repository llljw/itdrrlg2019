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
        if (username == null || username.equals("")) {
            sr = ServerResponse.defeatedRS(Const.UserEnum.NAME_NULL.getCode(),Const.UserEnum.NAME_NULL.getDesc());
            return sr;
        }
        if (password == null || password.equals("")) {
            sr = ServerResponse.defeatedRS(Const.UserEnum.PSD_NULL.getCode(),Const.UserEnum.PSD_NULL.getDesc());
            return sr;
        }
        Users users = usersMapper.selectByNameAndPassword(username, password);
        if (users == null) {
            sr = ServerResponse.defeatedRS(Const.UserEnum.NO_USER.getCode(),Const.UserEnum.NO_USER.getDesc());
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
            return ServerResponse.defeatedRS(Const.UserEnum.NAME_NULL.getCode(),Const.UserEnum.NAME_NULL.getDesc());
        }
        if (password == null || "".equals(password)) {
            return ServerResponse.defeatedRS(Const.UserEnum.PSD_NULL.getCode(),Const.UserEnum.PSD_NULL.getDesc());
        }

//        根据用户名查找是否存在该用户
        int i = usersMapper.selectByUserNameOrEmail(username,Const.User.LOGINUSER);
        if (i <= 0) {
            return ServerResponse.defeatedRS(Const.UserEnum.NO_USER.getCode(),Const.UserEnum.NO_USER.getDesc());
        }

//        md5加密
        String md5Code = MD5Utils.getMD5Code(password);


//        根据用户名和密码查询用户是否存在
        Users users = usersMapper.selectByUsernameAndPassword(username, md5Code);

        if (users == null) {
            return ServerResponse.defeatedRS(Const.UserEnum.PSD_WRONG.getCode(),Const.UserEnum.PSD_WRONG.getDesc());
        }


//        封装数据并返回
        ServerResponse sr = ServerResponse.successRS(users);
        return sr;
    }

    //    用户注册
    @Override
    public ServerResponse<Users> register(Users u) {
        if (u.getUsername() == null || u.getUsername().equals("")) {
            return ServerResponse.defeatedRS(Const.UserEnum.NAME_NULL.getCode(),Const.UserEnum.NAME_NULL.getDesc());
        }
        if (u.getPassword() == null || u.getPassword().equals("")) {
            return ServerResponse.defeatedRS(Const.UserEnum.PSD_NULL.getCode(),Const.UserEnum.PSD_NULL.getDesc());
        }

        //检查注册用户名是否存在
        int i2 = usersMapper.selectByUserNameOrEmail(u.getUsername(), Const.User.USERNAME);
        if (i2 > 0) {
            return ServerResponse.defeatedRS(Const.UserEnum.HAVE_ONE_USER.getCode(),Const.UserEnum.HAVE_ONE_USER.getDesc());
        }

        //检查注册邮箱是否存在
        int i3 = usersMapper.selectByUserNameOrEmail(u.getEmail(), Const.User.EMAIL);
        if (i3 > 0) {
            return ServerResponse.defeatedRS(Const.UserEnum.HAVE_ONE_EMAIL.getCode(),Const.UserEnum.HAVE_ONE_EMAIL.getDesc());
        }

//        md5加密
        u.setPassword(MD5Utils.getMD5Code(u.getPassword()));

        //注册用户
        int i = usersMapper.insert(u);
        if (i <= 0) {
            return ServerResponse.defeatedRS(Const.UserEnum.REGISTER_FAIL.getCode(),Const.UserEnum.REGISTER_FAIL.getDesc());
        }
        return ServerResponse.successRS(Const.UserEnum.REGISTER_SUCCESS.getCode(),Const.UserEnum.REGISTER_SUCCESS.getDesc());
    }

    //    检查用户名/邮箱是否有效
    @Override
    public ServerResponse<Users> check_valid(String str, String type) {
        //判断
        if (str == null || str.equals("")) {
            return ServerResponse.defeatedRS(Const.UserEnum.MSG_NULL.getCode(),Const.UserEnum.MSG_NULL.getDesc());
        }
        if (type == null || type.equals("")) {
            return ServerResponse.defeatedRS(Const.UserEnum.MSG_NULL.getCode(),Const.UserEnum.MSG_NULL.getDesc());
        }

        int i = usersMapper.selectByUserNameOrEmail(str, type);
        if (i > 0 && type.equals("username")) {
            return ServerResponse.defeatedRS(Const.UserEnum.HAVE_ONE_USER.getCode(),Const.UserEnum.HAVE_ONE_USER.getDesc());
        }
        if (i > 0 && type.equals("email")) {
            return ServerResponse.defeatedRS(Const.UserEnum.HAVE_ONE_EMAIL.getCode(),Const.UserEnum.HAVE_ONE_EMAIL.getDesc());
        }
        return ServerResponse.successRS(Const.UserEnum.VERIFY_SUCCESS.getCode(),Const.UserEnum.VERIFY_SUCCESS.getDesc());
    }

    //    登录状态更新个人信息
    @Override
    public ServerResponse update_information(Users users) {
        int i2 = usersMapper.selectByEmailAndId(users.getEmail(), users.getId());
        if (i2 > 0) {
            return ServerResponse.defeatedRS(Const.UserEnum.HAVE_ONE_EMAIL.getCode(),Const.UserEnum.HAVE_ONE_EMAIL.getDesc());
        }
        int i = usersMapper.updateByPrimaryKeySelective(users);
        if (i <= 0) {
            return ServerResponse.defeatedRS(Const.UserEnum.UPDATE_MSG_FAIL.getCode(),Const.UserEnum.UPDATE_MSG_FAIL.getDesc());
        }
        return ServerResponse.successRS(Const.UserEnum.UPDATE_MSG_SUCCESS.getCode(),Const.UserEnum.UPDATE_MSG_SUCCESS.getDesc());
    }

    //    获取当前登录用户详细信息
    @Override
    public ServerResponse<Users> getInforamtion(Users users) {
        Users u = usersMapper.selectByPrimaryKey(users.getId());
        if (u == null) {
            return ServerResponse.defeatedRS(Const.UserEnum.NO_USER.getCode(),Const.UserEnum.NO_USER.getDesc());
        }
        u.setPassword("");
        return ServerResponse.successRS(u);
    }

    //    忘记密码
    @Override
    public ServerResponse<Users> forgetGetQuestion(String username) {
        if (username == null || username.equals("")) {
            return ServerResponse.defeatedRS(Const.UserEnum.NAME_NULL.getCode(),Const.UserEnum.NAME_NULL.getDesc());
        }
        int i = usersMapper.selectByUserNameOrEmail(username, Const.User.USERNAME);
        if (i <= 0) {
            return ServerResponse.defeatedRS(Const.UserEnum.NO_USER.getCode(),Const.UserEnum.NO_USER.getDesc());
        }
        String s = usersMapper.selectByUserName(username);
        if (s == null || "".equals(s)) {
            return ServerResponse.defeatedRS(Const.UserEnum.NOT_FIND_PSD_ANSWER.getCode(),Const.UserEnum.NOT_FIND_PSD_ANSWER.getDesc());
        }
        ServerResponse sr = ServerResponse.successRS(s);
        return sr;
    }

    //    提交问题答案
    @Override
    public ServerResponse<Users> forgetCheckAnswer(String username, String question, String answer) {
        if (username == null || username.equals("")) {
            return ServerResponse.defeatedRS(Const.UserEnum.NAME_NULL.getCode(),Const.UserEnum.NAME_NULL.getDesc());
        }
        if (question == null || question.equals("")) {
            return ServerResponse.defeatedRS(Const.UserEnum.QUESTION_NULL.getCode(),Const.UserEnum.QUESTION_NULL.getDesc());
        }
        if (answer == null || answer.equals("")) {
            return ServerResponse.defeatedRS(Const.UserEnum.ANSWER_NULL.getCode(),Const.UserEnum.ANSWER_NULL.getDesc());
        }

        //判断用户是否存在
        String s = usersMapper.selectByUserName(username);
        if(s == null || "".equals(s)){
            return ServerResponse.defeatedRS(Const.UserEnum.NO_USER.getCode(),Const.UserEnum.NO_USER.getDesc());
        }


        int i = usersMapper.selectByUsernameAndQuestionAndAnswer(username, question, answer);
        if (i <= 0) {
            return ServerResponse.defeatedRS(Const.UserEnum.ANSWER_WRONG.getCode(),Const.UserEnum.ANSWER_WRONG.getDesc());
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
            return ServerResponse.defeatedRS(Const.UserEnum.NAME_NULL.getCode(),Const.UserEnum.NAME_NULL.getDesc());
        }
        if (passwordNew == null || "".equals(passwordNew)) {
            return ServerResponse.defeatedRS(Const.UserEnum.NEW_PSD_NULL.getCode(),Const.UserEnum.NEW_PSD_NULL.getDesc());
        }
        if (forgetToken == null || "".equals(forgetToken)) {
            return ServerResponse.defeatedRS(Const.UserEnum.NO_TOKEN.getCode(),Const.UserEnum.NO_TOKEN.getDesc());
        }

        //    判断缓存中的token
        String token = TokenCache.get("token_" + username);
        if (token == null || "".equals(token)) {
            return ServerResponse.defeatedRS(Const.UserEnum.LOSE_TOKEN.getCode(),Const.UserEnum.LOSE_TOKEN.getDesc());
        }
        //    不成功的情况下
        if (!token.equals(forgetToken)) {
            return ServerResponse.defeatedRS(Const.UserEnum.NO_TOKEN.getCode(),Const.UserEnum.NO_TOKEN.getDesc());
        }

        //    md5加密
        String md5Code = MD5Utils.getMD5Code(passwordNew);

        int i = usersMapper.updateByUsernameAndPassword(username, md5Code);
        if (i <= 0) {
            return ServerResponse.defeatedRS(Const.UserEnum.ALTER_PSD_FAIL.getCode(),Const.UserEnum.ALTER_PSD_FAIL.getDesc());
        }
        return ServerResponse.successRS(Const.UserEnum.ALTER_PSD_SUCCESS.getCode(),Const.UserEnum.ALTER_PSD_SUCCESS.getDesc());
    }

    //    登录状态修改密码
    @Override
    public ServerResponse<Users> resetPassword(Users users, String passwordOld, String passwordNew) {
        if (passwordOld == null || "".equals(passwordOld)) {
            return ServerResponse.defeatedRS(Const.UserEnum.PARAMETER_NULL.getCode(),Const.UserEnum.PARAMETER_NULL.getDesc());
        }
        if (passwordNew == null || "".equals(passwordNew)) {
            return ServerResponse.defeatedRS(Const.UserEnum.PARAMETER_NULL.getCode(),Const.UserEnum.PARAMETER_NULL.getDesc());
        }

        //    md5加密
        String md5CodeOld = MD5Utils.getMD5Code(passwordOld);

        int i = usersMapper.selectByIdAndPassword(users.getId(), md5CodeOld);
        if (i <= 0) {
            return ServerResponse.defeatedRS(Const.UserEnum.OLD_PSD_WRONG.getCode(),Const.UserEnum.OLD_PSD_WRONG.getDesc());
        }

        //    md5加密
        String md5CodeNew = MD5Utils.getMD5Code(passwordNew);

        int i1 = usersMapper.updateByUsernameAndPassword(users.getUsername(), md5CodeNew);
        if (i1 <= 0) {
            return ServerResponse.defeatedRS(Const.UserEnum.OLD_PSD_UPDATE_FAIL.getCode(),Const.UserEnum.OLD_PSD_UPDATE_FAIL.getDesc());
        }
        return ServerResponse.successRS(Const.UserEnum.OLD_PSD_UPDATE_SUCCESS.getCode(),Const.UserEnum.OLD_PSD_UPDATE_SUCCESS.getDesc());
    }
}
