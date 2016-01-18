package com.test.j2se;

import com.test.j2se.inf.Request;
import com.test.j2se.inf.TestInterface;
import com.test.j2se.memory.Person;

/**
 * 测试java泛型
 * @author Administrator
 *
 */
public class TestGen extends Person implements TestInterface{

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {

		Object obj=80;
		int i=(Integer)obj;
		
		
		System.out.println(TestInterface.p);
	}

	@Override
	public void execute(Request request) {
		// TODO Auto-generated method stub
		
	}

}
