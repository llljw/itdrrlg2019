package com.itdr.services.Impl;

import com.itdr.common.Const;
import com.itdr.common.ServerResponse;
import com.itdr.mappers.CategoryMapper;
import com.itdr.mappers.ProductMapper;
import com.itdr.pojo.Category;
import com.itdr.pojo.Product;
import com.itdr.services.CategoryService;
import com.itdr.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User: Jwen
 * Date: 2019/9/6
 * Time: 19:31
 */

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;


    /*后端*/
//    商品列表
    @Override
    public ServerResponse selectAll() {
        List<Product> li = productMapper.selectAll();
        ServerResponse sr = ServerResponse.successRS(li);
        return sr;
    }

    //    根据id或名称商品搜索
    @Override
    public ServerResponse selectOne(String productName, Integer productId) {
        ServerResponse sr = null;
        List<Product> li = productMapper.selectOne("%" + productName + "%", productId);
        /*判断商品是否存在*/
        if (li == null || li.size() == 0) {
            sr = ServerResponse.defeatedRS(1, Const.PRODUCT_NO_MSG);
            return sr;
        }
        sr = ServerResponse.successRS(li);
        return sr;
    }

    //    商品上下架
    @Override
    public ServerResponse selectByIdSaleStatus(Product p) {
        ServerResponse sr = null;
        /*产品上下架*/
        int i = productMapper.updateByPrimaryKeySelective(p);
        if (i <= 0) {
            sr = ServerResponse.defeatedRS(1, Const.PRODUCT_AMEND_FAILED_MSG);
            return sr;
        }
        sr = ServerResponse.successRS(0, Const.PRODUCT_AMEND_MSG);
        return sr;
    }

    //    新增产品
    @Override
    public ServerResponse insertOne(Product p) {
        ServerResponse sr = null;
        int i = productMapper.insert(p);
        if (i <= 0) {
            sr = ServerResponse.defeatedRS(Const.PRODUCT_APPEND_FAILED_MSG);
            return sr;
        }
        sr = ServerResponse.successRS(Const.PRODUCT_APPEND_MSG);
        return sr;
    }

    //    更新产品
    @Override
    public ServerResponse updateOne(Product p) {
        ServerResponse sr = null;
        int i = productMapper.updateByPrimaryKeySelective(p);
        if (i <= 0) {
            sr = ServerResponse.defeatedRS(Const.PRODUCT_UPDATE_FAILED_MSG);
            return sr;
        }
        sr = ServerResponse.successRS(Const.PRODUCT_UPDATE_MSG);
        return sr;
    }


    /*前端*/
//    产品搜索及动态排序List
    @Override
    public ServerResponse<Product> list(Product product) {
        List<Product> products = productMapper.selectByCategoryId(product.getCategoryId());
        if (products == null) {
            return ServerResponse.defeatedRS(Const.ProductEnum.PARAMETER_WRONG.getCode(), Const.ProductEnum.PARAMETER_WRONG.getDesc());
        }
        return null;
    }

    //    产品详情
    @Override
    public ServerResponse<Product> detail(Product product) {
        Product product1 = productMapper.selectByPrimaryKey(product.getId());
        if (product1 == null) {
            return ServerResponse.defeatedRS(Const.ProductEnum.PARAMETER_WRONG.getCode(), Const.ProductEnum.PARAMETER_WRONG.getDesc());
        }
        if (product1.getStatus() == 0) {
            return ServerResponse.defeatedRS(Const.ProductEnum.SOLD_OUT.getCode(), Const.ProductEnum.SOLD_OUT.getDesc());
        }
        return ServerResponse.successRS(product1);
    }

    //    获取产品分类
    @Override
    public ServerResponse<Category> topCategory(Integer sid) {
        if (sid == null) {
            sid = 0;
        }
        List<Category> products = productMapper.selectByPid(sid);
        return ServerResponse.successRS(products);
    }


}
