package com.test.j2se.reflection;

public class Aref {

	public int p=0;
	
	private int p2;
	
	public int getP(){
		
		return p;
	}
	
	public void setP(int p) {
		this.p = p;
	}

	public Aref(){};
//	
	public Aref(int p){
		this.p=p;
	}
	
	private void getPag(){
		System.out.println("1111111");
		
	}
}
