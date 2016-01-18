package com.test.j2se.classload.static1;

public class ChildClass extends SuperClass {

	static {
		System.out.println("sub class init.");
	}
}
