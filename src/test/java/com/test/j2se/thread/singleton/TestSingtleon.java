package com.test.j2se.thread.singleton;

/**
 * 单例创建模式
 *  懒汉式
 *   1.私有的构造函数，
 *   2.私有的静态成员变量
 *   3.对外提供的静态方法。确保对象存在
 *   4.考虑到多线程环境下的线程安全。使用DOUBLE-CHECKIKNG 的方式，提高效率，减少线程锁，等待的时间。
 * @author Administrator
 *
 */
public class TestSingtleon {
	private TestSingtleon(){}
	
	private static TestSingtleon instance=null;
	
	public static TestSingtleon getInstance(){
		//这里的对象=null,是为了提高效率
		if(null == instance){
			synchronized (TestSingtleon.class) {
				//synchronized：保证安全
				if(null == instance){
					instance=new TestSingtleon();
				}
			}
		}
		
		return instance;
	}
	
}
