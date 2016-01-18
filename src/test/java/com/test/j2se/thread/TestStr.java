package com.test.j2se.thread;

import org.junit.Test;

import junit.framework.TestCase;

/**
 * 1.java string常见面试题
 */
public class TestStr {
	
	/**
	 * 测试string对象地址存储方式
	 * 
	 */
	@Test
	public void test1(){
		String s1="a"+"b";
		String s2="ab";
		System.out.println(s1 == s2);
		//String类为不可变对象,
		String s3="a";
		//此处s3在 堆 中指向的地址已经发生了变化。
		s3+="b";
		System.out.println(s2 == s3);
		
	}
	
	//测试 equals ==
	@Test
	public  void testEqual(){
		Object o1=new String("Hello");
		Object o2=new String("Hello");
		System.out.println(o1 == o2);
		
		String str1="hello";
		String str2="hello";
		System.out.println(str1 == str2);
	}

}
