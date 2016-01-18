package com.test.rabbitmq;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import com.rabbitmq.client.QueueingConsumer;

/**
 * rabbitmq：接收消息
 * @author Administrator
 *
 */
public class RevevMsg {
	private final static String QUEUE_NAME = "hello2";

	public static void main(String[] args)  {
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    Connection connection = null;
	    Channel channel =null;

	    
	    try{
	    	connection=factory.newConnection();
	    	channel=connection.createChannel();
	    	channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	    	channel.basicQos(1);
	    	
//		    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
		    QueueingConsumer consumer = new QueueingConsumer(channel);
		    channel.basicConsume(QUEUE_NAME, false, consumer);
		    while(true){
		    	QueueingConsumer.Delivery delivery = consumer.nextDelivery();
		    	String message = new String(delivery.getBody());
		    	System.out.println(" [x] Received '" + message + "'");
		    	doWork(message);
		    	//确认ack接收到消息后,消息从队列中删除
		    	channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
		    }
	    }catch(Exception e){
	    	
	    }finally{
//	    	try {
//				channel.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//	    	try {
//				connection.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
	    }
	    
	    
	    
	}
	
	private static void doWork(String task) throws Exception{
		for (char ch : task.toCharArray())  
        {  
            if (ch == '.')  
                Thread.sleep(1000);  
        }
	}

}
