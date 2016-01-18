package com.test.j2se.classload;

import org.junit.Test;

import com.test.j2se.reflection.Jordon;

/**
 * 学习类加载器
 * @author Nick
 *
 */
public class Demo02 {

	@Test
	public void test1(){
		String classpath=System.getProperty("java.class.path");
		
		Jordon jordon =new Jordon();
		System.out.println(jordon.getClass().getClassLoader());
		//String类存在于java.lang.String,位于rt.jar包中,由BootstrapClassLoader加载，调用的是底层实现
		String a1="1222";
		System.out.println("string加载的classloader："+a1.getClass().getClassLoader());
	}
	
}
