package com.xsyi.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xsyi.dao.UserMapper;
import com.xsyi.model.User;
import com.xsyi.service.UserService;

@Service(value="userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	public User getUserById(String userId) {
		return userMapper.selectByPrimaryKey(userId);
	}

	@Override
	public void insertUser(User user) {
		userMapper.insert2User(user);
	}

	@Override
	public User selectUser(Map<String, String> param) {
		return userMapper.selectUser(param);
	}

}
