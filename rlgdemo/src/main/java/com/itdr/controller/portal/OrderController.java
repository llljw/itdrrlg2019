package com.itdr.controller.portal;

import com.itdr.common.Const;
import com.itdr.common.ServerResponse;
import com.itdr.pojo.Users;
import com.itdr.services.OrderService;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * User: Jwen
 * Date: 2019/9/21
 * Time: 11:33
 */
@RestController
@RequestMapping("/portal/order/")
public class OrderController {
    @Autowired
    OrderService orderService;

    //创建订单
    @RequestMapping("create.do")
    public ServerResponse createOrder(Integer shippingId, HttpSession session) {
        Users users = (Users) session.getAttribute(Const.User.LOGINUSER);
        if (users == null) {
            return ServerResponse.defeatedRS(Const.UserEnum.NOT_LOGIN_BUT_HAVE.getCode(),
                    Const.UserEnum.NOT_LOGIN_BUT_HAVE.getDesc());
        }
        ServerResponse sr = orderService.createOrder(users.getId(), shippingId);
        return sr;
    }

    /**
     * 获取订单的商品信息
     *
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping("get_order_cart_product.do")
    public ServerResponse getOrderCartProduct(HttpSession session,
                                              @RequestParam(value = "orderNo", required = false) Long orderNo) {
        Users users = (Users) session.getAttribute(Const.User.LOGINUSER);
        if (users == null) {
            return ServerResponse.defeatedRS(Const.UserEnum.NOT_LOGIN_BUT_HAVE.getCode(),
                    Const.UserEnum.NOT_LOGIN_BUT_HAVE.getDesc());
        }
        ServerResponse sr = orderService.getOrderCartProduct(users.getId(), orderNo);
        return sr;
    }


    /**
     * 获取订单列表
     * @param session
     * @param pageSize
     * @param pageNum
     * @return
     */
    @RequestMapping("list.do")
    public ServerResponse getOrderList(HttpSession session,
                                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                       @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum) {
        Users users = (Users) session.getAttribute(Const.User.LOGINUSER);
        if (users == null) {
            return ServerResponse.defeatedRS(Const.UserEnum.NOT_LOGIN_BUT_HAVE.getCode(),
                    Const.UserEnum.NOT_LOGIN_BUT_HAVE.getDesc());
        }
        ServerResponse sr = orderService.getOrderList(users.getId(),pageNum,pageSize);
        return sr;
    }

    /**
     * 取消订单
     * @param session
     * @param orderNo
     * @return
     */
    @RequestMapping("countermand_order.do")
    public ServerResponse countermandOrder(HttpSession session,Long orderNo) {
        Users users = (Users) session.getAttribute(Const.User.LOGINUSER);
        if (users == null) {
            return ServerResponse.defeatedRS(Const.UserEnum.NOT_LOGIN_BUT_HAVE.getCode(),
                    Const.UserEnum.NOT_LOGIN_BUT_HAVE.getDesc());
        }
        ServerResponse sr = orderService.countermandOrder(users.getId(),orderNo);
        return sr;
    }

}
