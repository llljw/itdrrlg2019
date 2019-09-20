package com.itdr.controller.portal;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.Category;
import com.itdr.pojo.Product;
import com.itdr.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


/**
 * User: Jwen
 * Date: 2019/9/12
 * Time: 8:50
 */
@RestController
@RequestMapping("/product/")
public class ProductController {

    @Autowired
    ProductService productService;

    //    产品搜索及动态排序List
    @GetMapping("list.do")
    public ServerResponse<Product> list(Integer productId, String keyWord,
                                        @RequestParam(value = "pageNum", required = false, defaultValue = "1")Integer pageNum,
                                        @RequestParam(value = "pageSize", required = false, defaultValue = "10")Integer pageSize,
                                        @RequestParam(value = "orderBy", required = false, defaultValue = "")String orderBy) {
        ServerResponse<Product> sr = productService.listProduct(productId,keyWord,pageNum,pageSize,orderBy);
        return sr;
    }

    //    产品详情
    @GetMapping("detail.do")
    public ServerResponse<Product> detail(Integer productId,
                                          @RequestParam(value = "is_new", required = false, defaultValue = "0") Integer is_new,
                                          @RequestParam(value = "is_new", required = false, defaultValue = "0") Integer is_hot,
                                          @RequestParam(value = "is_new", required = false, defaultValue = "0") Integer is_banner) throws IOException {
        ServerResponse<Product> sr = productService.detail(productId, is_new, is_hot, is_banner);
        return sr;
    }

    //    获取产品分类
    @GetMapping("topcategory.do")
    public ServerResponse<Product> topCategory(Integer sid) {
        ServerResponse<Product> sr = productService.topCategory(sid);
        return sr;
    }


}
