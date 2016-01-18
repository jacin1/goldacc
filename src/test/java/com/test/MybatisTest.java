package com.test;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.xsyi.model.User;
import com.xsyi.service.UserService;
import com.xsyi.service.impl.UserServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:springmvc/spring.xml"})
public class MybatisTest {

	@Autowired
	private UserServiceImpl userService;
	
	private static final Logger log=Logger.getLogger(MybatisTest.class);
	
	@Test
	public void test1(){
		User user=userService.getUserById("002");
		log.info(JSON.toJSONStringWithDateFormat(user, "yyyy:MM:dd HH24:MI:SS"));
	}
	
}
