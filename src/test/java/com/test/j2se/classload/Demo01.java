package com.test.j2se.classload;

import org.junit.Before;
import org.junit.Test;

/**
 * 学习JVM类加载机制,初始化过程
 * 类加载全过程：
            类的主动引用（一定会发生类的初始化）
     1. new 一个类的对象
     2. 调用类的静态成员(除了final常量)和静态方法
     3. 使用java.lang.reflect包的方法对类进行反射调用
     4. 当初始化一个类，如果父类没有初始化，则先初始化它的父类。
           类的被动引用(不会发生类的初始化)
      ** 当访问一个静态域时，只有真正声明这个域的类才会被初始化。
    	  通过子类引用父类的静态变量，不会导致子类初始化。
	  ** 通过数组定义类引用，不会触发此类的初始化。
	  ** 引用常量不会触发此类的初始化。(常量在编译阶段就存入调用类的常量池中了)。
 * @author Nick
 *
 */
public class Demo01 {
	
	static{
		System.out.println("静态初始化Demo1");
	}
	
	@Before
	public void startUp(){
//		A a=new A();
	}

	@Test
	public void test1(){
//		System.out.println("11");
//		A a =new A();
		System.out.println(A_Father.MAX);
	}
}

class A extends A_Father{
	public static int width=100;
	
	public static final int MAX=100;
	static{
		System.out.println("静态初始化类A");
		width=300;
	}
	
	public A(){
		System.out.println("创建A类的对象");
	}
	
}

class A_Father{
	
	public static final int MAX=100;
	
	static{
		System.out.println("静态初始化A_Father");
	}
	
	public A_Father(){
		System.out.println("创建A_Father类的对象");
	}
	
}
