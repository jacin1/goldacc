package com.test.j2se.classload;

import com.test.j2se.TestAbstarct;
import com.test.j2se.vo.Bird;

import junit.framework.TestCase;

/**
 * 1.了解java 编译时,运行时,构建时  三个概念
 * @author Administrator
 *
 */
public class TestLoading extends TestCase{

	static final int number1=5;
	static final int number2=3;
	
	int number3=3;
	static int number4=4;
	
	public void test1(){
		//编译期计算,生产的class中,pro1已经计算好
		int pro1=number1 * number2;
		//运行期调用
		int pro2=number3 * number4;
	}
	
	public void test2(){
		Bird bird=new Bird();
		bird.name="111";
		bird.run(bird);
		System.out.println("name ="+bird.name);
		TestAbstarct abstarct=new TestAbstarct();
		abstarct.f1();
//		ClassLoader;
	}
	
}
