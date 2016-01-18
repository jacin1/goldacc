package com.test.socket.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TestPost {
	public static void main(String args[]) {
		try {
			// Post请求格式如下:
			String postStr = "account=abc&password=123456789";
			int postStrLen = postStr.length();
			StringBuffer post = new StringBuffer("POST /login.php HTTP/1.1\r\n");
			post.append("Host: 127.0.0.1:8888\r\n");
			post.append("Accept: text/html\r\n");
			post.append("Connection: Close\r\n");
			post.append("Content-Length: " + postStrLen + "\r\n");
			post.append("Content-Type: application/x-www-form-urlencoded\r\n");// *
			post.append("\r\n");
			post.append(postStr);
			System.out.println(post);// post
			// HTTP POST其他请求头如下
			// post.append("User-Agent: Java/1.6.0_20\r\n");
			// post.append("Accept-Charset: gb2312,utf-8;q=0.7,*;q=0.7");
			Socket socket = new Socket("127.0.0.1", 8888);
			PrintWriter os = new PrintWriter(socket.getOutputStream());// 用于发送
			BufferedReader is = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));// 用于接收
			os.println(post);// 发送post请求
			os.flush();
			System.out.println("Server:");
			String tmp = "";
			while ((tmp = is.readLine()) != null) {
				System.out.println(tmp);
			}
			os.close();
			is.close();
			System.out.println("end");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
