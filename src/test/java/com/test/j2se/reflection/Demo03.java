package com.test.j2se.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

/**
 * 通过反射API动态的操作：构造器，方法，属性
 * @author Nick
 *
 */
public class Demo03 {
	
	Class c=null;
	
	@Before
	public void startUp() throws Exception{
		//通过Class对象生成对象,默认调用无参构造函数
		c=Class.forName("com.test.j2se.reflection.Jordon");
	}

	//通过反射API调用构造方法，创建对象
	@Test
	public void test1() throws Exception{
//		for(Field f:c.getDeclaredFields()){
//			System.out.println(f);
//		}
		Jordon j=(Jordon) c.newInstance();
		System.out.println(j.getAge());
	}
	
	//也可以通过Class对象获取构造方法,生成对象
	@Test
	public void test2() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Constructor jc=c.getDeclaredConstructor(String.class,int.class,String.class,String.class);
		Jordon j=(Jordon) jc.newInstance("12",2,"222",null);
		
		System.out.println(j.getAge());
	}
	
	//通过反射API调用普通方法
	@Test
	public void test3() throws Exception{
		Jordon j=(Jordon) c.newInstance();
		Method m=c.getDeclaredMethod("setName", String.class,String.class);
		m.invoke(j, "11","测试");
		System.out.println(j.getName());
	}
	
	//通过反射API操作属性
	@Test
	public void test4() throws Exception{
		Jordon j=(Jordon) c.newInstance();
		
		Field f=c.getDeclaredField("name");
		f.set(j, "23343等待");
		
		Field ageField=c.getDeclaredField("age");
		//对这个属性不做安全性检查,可以直接访问
		ageField.setAccessible(true);
		ageField.set(j, 22);
		System.out.println(ageField.get(j));
		System.out.println("name="+j.getName()+";age="+j.getAge());
	}
}
