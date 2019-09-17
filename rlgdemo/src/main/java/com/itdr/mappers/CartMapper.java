package com.itdr.mappers;

import com.itdr.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    int insertProduct(Cart cart);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    //    购物车列表
    List<Cart> selectAll();

    //    根据商品id添加
    int updateByProductId(Cart cart);

    //    根据商品id查询
    Cart selectByProductId(Integer productId);

    //    根据商品id删除
    int deleteByProductId(Integer productId);
}