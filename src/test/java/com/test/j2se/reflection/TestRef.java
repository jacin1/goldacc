package com.test.j2se.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import junit.framework.TestCase;

public class TestRef extends TestCase{
	
	public void test1() throws ClassNotFoundException, NoSuchFieldException, SecurityException{
		Class c1= Class.forName("com.test.j2se.reflection.Aref");
		Class c2= Class.forName("com.test.j2se.reflection.Aref");
		
		System.out.println(c1 == c2);
		
		System.out.println(c1.getName());
		Method[] methods=c1.getMethods();
		
		System.out.println(methods.toString());
		
		//获取私有变量
		Field fiel2=c1.getDeclaredField("p2");
		System.out.println(fiel2.getName()+";type="+fiel2.getType());
		
		Field field1=c1.getDeclaredField("p2");
		System.out.println(field1.getType()+";decl="+field1.getModifiers());
		
	}
	
	//测试Field类的get方法
	public void testGet() throws Exception{
		//getDeclaredField:获取一个类的所有字段
		//getField:获取类的public字段
//		Field field=Aref.class.getDeclaredField("p");
		Field field=Aref.class.getField("p");
		Aref a =new Aref();
		
		int p=(Integer) field.get(a);
		
		System.out.println(p);
	}
	
	//反射测试
	@SuppressWarnings("all")
	public void test3() throws Exception{
		String classPath="com.test.j2se.reflection.Jordon";
		Class c=Class.forName(classPath);
		System.out.println("className:"+c.getName());
		Field f=c.getField("name");
		//获取属性信息 
		System.out.println(c.getDeclaredFields().length);
		
		//获取方法
		Method []methods=c.getDeclaredMethods();
		for(Method m:methods){
			System.out.println("方法:"+m);
		}
		
		//构造方法
		Constructor[] cs=c.getDeclaredConstructors();
		for(Constructor tc:cs){
			System.out.println(tc);
		}
		
		
	}
	
}
