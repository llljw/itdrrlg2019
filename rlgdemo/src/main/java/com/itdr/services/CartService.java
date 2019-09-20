package com.itdr.services;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.Cart;
import jdk.internal.org.objectweb.asm.Handle;

import javax.servlet.http.HttpSession;

/**
 * User: Jwen
 * Date: 2019/9/12
 * Time: 21:41
 */
public interface CartService {
    //    获取登录用户购物车列表
    ServerResponse list(Integer id);

    //    增加商品
    ServerResponse<Cart> insert(Integer productId, Integer count, Integer uid);

    //    更新产品数量
    ServerResponse<Cart> update(Integer productId, Integer count, Integer uid);


    ServerResponse<Cart> deleteByProductId(Integer productId, HttpSession session);

    //    查询在购物车里的产品数量
    ServerResponse<Cart> getCartProductCount(Integer id);

    //    改变购物车中商品选中状态
    ServerResponse<Cart> selectOrUnSelect(Integer id, Integer check, Integer productId);
}
