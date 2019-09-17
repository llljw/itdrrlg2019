package com.itdr.services.Impl;

import com.itdr.common.Const;
import com.itdr.common.ServerResponse;
import com.itdr.mappers.CategoryMapper;
import com.itdr.pojo.Category;
import com.itdr.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Jwen
 * Date: 2019/9/9
 * Time: 8:24
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /*获取品类子节点*/
    @Override
    public ServerResponse selectByPd(Integer parentId) {
        ServerResponse sr = null;
        List<Category> li = categoryMapper.selectByPd(parentId);
        if (li == null) {
            sr = ServerResponse.defeatedRS(1,Const.CATEGORY_PID_NO_MSG);
            return sr;
        }
        sr = ServerResponse.successRS(li);
        return sr;
    }

    /*增加*/
    @Override
    public ServerResponse addOne(Category c) {
        ServerResponse sr = null;
        int i = categoryMapper.insert(c);
        if (i <= 0) {
            sr = ServerResponse.defeatedRS(1,Const.CATEGOR_ADD_FAILED_MSG);
            return sr;
        }
        sr = ServerResponse.successRS(0,Const.CATEGOR_ADD_MSG);
        return sr;
    }

    /*修改品类名字*/
    @Override
    public ServerResponse updateNameByPd(Category c) {
        ServerResponse sr = null;
        int i = categoryMapper.updateNameByPd(c);
        if (i <= 0) {
            sr = ServerResponse.defeatedRS(1,Const.CATEGOR_UPDATEPNAME_FAILED_MSG);
            return sr;
        }
        sr = ServerResponse.successRS(0,Const.CATEGOR_UPDATEPNAME_MSG);
        return sr;
    }

    /*获取当前分类id及递归子节点*/
    @Override
    public ServerResponse get_deep_category(Integer parentId) {
        ServerResponse sr = null;
        if (parentId == null) {
            sr = ServerResponse.defeatedRS(1,Const.ORDER_PARAMETER_MSG);
            return sr;
        }
        List<Integer> li = new ArrayList<>();
        getDeepChilds(parentId, li);
        sr = ServerResponse.successRS(li);
        return sr;
    }

    private void getDeepChilds(Integer parentId, List<Integer> li) {
        List<Category> list = categoryMapper.getChildsByPd(parentId);
        if (list != null && list.size() != 0) {
            for (Category category : list) {
                li.add(category.getId());
                getDeepChilds(category.getId(), li);
            }
        }

    }

}
