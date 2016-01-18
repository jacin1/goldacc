package com.test.socket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.log4j.Logger;

/**
 * 客户端结束服务器响应
 * @author Nick
 *
 */
public class TestSocketClient {
	
	private static final Logger logger=Logger.getLogger(TestSocketClient.class);

	public static String sendSynMsg(String ip,String port, String xml,int timeout) throws Exception{   
		 //为了简单起见，所有的异常都直接往外抛  
	     //与服务端建立连接  
	     Socket client = new Socket(ip, Integer.parseInt(port));  
	      //建立连接后就可以往服务端写数据了  
	     Writer writer = new OutputStreamWriter(client.getOutputStream());  
	      writer.write("Hello Server.");  
	      writer.write("eof123"); 
	      writer.flush();  
	     
	      String reslult=readFromServer(client);
	      
	      writer.close();  
	      
	      client.close();  
	      return reslult;
	}
	
	//一次性读取响应数据,对数据传输量小的可使用
	private static String readFromServer(Socket client) throws UnsupportedEncodingException, IOException{
		 //读取服务器端响应  
	      BufferedReader reader =new BufferedReader(new InputStreamReader(client.getInputStream(),"GBK"));  
	      char chars[] = new char[1024];  
	      StringBuffer sb = new StringBuffer();  
	      sb.append(new String(chars, 0,reader.read(chars))); 
	      reader.close();  
	      logger.info("from server: " + sb);  
	      return sb.toString();
	}
	
	//数据传输量较大,或考虑到网络延迟,一次性读取会发生读取数据不完整的问题。
	//可以通过服务端和客户端约定结束标识。来作为break的条件。
	private static String readFromServer2(Socket client) throws UnsupportedEncodingException, IOException{
		
		
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		String s1=sendSynMsg("172.56.3.86", "8888", "12222222", 10000);
		
		logger.info(s1);
	}
}
