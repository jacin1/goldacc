package com.test.webservcie;

import javax.xml.ws.Endpoint;

//import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xsyi.webservice.IHelloWorld;
import com.xsyi.webservice.impl.HelloWorldImpl;

public class WebServiceApp {

	public static String address="http://localhost:8080/s3mDemo/helloworld";
	public static void main(String[] args) {
		HelloWorldImpl impll=new HelloWorldImpl();
		
		Endpoint.publish(address, impll);
	}
	
//	@Test
//	public void test1(){
//		JaxWsProxyFactoryBean factory=new JaxWsProxyFactoryBean();
//		factory.setServiceClass(IHelloWorld.class);
//		factory.setAddress(address);
//		IHelloWorld client=(IHelloWorld) factory.create();
//		String result=client.sayHello("123掌声呢ddd");
//		System.out.println(result);
//	}
	
	//webservice客户端测试,集成spring进行调用
	@Test
	public void test2(){
		//加载读取配置文件
		ApplicationContext context=new ClassPathXmlApplicationContext("webservice/webservice-client.xml");
		IHelloWorld client=(IHelloWorld) context.getBean("helloClient");
		String result=client.sayHello("测试");
		System.out.println(result);
		
	}
}
