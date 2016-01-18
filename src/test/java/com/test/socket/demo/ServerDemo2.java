package com.test.socket.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 创建服务器，并启动，接受请求，打印 
 * @author Nick
 *
 */
public class ServerDemo2 {
	
	private ServerSocket server;
	private int port=8888; 

	public static void main(String[] args) {
		ServerDemo2 serverDemo=new ServerDemo2();
		serverDemo.start();
	}
	
	//启动方法
	public void start(){
		try {
			server=new ServerSocket(port);
			this.receive();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//接收请求
	private void receive(){
		StringBuilder sb=new StringBuilder();
		try {
			Socket client=server.accept();
			//接收到客户端的请求信息,客户端使用post请求时,通过读取字节来接收客户端信息
			byte[] data=new byte[20480];
			int len=client.getInputStream().read(data);
			String rece=new String(data, 0, len).trim();
			sb.append(rece);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(sb.toString().trim());
	}
	
	//停止
	public void stop(){
		
	}
}
