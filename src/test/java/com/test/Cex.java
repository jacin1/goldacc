package com.test;

public class Cex {

	static int i;
	public static void main(String args[]){
		System.out.println(i);
//		Base b=new Base();
//		Sub s=(Sub) b;
		Base b=new Sub();
	}
}

class Base{}
class Sub extends Base{}
