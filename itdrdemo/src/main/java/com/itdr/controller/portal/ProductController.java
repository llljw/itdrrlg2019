package com.itdr.controller.portal;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.Product;
import com.itdr.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


/**
 * User: Jwen
 * Date: 2019/9/12
 * Time: 8:50
 */
@RestController
@RequestMapping("/portal/product/")
public class ProductController {

    @Autowired
    ProductService productService;

    //    产品搜索及动态排序List
    @RequestMapping("list.do")
    public ServerResponse<Product> list(Integer productId, String keyWord,
                                        @RequestParam(value = "pageNum", required = false, defaultValue = "1")Integer pageNum,
                                        @RequestParam(value = "pageSize", required = false, defaultValue = "10")Integer pageSize,
                                        @RequestParam(value = "orderBy", required = false, defaultValue = "")String orderBy) {
        ServerResponse<Product> sr = productService.listProduct(productId,keyWord,pageNum,pageSize,orderBy);
        return sr;
    }

    //    获取一个产品详情
    @RequestMapping("detail.do")
    public ServerResponse<Product> detail(Integer productId){
        ServerResponse<Product> sr = productService.detail(productId);
        return sr;
    }

    //    获取最新、最热、banner产品详情
    @RequestMapping("detailNewOrHotOrBanner.do")
    public ServerResponse<Product> detailNewOrHotOrBanner(
                                          @RequestParam(value = "is_new", required = false, defaultValue = "0") Integer is_new,
                                          @RequestParam(value = "is_hot", required = false, defaultValue = "0") Integer is_hot,
                                          @RequestParam(value = "is_banner", required = false, defaultValue = "0") Integer is_banner) throws IOException {
        ServerResponse<Product> sr = productService.detailNewOrHotOrBanner(is_new, is_hot, is_banner);
        return sr;
    }

    //    获取产品分类
    @RequestMapping("topcategory.do")
    public ServerResponse<Product> topCategory(Integer sid) {
        ServerResponse<Product> sr = productService.topCategory(sid);
        return sr;
    }


}
