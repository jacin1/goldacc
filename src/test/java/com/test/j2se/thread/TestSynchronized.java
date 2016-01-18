package com.test.j2se.thread;


import junit.framework.TestCase;

/**
 * 模拟实现:synchronized实现线程同步机制,
 * 重点了解 同步方法体和同步对象的区别
 * @author Administrator
 *
 */
public class TestSynchronized extends TestCase{

	private static int NUM_OF_THREAD=2000;
	private static float FST_AMOUNT=1000;
	static Thread[] threads=new Thread[NUM_OF_THREAD];
	
	/**
	 * 1.这里需要注意 匿名内部类访问外部定义的对象account,这个对象要定义成final的。
	 * 2.因为 2.1 方法返回后,方法里的本地变量就被释放,不可以再使用了,
	 *      2.2 实际上内部类里没有访问外部类的本地变量,而是做了一份拷贝(拷贝也是final的),内部类里使用的一直是这个拷贝。
	 */
	public static void main(String args[]){
		long currentTime=new java.util.Date().getTime();
		final Account account=new Account("11", FST_AMOUNT);
		for(int i=0;i<NUM_OF_THREAD;i++){
			threads[i]=new Thread(new Runnable() {
				public void run() {
					account.deposit(100.0f);
					account.withDraw(100.0f);
				}
			});
			threads[i].start();
		}
		
		//所有线程执行完成后,打印Account的金额
		//使用到了thread join(),等待线程运行结束后,再继续
		for(int i=0;i<NUM_OF_THREAD;i++){
			try {
				threads[i].join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		long currentTime22=new java.util.Date().getTime();
		System.out.println("spent time ="+(currentTime22-currentTime));
		System.out.println("finally :banlance is ="+account.getBalance());
		
	}
}

class Account{
	
	String name;
	
	float amount;

	public Account(String name, float amount) {
		this.name = name;
		this.amount = amount;
	}
	
	//模拟存钱操作
	public  void deposit(float amt){
		synchronized (this) {
			float tmp=amount;
			tmp+=amt;
			try {
				Thread.sleep(1);
			} catch (Exception e) {
			}
			amount=tmp;
		}
		
	}
	
	//模拟取钱操作
	public synchronized void withDraw(float amt) {
		float tmp = amount;
		tmp -= amt;
		try {
			Thread.sleep(1);
		} catch (Exception e) {
		}
		amount = tmp;
	}
	
	public float getBalance(){
		return amount;
	}
}
