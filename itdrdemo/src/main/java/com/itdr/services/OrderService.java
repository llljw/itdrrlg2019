package com.itdr.services;

import com.itdr.common.ServerResponse;
import org.apache.ibatis.annotations.Mapper;

/**
 * User: Jwen
 * Date: 2019/9/7
 * Time: 13:43
 */
public interface OrderService {
    /*订单列表*/
    ServerResponse selectAll();

    /*按订单号查询*/
    ServerResponse selectOne(Long orderNo);

    /*id查询*/
    ServerResponse selectOneById(Integer id);

    //创建订单
    ServerResponse createOrder(Integer shippingId, Integer id);

    //获取订单的商品信息
    ServerResponse getOrderCartProduct(Integer uid, Long orderNo);

    //获取订单列表
    ServerResponse getOrderList(Integer id, Integer pageNum, Integer pageSize);

    //取消订单
    ServerResponse countermandOrder(Integer id, Long orderNo);
}
