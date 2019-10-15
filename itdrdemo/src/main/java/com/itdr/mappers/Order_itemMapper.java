package com.itdr.mappers;

import com.itdr.pojo.Order_item;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface Order_itemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order_item record);

    int insertSelective(Order_item record);

    Order_item selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order_item record);

    int updateByPrimaryKey(Order_item record);

    //根据订单编号查询
    List<Order_item> selectByOrderNo(Long oid);




    int insertAll(@Param("orderItem") List<Order_item> orderItem);
    //根据订单编号和id查询
    List<Order_item> selectByUidAndOrderNo(@Param("uid") Integer uid, @Param("orderNo") Long orderNo);
}