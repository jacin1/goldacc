package com.test.j2se.singleton;

/**
 * 1.单例设计模式,使用静态内部类完成。
 *   延时加载
 *   高效
 *   考虑到多线程环境
 * @author Nick
 *
 */
public class Singleton02 {

	public static void main(String[] args) {
		for(int i=0;i<10;i++){
			Thread t=new Thread(new Runnable() {
				@Override
				public void run() {
					Singleton02 s=Singleton02.getInstance();
					System.out.println(s);
				}
			});
			t.start();
		}
		
		
	}
	
	//1.私有的构造方法
	private Singleton02(){}
	
	//2.静态内部类
	private static  class InstanceHolder{
		private static final Singleton02  instance=new Singleton02();
	}
	
	//没有同步,调用效率高
	public static Singleton02 getInstance(){
		return InstanceHolder.instance;
	}
}
