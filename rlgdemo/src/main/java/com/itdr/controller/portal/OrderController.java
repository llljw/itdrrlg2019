package com.itdr.controller.portal;

import com.itdr.common.Const;
import com.itdr.common.ServerResponse;
import com.itdr.pojo.Users;
import com.itdr.services.OrderService;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * User: Jwen
 * Date: 2019/9/21
 * Time: 11:33
 */
@RestController
@RequestMapping("/order/")
public class OrderController {
    @Autowired
    OrderService orderService;

    //创建订单
    @RequestMapping("create.do")
    public ServerResponse createOrder(Integer shippingId,HttpSession session) {
        Users users = (Users) session.getAttribute(Const.User.LOGINUSER);
        if (users == null) {
            return ServerResponse.defeatedRS(Const.UserEnum.NOT_LOGIN_BUT_HAVE.getCode(), Const.UserEnum.NOT_LOGIN_BUT_HAVE.getDesc());
        }
        ServerResponse sr = orderService.createOrder(users.getId(),shippingId);
        return sr;
    }

}
