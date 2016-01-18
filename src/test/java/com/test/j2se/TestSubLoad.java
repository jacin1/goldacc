package com.test.j2se;

/**
 * 父类和子类的加载顺序,考虑到静态体
 * @author Administrator
 *
 */
public class TestSubLoad extends superC{

	static{
		System.out.println("child load");
	}
	public TestSubLoad(){
		System.out.println("testsubload construct;");
	}
	
	public static void main(String[] args) {
		new TestSubLoad();
	}

}

class superC{
	static{
		System.out.println("superc load");
	}
	
	
	public superC(){
		this("123");
		System.out.println("superc construct;");
	}
	
	public superC(String name){
		System.out.println("1111111");
	}
}