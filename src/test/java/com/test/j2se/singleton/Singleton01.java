package com.test.j2se.singleton;

/**
 * 单例模式类的编写
 * 1、单例类只能有一个实例。
　　2、单例类必须自己自己创建自己的唯一实例。
　　3、单例类必须给所有其他对象提供这一实例。
 要考虑多线程情况下,

 * @author Administrator
 *
 */
public class Singleton01 {
	
	private static Singleton01 instance=null;
	//通过构造方法限定为private,避免了类在外部被实例化,这样该类的实例只能通过getInstance()获取.
	private Singleton01(){}
	
	public static Singleton01 getInstance(){
		if(instance == null){
			instance=new Singleton01();
		}
		return instance;
	}
	
}
