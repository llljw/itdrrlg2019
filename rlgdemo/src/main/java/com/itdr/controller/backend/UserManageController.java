package com.itdr.controller.backend;

import com.itdr.common.ServerResponse;
import com.itdr.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User: Jwen
 * Date: 2019/9/6
 * Time: 18:03
 */
@Controller
@ResponseBody
@RequestMapping("/manage/user/")
public class UserManageController {

    /*调用业务层*/
    @Autowired
    private UserService userService;

    /*登录*/
    @RequestMapping("login.do")
    public ServerResponse login(String username, String password){
        ServerResponse sr = userService.selectOne(username, password);
        return sr;
    }

    /*用户列表*/
    @RequestMapping("list.do")
    public ServerResponse list(){
        ServerResponse sr = userService.selectAll();
        return sr;
    }
}
