package com.test.j2se.thread.threadlocal;

public class TestTwoThread {

	public static void main(String[] args) {
		Thread t1=new Thread(new A());
		Thread t2=new Thread(new A());
		t1.start();
		t2.start();
	}
}

class A implements Runnable{
	
	private int seqnum=0;
	
	@Override
	public void run() {
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		synchronized (this) {
			for(int i=0;i<10;i++){
				System.out.println("currentThread="+Thread.currentThread().getName()+",seqnum="+seqnum++);
			}
		}
	}
	
}
