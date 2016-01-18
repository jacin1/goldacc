package com.test.j2se.vo;

public class TestAnimal {
	
	static{
		System.out.println("parent animal static ...");
	}

	public void run(){
		System.out.println("aniaml ...");
	}
	
	public TestAnimal(){
		System.out.println("TestAnimal constructor ... ");
	}
}

