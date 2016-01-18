package com.test.mongodb;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xsyi.mongo.model.MongoUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/spring.xml")
public class Demo02 extends AbstractJUnit4SpringContextTests {
	
	@Resource
	private MongoTemplate mongoTemplate;
	
	//测试获取mongoTemplate对象是否成功
	@Test
	public void test1(){
		System.out.println(mongoTemplate);
	}
	
	
	@Test
	public void test2(){
		MongoUser user=new MongoUser("mongoTemplate测试插入","sdddd",23);
		mongoTemplate.insert(user);
		
	}
}


	