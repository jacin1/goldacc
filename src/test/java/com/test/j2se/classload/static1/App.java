package com.test.j2se.classload.static1;

/**
 * http://www.cnblogs.com/alexlo/archive/2013/03/05/2944573.html
 * @author Nick
 * 被动引用情景:
 * 通过数组引用来引用类，不会触发此类的初始化
 *
 */
public class App {

	public static void main(String[] args) {
		//通过数组引用来引用类，不会触发此类的初始化
//		SuperClass[] c_list=new SuperClass[100];
		
		Singleton instance=Singleton.getInstance();
		System.out.println(instance.a);
		System.out.println(instance.b);
		
	}
}

/**
 * 类中的静态块static块也是顺序地从上到下执行的
 * 1.先去加载构造函数---->a=1，b=1
 * 2.接着执行静态语句,a没有赋值,保持原状;b被赋值了,b原先的值被覆盖,b=0;
 * @author Nick
 *
 */
class Singleton{
	
	public static Singleton instance=new Singleton();
	
	public static int a;
	
	public static int b=0;
	
	private Singleton(){
		super();
		a++;
		b++;
	}
	
	public static Singleton getInstance(){
		return instance;
	}
	
}