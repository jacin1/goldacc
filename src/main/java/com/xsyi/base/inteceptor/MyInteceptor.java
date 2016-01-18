package com.xsyi.base.inteceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.cglib.transform.impl.AbstractInterceptFieldCallback;
import org.springframework.web.bind.annotation.support.HandlerMethodInvoker;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;
import org.springframework.web.servlet.mvc.multiaction.InternalPathMethodNameResolver;
import org.springframework.web.servlet.mvc.multiaction.MethodNameResolver;

public class MyInteceptor implements HandlerInterceptor {

	private static final Logger log=Logger.getLogger(MyInteceptor.class);
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		log.info("afterCompletion");
	}

	//可以修改转向的视图名称
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2, ModelAndView model) throws Exception {
		log.info("postHandle");
//		arg3.setViewName("index");
		
	}

	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object handler) throws Exception {
		log.info("preHandle");
		HandlerMethod handlerMethod=(HandlerMethod) handler;
		String methodName=handlerMethod.getMethod().getName();
		log.info("method name :="+methodName);
		MethodNameResolver methodNameResolver=new InternalPathMethodNameResolver();
		log.info(methodNameResolver.getHandlerMethodName(arg0));
		
		
		return true;
	}

}
