package com.itdr.mappers;


import com.itdr.pojo.Category;
import com.itdr.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    //增加
    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Product id);

    Product selectByPrimaryKey(Integer id);

    //更新商品
    int updateByPrimaryKeySelective(Product record);

    //更新商品
    int updateByPrimaryKey(Product record);

    //商品列表
    List<Product> selectAll();

    //根据id或名称商品搜索
    List<Product> selectOne(@Param("name") String name, @Param("id") Integer id);

    //产品搜索及动态排序List
    List<Product> selectByIdOrName(@Param("productId") Integer productId,
                                   @Param("keyWord") String keyWord,
                                   @Param("col") String col,
                                   @Param("order") String order);

    //根据父id查询分类
    List<Category> selectByPid(Integer sid);

    //根据商品id获取商品详情
    Product selectById(@Param("productId") Integer productId,
                       @Param("is_new") Integer is_new,
                       @Param("is_hot") Integer is_hot,
                       @Param("is_banner") Integer is_banner);


    //根据商品id获取商品数据
    Product selectByIdAll(Integer productId);
}