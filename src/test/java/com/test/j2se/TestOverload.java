package com.test.j2se;

import com.test.j2se.sort.TestBubbleSort;

/**
 * 测试重载和重写
 * 1.重载方法是在编译时起作用,静态绑定，意味在JVM编译时决定调用的方法。
 *   重写在运行时起作用，JVM是在运行时决定调用的类或方法，动态绑定是多态的基础。
 *   
 * @author Administrator
 *
 */

public class TestOverload {
	
	public void testOverrid(String obj){
		System.out.println("parent  :"+obj);
	}

	public static void main(String[] args) {

//		Object o1=new TestOverload();
//		Object o2=new TestOverload();
//		
//		if(o1.equals(o2)){
//			System.out.println("o1 == o2");
//		}else{
//			System.out.println("o1 != o2");
//		}
//		
//		TestOverload load1=new TestOverload();
//		TestOverload load2=new TestOverload();
//		System.out.println(load1.equals(load2));
		
		TestOverload parent=new TestBubbleSort();
		parent.testOverrid("111");
		TestBubbleSort child=new TestBubbleSort();
		child.testOverrid("222");
		TestOverload parent2=new TestOverload();
		parent2.testOverrid("");
		
		
	}
	
	@Override
	public boolean equals(Object load){
		System.out.println("TestOverload equals method reached");
		return true;
	}
	
	

}
