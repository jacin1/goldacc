package com.test;

public class StaticStuff {

	static int x=10;
	static{
		x+=5;
	}

	public static void main(String[] args) {
//		System.out.println(x);
//		
//		Long x1=new Long(7);
//		Long x2=new Long(7);
//		System.out.println(x1 == x2);
		int a1[]=new int[]{2,34};
		
	}
	
	static{
		x/=3;
	}
	public Object m(){
		Object o=new Float(3.144);
		Object[] oa=new Object[1];
		oa[0]=o;
		o=null;
		oa[0]=null;
		return o;
	}

}
