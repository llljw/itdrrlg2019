package com.itdr.services;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.vo.CartVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * User: Jwen
 * Date: 2019/9/12
 * Time: 21:41
 */

public interface CartService {
    //    获取登录用户购物车列表
    ServerResponse<CartVO> list(Integer id);

    //    购物车增加商品
    ServerResponse<CartVO> insert(Integer productId, Integer count, Integer uid);

    //    更新产品数量
    ServerResponse<CartVO> update(Integer productId, Integer count, Integer uid);

    //    购物车删除
    ServerResponse<CartVO> deleteByProductId(String productIds, Integer id);

    //    查询在购物车里的产品数量
    ServerResponse<Integer> getCartProductCount(Integer id);

    //    改变购物车中商品选中状态
    ServerResponse<CartVO> selectOrUnSelect(Integer id, Integer check, Integer productId);
}
