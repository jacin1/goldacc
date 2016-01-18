package com.test.j2se.vo;

public class Bird extends TestAnimal {
	
	public String name;
	
	String color;
	
	static{
		System.out.println("Bird static ... ");
	}
	
	public void run(Bird bird) {
		super.run();
		bird.name="bird";
		System.out.println("bird ...");
	}
	
	public Bird(){
		super();
		System.out.println("bird constructor ... ");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	
}
