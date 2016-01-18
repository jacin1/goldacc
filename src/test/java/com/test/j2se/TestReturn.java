package com.test.j2se;

import junit.framework.TestCase;

public class TestReturn extends TestCase{

	@SuppressWarnings("finally")
	public int test1(){
		int a=10;
		try{
			return a;
		}catch(Exception e){
			
		}finally{
			a=20;
			return a;
		}
		
	}
	
	public void test2(){
		int a=test1();
		System.out.println(a);
	}
	
	public static void main(String[] args) {
	}
}
