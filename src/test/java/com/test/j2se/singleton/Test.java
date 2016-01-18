package com.test.j2se.singleton;

import junit.framework.TestCase;

public class Test extends TestCase{

	public static void main(String[] args) {

	}

	public void test1(){
		Singleton01 instance=Singleton01.getInstance();
		Singleton01 instance02=Singleton01.getInstance();
		
		if(instance == instance02){
			System.out.println("同一个实例");
		}
		
	}
	
	public void test2(){
		Singleton s1=Singleton.getInstance();
		Singleton s2=Singleton.getInstance();
		if(s1 == s2){
			System.out.println("111111111111");
		}
		
	}
	
	//测试内部类实现的单例模式
	public void test3(){
		Singleton02 s1=Singleton02.getInstance();
		Singleton02 s2=Singleton02.getInstance();
		System.out.println(s1 == s2);
		
	}
}
