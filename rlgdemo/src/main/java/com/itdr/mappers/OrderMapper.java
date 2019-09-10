package com.itdr.mappers;

import com.itdr.pojo.Order;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    /*列表*/
    List<Order> selectAll();

    /*订单号查询*/
    Order selectByOrderNo(Integer order_no);
}