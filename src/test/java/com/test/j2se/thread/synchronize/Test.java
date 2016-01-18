package com.test.j2se.thread.synchronize;

import com.test.j2se.thread.TestSynchronized;

/**
 * synchronized的学习：
 * http://www.warting.com/program/201112/39394.html
 * 
 * @author Administrator
 *
 */
public class Test implements Runnable{

	String name;
	
	public static void main(String[] args) {
		Test t=new Test();
		Thread t1=new Thread(t);
		Thread t2=new Thread(t);
		t1.start();
		t2.start();
	}
	
	public Test(){}
	
	public Test(String name){
		this.name=name;
	}

	@Override
	public void run() {
		try {
			testSynch();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void testSynch() throws InterruptedException{
		synchronized (this) {
			for(int i=0;i<10;i++){
				Thread.sleep(1000);
				System.out.println(Thread.currentThread().getName());
			}
			
		}
		
	}
	
}

