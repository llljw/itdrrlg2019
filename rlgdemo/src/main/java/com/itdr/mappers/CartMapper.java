package com.itdr.mappers;

import com.itdr.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

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

    //    根据用户id查询所有购物数据
    List<Cart> selectByUid(Integer id);

    int selectOrUnSelect(@Param("id") Integer id,
                         @Param("check") Integer check,
                         @Param("productId") Integer productId);

    //    根据用户id和商品id判断数据是否存在
    Cart selectByProductIdAndUid(@Param("productId") Integer productId, @Param("uid") Integer uid);

    //    根据用户id判断用户购物车是否全选
    int selectByUidCheckAll(@Param("uid")Integer uid,@Param("check")Integer check);
}