package com.test.j2se;

public  class TestAbstarct extends TestFinal{

	private int a;
	
	public final int b=0;
	
	public void f1(){
		System.out.println("subclass f1");
	}
	
	
	public void f3(final int t){
//		t=1;
	}
	
	public static void main(String[] args){
		TestAbstarct t1=new TestAbstarct();
		t1.f1();
		t1.f2();
	}
}

abstract class Student{
	public void show(){
		System.out.println("abstract show() 111");
	}
	public abstract void song();
}

class SmallStu extends Student{

	@Override
	public void song() {
		// TODO Auto-generated method stub
		
	}
	
}

