package com.test.j2se.thread;

import java.lang.reflect.Constructor;

public class TestThread {

	public static void main(String[] args) throws Exception {

		Rabbit thread=new Rabbit();
		System.out.println(thread instanceof Thread);
//		thread.start();
		
//		Class<Rabbit> tmp=Rabbit.class;
//		System.out.println(tmp);
//		Constructor[] cons=Class.forName("com.test.j2se.thread.Rabbit").getConstructors();
//		System.out.println(cons);
		
		
	}
	
	public class B{
		
	}

}

 class A{
	
}