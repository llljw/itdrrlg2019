package com.itdr.controller.portal;

import com.itdr.common.Const;
import com.itdr.common.ServerResponse;
import com.itdr.pojo.Cart;
import com.itdr.pojo.Users;
import com.itdr.pojo.vo.CartVO;
import com.itdr.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * User: Jwen
 * Date: 2019/9/12
 * Time: 21:33
 */

@Controller
@ResponseBody
@RequestMapping("/portal/cart/")
public class CartController {

    @Autowired
    CartService cartService;

    //    购物车列表
    @RequestMapping("list.do")
    public ServerResponse<CartVO> list(HttpSession session) {
        Users users = (Users) session.getAttribute(Const.User.LOGINUSER);
        if (users == null) {
            return ServerResponse.defeatedRS(Const.UserEnum.NOT_LOGIN_BUT_HAVE.getCode(), Const.UserEnum.NOT_LOGIN_BUT_HAVE.getDesc());
        }
        ServerResponse<CartVO> sr = cartService.list(users.getId());
        return sr;
    }

    //    购物车添加商品
    @RequestMapping("add.do")
    public ServerResponse<CartVO> add(Integer productId, Integer count,HttpSession session) {
        Users users = (Users) session.getAttribute(Const.User.LOGINUSER);
        if (users == null) {
            return ServerResponse.defeatedRS(Const.UserEnum.NOT_LOGIN_BUT_HAVE.getCode(), Const.UserEnum.NOT_LOGIN_BUT_HAVE.getDesc());
        }
        ServerResponse<CartVO> sr = cartService.insert(productId, count,users.getId());
        return sr;
    }

    //    更新产品数量
    @RequestMapping("update.do")
    public ServerResponse<CartVO> update(Integer productId, Integer count, HttpSession session) {
        Users users = (Users) session.getAttribute(Const.User.LOGINUSER);
        if (users == null) {
            return ServerResponse.defeatedRS(Const.UserEnum.NOT_LOGIN_BUT_HAVE.getCode(), Const.UserEnum.NOT_LOGIN_BUT_HAVE.getDesc());
        }
        ServerResponse<CartVO> sr = cartService.update(productId, count, users.getId());
        return sr;
    }

    //    移除购物车某个商品
    @RequestMapping("delete_product.do")
    public ServerResponse<CartVO> delete(String productIds, HttpSession session) {
        Users users = (Users) session.getAttribute(Const.User.LOGINUSER);
        if (users == null) {
            return ServerResponse.defeatedRS(Const.UserEnum.NOT_LOGIN_BUT_HAVE.getCode(), Const.UserEnum.NOT_LOGIN_BUT_HAVE.getDesc());
        }
        ServerResponse<CartVO> sr = cartService.deleteByProductId(productIds,users.getId());
        return sr;
    }

    //    查询在购物车里的产品数量
    @RequestMapping("get_cart_product_count.do")
    public ServerResponse<Integer> getCartProductCount(HttpSession session) {
        Users users = (Users) session.getAttribute(Const.User.LOGINUSER);
        if (users == null) {
            return ServerResponse.defeatedRS(Const.UserEnum.NOT_LOGIN_BUT_HAVE.getCode(), Const.UserEnum.NOT_LOGIN_BUT_HAVE.getDesc());
        }
        ServerResponse<Integer> sr = cartService.getCartProductCount(users.getId());
        return sr;
    }

    //    购物车全选
    @RequestMapping("select_all.do")
    public ServerResponse<CartVO> selectAll(HttpSession session, Integer check) {
        Users users = (Users) session.getAttribute(Const.User.LOGINUSER);
        if (users == null) {
            return ServerResponse.defeatedRS(Const.UserEnum.NOT_LOGIN_BUT_HAVE.getCode(), Const.UserEnum.NOT_LOGIN_BUT_HAVE.getDesc());
        }
        ServerResponse<CartVO> sr = cartService.selectOrUnSelect(users.getId(), check, null);
        return sr;
    }

    //    购物车取消全选
    @RequestMapping("un_select_all.do")
    public ServerResponse<CartVO> UnSelectAll(HttpSession session, Integer check) {
        Users users = (Users) session.getAttribute(Const.User.LOGINUSER);
        if (users == null) {
            return ServerResponse.defeatedRS(Const.UserEnum.NOT_LOGIN_BUT_HAVE.getCode(),
                    Const.UserEnum.NOT_LOGIN_BUT_HAVE.getDesc());
        }
        ServerResponse<CartVO> sr = cartService.selectOrUnSelect(users.getId(), check, null);
        return sr;
    }

    //    购物车选中某个商品
    @RequestMapping("select.do")
    public ServerResponse<CartVO> select(HttpSession session, Integer check, Integer productId) {
        Users users = (Users) session.getAttribute(Const.User.LOGINUSER);
        if (users == null) {
            return ServerResponse.defeatedRS(Const.UserEnum.NOT_LOGIN_BUT_HAVE.getCode(),
                    Const.UserEnum.NOT_LOGIN_BUT_HAVE.getDesc());
        }
        ServerResponse<CartVO> sr = cartService.selectOrUnSelect(users.getId(), check, productId);
        return sr;
    }

    //    购物车取消选中某个商品
    @RequestMapping("un_select.do")
    public ServerResponse<CartVO> UnSelect(HttpSession session, Integer check, Integer productId) {
        Users users = (Users) session.getAttribute(Const.User.LOGINUSER);
        if (users == null) {
            return ServerResponse.defeatedRS(Const.UserEnum.NOT_LOGIN_BUT_HAVE.getCode(),
                    Const.UserEnum.NOT_LOGIN_BUT_HAVE.getDesc());
        }
        ServerResponse<CartVO> sr = cartService.selectOrUnSelect(users.getId(), check, productId);
        return sr;
    }
}
