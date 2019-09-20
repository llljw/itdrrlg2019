package com.itdr.mappers;

import com.itdr.pojo.Category;

import java.util.List;

public interface CategoryMapper {

    int deleteByPrimaryKey(Integer id);

    /*增加*/
    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    /*获取品类子节点*/
    List<Category> selectByPd(Integer parentId);

    /*修改品类名称*/
    int updateNameByPd(Category c);

    /*获取当前分类id及递归子节点*/
    List<Category> getChildsByPd(Integer parentId);

}