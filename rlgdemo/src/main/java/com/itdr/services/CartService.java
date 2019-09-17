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
    //    购物车列表
    ServerResponse list();

    //    增加商品
    ServerResponse<Cart> insert(Integer productId, Integer count, HttpSession session);

    //    更新产品数量
    ServerResponse<Cart> update(Integer productId, Integer count, HttpSession session);

    ServerResponse<Cart> deleteByProductId(Integer productId, HttpSession session);
}
