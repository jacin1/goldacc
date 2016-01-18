package com.xsyi.service;

import java.util.Map;

import com.xsyi.model.User;

public interface UserService {

	public User getUserById(String userId);
	
	public void  insertUser(User user);
	
	public User selectUser(Map<String, String> param);
}
