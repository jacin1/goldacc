package com.test.j2se.thread.singleton;


import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class App{

	
	@Test
	public void test1(){
		Thread t1=new Thread(new MyThread());
		Thread t2=new Thread(new MyThread());
		t1.start();
		t2.start();
	}

	public static void main(String[] args) {
		Thread t1=new Thread(new MyThread());
		Thread t2=new Thread(new MyThread());
		t1.start();
		t2.start();
	}
}



class MyThread implements Runnable{

	@Override
	public void run() {
		try {
			Thread.sleep(1000);
			System.out.println(Thread.currentThread().getName()+"--->创建:"+TestSingtleon.getInstance());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
