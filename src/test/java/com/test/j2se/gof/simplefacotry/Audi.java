package com.test.j2se.gof.simplefacotry;

public class Audi extends Byd implements Car {

	

	public Audi() {
//		super();
		
	}

	public Audi(String name){
		System.out.println("11111111");
	}
	
	@Override
	public void run() {
	}
}
