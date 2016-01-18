package com.test.rabbitmq;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import junit.framework.TestCase;

/**
 * 测试rabbitmq 消息队列框架
 * @author nick
 *
 */
public class SendRabbitMQ2 extends TestCase {

	private static final String QUEUE_NAME="hello2";
	
	public static void main(String[] args) throws Exception {
		//1.The connection abstracts the socket connection, 
		//and takes care of protocol version negotiation and authentication and so on for us. 
		//Here we connect to a broker on the local machine - hence the localhost.
		//If we wanted to connect to a broker on a different machine 
		//we'd simply specify its name or IP address here.
		ConnectionFactory factory=new ConnectionFactory();
		factory.setHost("localhost");
		factory.setPort(5672);
		
		Connection connection=factory.newConnection();
		Channel channel=connection.createChannel();
		//2.Next we create a channel, which is where most of the API for getting things done resides.
		//To send, we must declare a queue for us to send to; then we can publish a message to the queue:
		
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//		String message="Hello world1234";
		
		//发送10条消息,依次在消息后面加上1-10个点
		for(int i=0;i<20;i++){
			String dtos="";
			for(int j=0;j<i;j++){
				dtos+=".";
			}
			String message="hello world" + dtos +dtos.length();
			
			channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");  
			
		}
		
		//close channel
		channel.close();
		connection.close();
		
	}
	
	public void test1(){
		
	}

}
