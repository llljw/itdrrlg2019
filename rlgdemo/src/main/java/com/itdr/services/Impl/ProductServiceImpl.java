package com.itdr.services.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itdr.common.Const;
import com.itdr.common.ServerResponse;
import com.itdr.mappers.CategoryMapper;
import com.itdr.mappers.ProductMapper;
import com.itdr.mappers.UsersMapper;
import com.itdr.pojo.Category;
import com.itdr.pojo.Product;
import com.itdr.pojo.vo.ProductVO;
import com.itdr.services.ProductService;
import com.itdr.utils.PoToVoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * User: Jwen
 * Date: 2019/9/6
 * Time: 19:31
 */

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryMapper categoryMapper;

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
            sr = ServerResponse.defeatedRS(Const.ProductEnum.PRODUCT_NO.getCode(),Const.ProductEnum.PRODUCT_NO.getDesc());
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
            sr = ServerResponse.defeatedRS(Const.ProductEnum.AMEND_FAILED.getCode(),Const.ProductEnum.AMEND_FAILED.getDesc());
            return sr;
        }
        sr = ServerResponse.successRS(Const.ProductEnum.AMEND_SUCCESS.getCode(),Const.ProductEnum.AMEND_SUCCESS.getDesc());
        return sr;
    }

    //    新增产品
    @Override
    public ServerResponse insertOne(Product p) {
        ServerResponse sr = null;
        int i = productMapper.insert(p);
        if (i <= 0) {
            sr = ServerResponse.defeatedRS(Const.ProductEnum.APPEND_FAILED.getCode(),Const.ProductEnum.APPEND_FAILED.getDesc());
            return sr;
        }
        sr = ServerResponse.successRS(Const.ProductEnum.APPEND_SUCCESS.getCode(),Const.ProductEnum.APPEND_SUCCESS.getDesc());
        return sr;
    }

    //    更新产品
    @Override
    public ServerResponse updateOne(Product p) {
        ServerResponse sr = null;
        int i = productMapper.updateByPrimaryKeySelective(p);
        if (i <= 0) {
            sr = ServerResponse.defeatedRS(Const.ProductEnum.UPDATE_FAILED.getCode(),Const.ProductEnum.UPDATE_FAILED.getDesc());
            return sr;
        }
        sr = ServerResponse.successRS(Const.ProductEnum.UPDATE_SUCCESS.getCode(),Const.ProductEnum.UPDATE_SUCCESS.getDesc());
        return sr;
    }


    /*前端*/
    //    产品搜索及动态排序List
    @Override
    public ServerResponse<Product> listProduct(Integer productId, String keyWord, Integer pageNum, Integer pageSize, String orderBy) {
        if ((productId == null || productId < 0) && (keyWord == null || "".equals(keyWord))) {
            return ServerResponse.defeatedRS(Const.ProductEnum.PARAMETER_WRONG.getCode(), Const.ProductEnum.PARAMETER_WRONG.getDesc());
        }

//        分隔排序
        String[] split = new String[2];
        if (!"".equals(orderBy)) {
            split = orderBy.split("_");
        }

        String keys = "%" + keyWord + "%";

        PageHelper.startPage(pageNum,pageSize,split[0]+" "+split[1]);
        List<Product> list = productMapper.selectByIdOrName(productId, keys, split[0], split[1]);
        PageInfo pf = new PageInfo(list);

        return ServerResponse.successRS(pf);
    }

    //    产品详情
    @Override
    public ServerResponse<Product> detail(Integer productId, Integer is_new, Integer is_hot, Integer is_banner) {
        if (productId == null || productId <= 0) {
            return ServerResponse.defeatedRS(Const.ProductEnum.PARAMETER_WRONG.getCode(), Const.ProductEnum.PARAMETER_WRONG.getDesc());
        }

        Product product = productMapper.selectById(productId, is_new, is_hot, is_banner);

        if (product.getStatus() == 2) {
            return ServerResponse.defeatedRS(Const.ProductEnum.SOLD_OUT.getCode(), Const.ProductEnum.SOLD_OUT.getDesc());
        }

        ProductVO productVO = null;
        try {
            productVO = PoToVoUtil.ProductToProductVO(product);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ServerResponse.successRS(productVO);
    }

    //    获取产品分类
    @Override
    public ServerResponse<Product> topCategory(Integer sid) {
        if (sid == null || sid < 0) {
            return ServerResponse.defeatedRS(Const.ProductEnum.PARAMETER_WRONG.getCode(), Const.ProductEnum.PARAMETER_WRONG.getDesc());
        }
        //    根据商品分类id查询子分类
        List<Category> list = categoryMapper.getChildsByPd(sid);
        if (list == null) {
            return ServerResponse.defeatedRS(Const.ProductEnum.NOT_PID.getCode(), Const.ProductEnum.NOT_PID.getDesc());
        }
        return ServerResponse.successRS(list);
    }


}
