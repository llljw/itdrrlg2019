package com.itdr.controller.backend;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.Category;
import com.itdr.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User: Jwen
 * Date: 2019/9/9
 * Time: 8:23
 */
@Controller
@RequestMapping("/manage/category/")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /*获取品类子节点*/
    @RequestMapping("get_category.do")
    @ResponseBody
    public ServerResponse get_category(Integer parentId) {
        ServerResponse sr = categoryService.selectByPd(parentId);
        return sr;
    }

    /*增加节点*/
    @RequestMapping("add_category.do")
    @ResponseBody
    public ServerResponse add_category(Category c) {
        ServerResponse sr = categoryService.addOne(c);
        return sr;
    }

    /*修改品类名字*/
    @RequestMapping("set_category_name.do")
    @ResponseBody
    public ServerResponse set_category_name(Category c) {
        ServerResponse sr = categoryService.updateNameByPd(c);
        return sr;
    }

    /*获取当前分类id及递归子节点*/
    @RequestMapping("get_deep_category.do")
    @ResponseBody
    public ServerResponse get_deep_category(Integer parentId){
        ServerResponse sr = categoryService.getDeepCategory(parentId);
        return sr;
    }
}
