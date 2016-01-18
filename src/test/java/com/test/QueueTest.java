package com.test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import junit.framework.TestCase;

/**
 * 阻塞队列实现生产者和消费者模式
 * @author Administrator
 *
 */
public class QueueTest extends TestCase{

    public static void main(String[] args) {

        BlockingQueue queue=new LinkedBlockingDeque();
        Thread prod=new Thread(new Producer(queue));
        Thread  cus=new Thread(new Customer(queue));
        prod.start();
        cus.start();
    }

}

class Producer implements Runnable{

    private BlockingQueue queue;
    
    public Producer(BlockingQueue queue){
        this.queue=queue;
    }
    
    @Override
    public void run() {
        for(int i=0;i<10;i++){
            try {
                System.out.println("produced----"+i);
                queue.put(i);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Customer implements Runnable{

    private BlockingQueue queue;
    
    public Customer(BlockingQueue queue){
        this.queue=queue;
    }
    @Override
    public void run() {
        while(true){
            try {
                System.out.println("customer------"+queue.take());
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}
