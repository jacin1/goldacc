package com.test.mybatis;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xsyi.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/springmvc/spring.xml"})
public class TestMybatis extends AbstractJUnit4SpringContextTests {

	private static final Logger log=LoggerFactory.getLogger(TestMybatis.class);
	
	@Resource
	private UserService userService;
	
	@Test
	public void test1(){
//		MapperFactoryBean
		log.info("11");
		Map<String, String> param=new HashMap<String, String>();
		param.put("userid", "1");
		userService.selectUser(param);
	}
	
}
