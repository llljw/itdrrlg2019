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

    //更新所有商品
    int updateByPrimaryKey(Product record);

    //商品列表
    List<Product> selectAll();

    //根据id或名称商品搜索
    List<Product> selectOne(@Param("name") String name,@Param("id") Integer id);

    //产品搜索及动态排序List
    List<Product> selectByCategoryId(Integer categoryId);

    //根据父id查询分类
    List<Category> selectByPid(Integer sid);
}