package com.test.socket;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketServer {

	public static void main(String[] args) throws UnknownHostException, IOException {

		 //为了简单起见，所有的异常信息都往外抛  
	      int port = 8899;  
	      //定义一个ServerSocket监听在端口8899上  
	      ServerSocket server = new ServerSocket(port);  
	      //server尝试接收其他Socket的连接请求，server的accept方法是阻塞式的  
	      Socket socket = server.accept();  
	      //跟客户端建立好连接之后，我们就可以获取socket的InputStream，并从中读取客户端发过来的信息了。  
	      Reader reader = new InputStreamReader(socket.getInputStream());  
	      char chars[] = new char[64];  
	      int len;  
	      StringBuilder sb = new StringBuilder();  
	      String temp;  
	      int index;  
	      while ((len=reader.read(chars)) != -1) {  
	         temp = new String(chars, 0, len);  
	         if ((index = temp.indexOf("eof")) != -1) {//遇到eof时就结束接收  
	            sb.append(temp.substring(0, index));  
	            break;  
	         }  
	         sb.append(temp);  
	      }  
	      System.out.println("from client: " + sb);  
	      //读完后写一句  
	      Writer writer = new OutputStreamWriter(socket.getOutputStream());  
	      writer.write("Hello Client.");  
	      writer.flush();  
	      writer.close();  
	      reader.close();  
	      socket.close();  
	      server.close();  
	}

}
