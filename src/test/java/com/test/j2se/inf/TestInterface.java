package com.test.j2se.inf;

public interface TestInterface <T>{

	public static int t=0;
	
	public static final int p=0;
	
	public void execute(Request<T> request);
}
