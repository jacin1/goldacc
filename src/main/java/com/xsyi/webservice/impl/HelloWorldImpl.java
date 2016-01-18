package com.xsyi.webservice.impl;

import javax.jws.WebService;

import com.xsyi.webservice.IHelloWorld;

@WebService(endpointInterface="com.xsyi.webservice.IHelloWorld",serviceName="HelloWorld")
public class HelloWorldImpl implements IHelloWorld {

	
	public String sayHello(String name) {
		return "Hello "+name;
	}

}
