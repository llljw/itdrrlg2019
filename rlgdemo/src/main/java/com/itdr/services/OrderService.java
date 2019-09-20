package com.itdr.services;

import com.itdr.common.ResponseCode;
import com.itdr.common.ServerResponse;
import com.itdr.pojo.Order;

/**
 * User: Jwen
 * Date: 2019/9/7
 * Time: 13:43
 */

public interface OrderService {
    /*订单列表*/
    ServerResponse selectAll();

    /*按订单号查询*/
    ServerResponse selectOne(Long orderNo );

    /*id查询*/
    ServerResponse selectOneById(Integer id);
}
