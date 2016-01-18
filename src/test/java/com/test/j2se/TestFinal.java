package com.test.j2se;

/**
 * 1.了解final关键字修饰方法,子类不可以继承
 * 2.了解类名.class ,了解获得Class实例的三种方式,http://blog.csdn.net/cto_51/article/details/8962357
 * 1). 利用对象调用getClass()方法获取该对象的Class实例
   2). 使用Class的静态方法forName()，用类的名字获取一个Class实例
   3). 运用.calss的方式获取Class实例，对基本数据类型的封装类，还可以采用.TYPE来获取对应的基本数据类型的Class实例。
 *   
 * @author nick
 *  2014-8-27
 *
 */
public  class TestFinal{

	public void f1(){
		System.out.println("f1  paretn");
	}
	
	public final void f2(){
		System.out.println("final f2");
	}
	
	public static void main(String[] args) {
		//1.
		System.out.println("before new point");
		new Point();
		System.out.println("after load point");
		try {
			Class<Line> line=(Class<Line>) Class.forName("com.test.j2se.Line");
			
		} catch (ClassNotFoundException e) {
			System.out.println("loading line error");
		}
		System.out.println("after loading line");
		
		try {
			Class point= Class.forName("com.test.j2se.Point");
			System.out.println(point.getName());
			
			Point p=(Point) point.newInstance();
			p.output();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//3.  A.class
		Class c3=Point.class;
		System.out.println(c3.getName());
		try {
			((Point)c3.newInstance()).output();
		} catch (Exception e) {
		} 
		
	} 
	
	
}


class Point{
	static {
		System.out.println("loading point!");
	}
	void output(){
		System.out.println("x="+x+";y="+y);
	}
	Integer x,y;
	
}

class Line{
	static {
		System.out.println("loading line!");
	}
}
