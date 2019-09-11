package com.itdr.services;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.Category;

/**
 * User: Jwen
 * Date: 2019/9/9
 * Time: 8:24
 */
public interface CategoryService {

    /*获取品类子节点*/
    ServerResponse selectByPd(Integer parent_id);

    /*增加节点*/
    ServerResponse addOne(Category c);

    /*修改品类名字*/
    ServerResponse updateNameByPd(Category c);

    /*获取当前分类id及递归子节点*/
    ServerResponse getDeepCategory(Integer parentId);
}
