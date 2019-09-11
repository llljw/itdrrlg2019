package com.itdr.services;

import com.itdr.common.ResponseCode;
import com.itdr.common.ServerResponse;
import com.itdr.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User: Jwen
 * Date: 2019/9/6
 * Time: 19:31
 */
public interface ProductService {
    /*商品列表*/
    ServerResponse selectAll();

    /*根据id或名称商品搜索*/
    ServerResponse selectOne(String productName, Integer productId);

    /*商品上下架*/
    ServerResponse selectByID_sale_status(Product p);

    /*新增产品*/
    ServerResponse insertOne(Product p);

    /*更新产品*/
    ServerResponse updateOne(Product p);
}
