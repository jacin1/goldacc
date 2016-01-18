package com.xsyi.base.inteceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LogInvocation implements InvocationHandler {

	private Object target;
	
	public LogInvocation(Object target) {
		super();
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		//相亲前，先打扮
		System.out.println("张三相亲前，代理人给他打扮了打扮。");
		Object result=method.invoke(target, args);
		System.out.println(result);
		
		return result;
	}

}
