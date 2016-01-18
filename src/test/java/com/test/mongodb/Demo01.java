package com.test.mongodb;

import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class Demo01 {
	
	MongoClient client;
	
	DB testDB;

	@Before
	public void startUp() throws Exception{
		 // 连接到 mongodb 服务
		client = new MongoClient( "localhost" , 27017 );
		//连接到数据库
		testDB=client.getDB("test");
		boolean isAuth=testDB.authenticate("xsyi", "xsyi123".toCharArray());
	}
	
	//插入文档
	@Test
	public void test1(){
		DBCollection collection=testDB.getCollection("user");
		BasicDBObject doc=new BasicDBObject("name", "3333").append("age", 222).append("title", "插入文档测试");
		collection.insert(doc);
		
	}
	
}
