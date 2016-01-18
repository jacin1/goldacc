package com.test.j2se.sort;

import java.util.Comparator;

public class Student {

	String name;
	int age;
	int score;
	
	public Student(){}
	
	public Student(String name, int age, int score) {
		super();
		this.name = name;
		this.age = age;
		this.score = score;
	}
	
	@Override
	public String toString(){
		return "name="+name+",age="+age+",score="+score;
	}
}
