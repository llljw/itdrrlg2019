package com.itdr.mappers;

import com.itdr.pojo.Users;

import java.util.List;

public interface UsersMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Users record);

    int insertSelective(Users record);

    Users selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    /*登录*/
    Users selectByNameAndPassword(String username,String password);

    /*用户列表*/
    List<Users> selectAll();
}