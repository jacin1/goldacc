package com.test.j2se.thread;

public class Rabbit extends Thread{

	@Override
	public void run() {
		System.out.println("thread run();");
	}

//	@Override
//	public synchronized void start() {
//		System.out.println("thread start;");
//	}

	
}
