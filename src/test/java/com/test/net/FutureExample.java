package com.test.net;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 使用future实现异步回调,测试
 * @author Nick
 *
 */
public class FutureExample {

	public static void main(String[] args) throws InterruptedException {
		//使用ExecuteService线程池
		ExecutorService executor=Executors.newFixedThreadPool(3);
		//创建线程
		Runnable task1=new Runnable() {
			@Override
			public void run() {
				System.out.println("I am task1.................");
			}
		};
		
		Callable<Integer> task2=new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				System.out.println("I am task2.................");
				return new Integer(100);
			}
		};

		Future<?> f1=executor.submit(task1);
		Future<Integer> f2=executor.submit(task2);
		Thread.sleep(1000);
		System.out.println("task1 is completed ?"+f1.isDone());
		System.out.println("task2 is completed ?"+f2.isDone());
		
		
		//waiting task1 completed
		while (f1.isDone()) {
			System.out.println("task1 completed !");
			break;
		}
		
		//waiting task2 completed
		while(f2.isDone()){
			System.out.println("task2 completed !");
			break;
		}
		
		System.out.println("---------------------");
		executor.shutdown();
		
	}
}
