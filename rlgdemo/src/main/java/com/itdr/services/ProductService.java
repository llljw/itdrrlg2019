package com.itdr.services;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.Product;

import java.io.IOException;


/**
 * User: Jwen
 * Date: 2019/9/6
 * Time: 19:31
 */
public interface ProductService {
    //    商品列表
    ServerResponse<Product> selectAll();

    //    根据id或名称商品搜索
    ServerResponse<Product> selectOne(String productName, Integer productId);

    //    商品上下架
    ServerResponse<Product> selectByIdSaleStatus(Product p);

    //    新增产品
    ServerResponse<Product> insertOne(Product p);

    //    更新产品
    ServerResponse<Product> updateOne(Product p);

    //    一个产品详情
    ServerResponse<Product> detail(Integer productId);

    //    获取最新、最热、banner产品详情
    ServerResponse<Product> detailNewOrHotOrBanner( Integer is_new, Integer is_hot, Integer is_banner);


    //    获取产品分类
    ServerResponse<Product> topCategory(Integer sid);

    //    产品搜索及动态排序List
    ServerResponse<Product> listProduct(Integer productId, String keyWord, Integer pageNum, Integer pageSize, String orderBy);

}
