package com.xsyi.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xsyi.model.User;

@Repository
public interface UserMapper {
    int deleteByPrimaryKey(String userid);

    int insert2User(User record);

    int insertSelective(User record);

//  @Select("select * from t_user u where u.userId=#{userid}")
    User selectByPrimaryKey(String userid);
    
    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    //使用一对一的方式,需要执行2次查询
    User selectUser(Map<String, String> param);
}