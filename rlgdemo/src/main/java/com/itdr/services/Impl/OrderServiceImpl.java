package com.itdr.services.Impl;

import com.itdr.common.Const;
import com.itdr.common.ResponseCode;
import com.itdr.common.ServerResponse;
import com.itdr.mappers.OrderMapper;
import com.itdr.pojo.Order;
import com.itdr.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: Jwen
 * Date: 2019/9/7
 * Time: 13:43
 */

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    /*订单列表*/
    @Override
    public ServerResponse selectAll() {
        List<Order> li = orderMapper.selectAll();
        ServerResponse sr = ServerResponse.successRS(li);
        return sr;
    }

    /*订单号查询*/
    @Override
    public ServerResponse selectOne(Long orderNo) {
        ServerResponse sr = null;
        if (orderNo == null) {
            sr = ServerResponse.defeatedRS(1,Const.ORDER_ORDERNO_FALSE_MSG);
            return sr;
        }
        Order o = orderMapper.selectByOrderNo(orderNo);
        sr = ServerResponse.successRS(o);
        return sr;
    }

    /*ID查询*/
    @Override
    public ServerResponse selectOneById(Integer id) {
        ServerResponse sr = null;
        if(id == null){
            sr = ServerResponse.defeatedRS(1,Const.ORDER_ID_FALSE_MSG);
            return sr;
        }
        Order o = orderMapper.selectByPrimaryKey(id);
        sr = ServerResponse.successRS(o);
        return sr;
    }


}
