package com.itdr.controller.backend;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.Product;
import com.itdr.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User: Jwen
 * Date: 2019/9/6
 * Time: 19:30
 */
@RequestMapping("/manage/product/")
@Controller
public class ProductController {

    /*调用业务层*/
    @Autowired
    private ProductService productService;

    /*商品列表*/
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse list() {
        ServerResponse sr = productService.selectAll();
        return sr;
    }

    /*根据id或名称商品搜索*/
    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse search(String productName, Integer productId) {
        ServerResponse sr = productService.selectOne(productName, productId);
        return sr;
    }

    /*商品上下架*/
    @RequestMapping("set_sale_status.do")
    @ResponseBody
    public ServerResponse set_sale_status(Product p) {
        ServerResponse sr = productService.selectByID_sale_status(p);
        return sr;
    }

    /*新增产品*/
    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse save(Product p) {
        ServerResponse sr = null;
        if (p.getId() == null) {
            sr = productService.insertOne(p);
            return sr;
        }
        sr = productService.updateOne(p);
        return sr;
    }


}
