package com.test.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="/spring.xml")
public class SpringMvcTest extends AbstractJUnit4SpringContextTests {
	
	@Test
	public void test1(){
//		XmlWebApplicationContext
//		DispatcherServlet
//		HandlerAdapter
//		AnnotationMethodHandlerAdapter
	}

}
