package com.itdr.services.Impl;

import com.alipay.api.domain.Car;
import com.itdr.common.Const;
import com.itdr.common.ServerResponse;
import com.itdr.mappers.CartMapper;
import com.itdr.pojo.Cart;
import com.itdr.pojo.Users;
import com.itdr.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * User: Jwen
 * Date: 2019/9/12
 * Time: 21:48
 */

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartMapper cartMapper;

    //    购物车列表
    @Override
    public ServerResponse<Cart> list() {
        List<Cart> carts = cartMapper.selectAll();
        if (carts == null) {
            return ServerResponse.defeatedRS(Const.CartEnum.CHOOSE_NULL.getCode(), Const.CartEnum.CHOOSE_NULL.getDesc());
        }
        return ServerResponse.successRS(carts);
    }

    //    增加商品
    @Override
    public ServerResponse<Cart> insert(Integer productId, Integer count, HttpSession session) {
        Users users = (Users) session.getAttribute(Const.LOGINUSER);
        if (users == null) {
            return ServerResponse.defeatedRS(Const.UserEnum.NOT_LOGIN_BUT_HAVE.getCode(), Const.UserEnum.NOT_LOGIN_BUT_HAVE.getDesc());
        }

        if (productId == null || "".equals(productId) || count == null || "".equals(count)) {
            return ServerResponse.defeatedRS(Const.CartEnum.PARAMETER_NULL.getCode(), Const.CartEnum.PARAMETER_NULL.getDesc());
        }

        Cart cart = new Cart();
        cart.setUserId(users.getId());
        cart.setProductId(productId);
        cart.setQuantity(count);

        int insert = cartMapper.insertProduct(cart);

        if (insert <= 0) {
            return ServerResponse.defeatedRS(Const.CartEnum.ADD_WRONG.getCode(), Const.CartEnum.ADD_WRONG.getDesc());
        }

        List<Cart> list = cartMapper.selectAll();
        return ServerResponse.successRS(list);
    }

    //    增加商品数量
    @Override
    public ServerResponse<Cart> update(Integer productId, Integer count, HttpSession session) {
        Users users = (Users) session.getAttribute(Const.LOGINUSER);
        if (users == null) {
            return ServerResponse.defeatedRS(Const.UserEnum.NOT_LOGIN_BUT_HAVE.getCode(), Const.UserEnum.NOT_LOGIN_BUT_HAVE.getDesc());
        }

        if (productId == null || "".equals(productId) || count == null || "".equals(count)) {
            return ServerResponse.defeatedRS(Const.CartEnum.PARAMETER_NULL.getCode(), Const.CartEnum.PARAMETER_NULL.getDesc());
        }

        Cart cart = new Cart();
        cart.setUserId(users.getId());
        cart.setProductId(productId);
        cart.setQuantity(count);

        int insert = cartMapper.updateByProductId(cart);

        if (insert <= 0) {
            return ServerResponse.defeatedRS(Const.CartEnum.ADD_WRONG.getCode(), Const.CartEnum.ADD_WRONG.getDesc());
        }

        List<Cart> list = cartMapper.selectAll();
        return ServerResponse.successRS(list);
    }

    //    移除商品
    @Override
    public ServerResponse<Cart> deleteByProductId(Integer productId, HttpSession session) {
        Users users = (Users) session.getAttribute(Const.LOGINUSER);
        if (users == null) {
            return ServerResponse.defeatedRS(Const.UserEnum.NOT_LOGIN_BUT_HAVE.getCode(), Const.UserEnum.NOT_LOGIN_BUT_HAVE.getDesc());
        }
        Cart cart = cartMapper.selectByProductId(productId);
        if (cart == null) {
            return ServerResponse.defeatedRS(Const.CartEnum.NO_PRODUCT.getCode(), Const.CartEnum.NO_PRODUCT.getDesc());
        }
        int i = cartMapper.deleteByProductId(productId);
        List<Cart> list = cartMapper.selectAll();
        return ServerResponse.successRS(list);
    }
}
