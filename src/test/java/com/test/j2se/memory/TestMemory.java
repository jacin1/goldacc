package com.test.j2se.memory;

import java.lang.ref.SoftReference;
import java.lang.reflect.Field;

import junit.framework.TestCase;


/**
 * 该类用来测试java对象的状态,java内存回收机制
 * 参考：http://blog.jobbole.com/37273/
 * @author Administrator
 *
 */
public class TestMemory extends TestCase {

	/**
	 * 1.java对象状态
	 */
	public void test1(){
		Person p1=new Person("kevin");
		
		Person p2=new Person("tony");
		
		Person p3=new Person("nick");
		
		p1.friend=p2;
		p3=p2;
		p2=null;
		
		System.out.println(p1);
		System.out.println(p2);
		System.out.println(p3);
	}

	/**
	 * java对象的4种引用
	 */
	public void test2(){
		//强引用 
		Person p1=new Person("r1");
		//软引用 
		SoftReference<Person> softReference=new SoftReference<Person>(new Person("r2"));
		
		//弱引用 
		
		
	}
	
	public  void test3(){
		Person p=new Person();
		int age=p.age;
		System.out.println(age);
		p.change(p);
		System.out.println(p.age);
	}
	
	public void test4(){
		Person p1=new Person("lisi");
		Person p2=new Person("maliu");
		p2=p1;
		changeData(p2);
		
		System.out.println("p1==="+p2.name+";;"+p2.age);
		System.out.println("p2==="+p2.name+";;"+p2.age);
	}
	
	public void test5(){
		
		
		
	}
	
	public void changeData(Person p ){
		p.age=30;
		p.name="wangwu";
	}
	
	public void testLoadClass() throws IllegalArgumentException, IllegalAccessException{
		try {
			Class<Person> personClass=(Class<Person>) Class.forName("com.test.j2se.memory.Person");
			Field field1=personClass.getDeclaredField("name");
			String nameValue= (String) field1.get(new Person("123"));
			System.out.println(nameValue);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
	
	public void testClassLoaderTree(){
		ClassLoader loader=TestMemory.class.getClassLoader();
//		String
		System.out.println(loader.toString());
		while(loader != null){
			System.out.println(loader.toString());
			loader=loader.getParent();
		}
		
		
		
	}
}
