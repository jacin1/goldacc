package com.test.redis;

import org.junit.Before;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import junit.framework.TestCase;

public class JRedisTest01 extends TestCase{

	JedisPool pool;
	Jedis js;
	
	@Before
	public void setUp(){
		js = new Jedis("192.168.1.106");
		
	}
	
	public void test1(){
		String key="name";
		
		js.del(key);
		
		//存数据 
		js.set(key, "444");
		
		//取数据
		String value=js.get(key);
		
		System.out.println(value);
	}
}
