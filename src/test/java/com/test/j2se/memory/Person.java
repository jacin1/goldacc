package com.test.j2se.memory;

import java.io.Serializable;

public class Person implements Serializable{

	private static final long serialVersionUID = 1L;

	String name;
	
	Person friend;
	
	public static final int p=0;
	
	int age=20;
	
	public static void testStatic(){
		System.out.println("static method initing");
	}
	
	public Person(){};
	
	public Person(String name){
		super();
		this.name=name;
	}
	
	public void change(Person person){
		person=new Person();
		person.age=30;
	}

}
