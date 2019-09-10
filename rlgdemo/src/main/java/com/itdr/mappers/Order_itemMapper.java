package com.itdr.mappers;

import com.itdr.pojo.Order_item;

public interface Order_itemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order_item record);

    int insertSelective(Order_item record);

    Order_item selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order_item record);

    int updateByPrimaryKey(Order_item record);
}