package com.itdr.controller.portal;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.Category;
import com.itdr.pojo.Product;
import com.itdr.services.CategoryService;
import com.itdr.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * User: Jwen
 * Date: 2019/9/12
 * Time: 8:50
 */
@Controller
@ResponseBody
@RequestMapping("/product/")
public class ProductController {

    @Autowired
    ProductService productService;

//    产品搜索及动态排序List
    @GetMapping("list.do")
    public ServerResponse<Product> list(Product product){
        ServerResponse<Product> sr = productService.list(product);
        return sr;
    }

//    产品详情
    @GetMapping("detail.do")
    public ServerResponse<Product> detail(Product product){
        ServerResponse<Product> sr = productService.detail(product);
        return null;
    }

//    获取产品分类
    @GetMapping("topcategory.do")
    public ServerResponse<Category> topCategory(Integer sid){
        ServerResponse<Category> sr = productService.topCategory(sid);
        return sr;
    }


}
