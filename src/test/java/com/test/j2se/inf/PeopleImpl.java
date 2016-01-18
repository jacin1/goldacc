package com.test.j2se.inf;

public  class PeopleImpl implements TestInterface {

	@Override
	public void execute(Request request) {
		
	}


	public static void main(String[] args) {
		TestInterface inf=new PeopleImpl();
		T1 instance=new T1();
		if(instance instanceof PeopleImpl){
			System.out.println("11111");
		}
		
	}
	
}

class T1 extends PeopleImpl{
	
}