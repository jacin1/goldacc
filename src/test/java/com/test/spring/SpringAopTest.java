package com.test.spring;

import static org.junit.Assert.*;

import java.lang.reflect.Proxy;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xsyi.base.inteceptor.LogInvocation;
import com.xsyi.service.UserService;
import com.xsyi.service.XiangQinInf;
import com.xsyi.service.impl.ZhangSanXQImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/spring.xml")
public class SpringAopTest extends AbstractJUnit4SpringContextTests {

	@Resource
	private UserService userService;
	
	private static final Logger log=Logger.getLogger(SpringAopTest.class);
	
	@Test
	public void test1() {
		log.info(userService);
	}
	
	//jdk动态代理测试
	@Test
	public void test2(){
		//先将被代理类实例化，也就是得到接口的一个实例对象
		XiangQinInf zs=new ZhangSanXQImpl();
		//得到一个代理类，同时为代理类绑定一个处理类
		XiangQinInf proxy=(XiangQinInf) Proxy.newProxyInstance(zs.getClass().getClassLoader(), 
					zs.getClass().getInterfaces(),
					new LogInvocation(zs));
		proxy.xiangqin();
	}

}
