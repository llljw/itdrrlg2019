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
 * Date: 2019/9/12
 * Time: 11:00
 */
@Controller
@ResponseBody
@RequestMapping("/manage/category/")
public class CategoryManageController {

    @Autowired
    private CategoryService categoryService;

    /*获取品类子节点*/
    @RequestMapping("get_category.do")
    public ServerResponse getCategory(Integer parentId) {
        ServerResponse sr = categoryService.selectByPd(parentId);
        return sr;
    }

    /*增加节点*/
    @RequestMapping("add_category.do")
    public ServerResponse addCategory(Category c) {
        ServerResponse sr = categoryService.addOne(c);
        return sr;
    }

    /*修改品类名字*/
    @RequestMapping("set_category_name.do")
    public ServerResponse setCategoryName(Category c) {
        ServerResponse sr = categoryService.updateNameByPd(c);
        return sr;
    }

    /*获取当前分类id及递归子节点*/
    @RequestMapping("get_deep_category.do")
    public ServerResponse get_deep_category(Integer parentId){
        ServerResponse sr = categoryService.get_deep_category(parentId);
        return sr;
    }
}
