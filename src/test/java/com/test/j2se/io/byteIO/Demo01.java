package com.test.j2se.io.byteIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

/**
 * IO流测试，输入输出流，字节字符流
 * @author Nick
 *
 */
public class Demo01 {

	/**
	 * 1.拷贝文件,通过字节流拷贝，
	 * 2.当遇到大文件的拷贝时,以上这种方式可能会出现内存溢出的问题。
	 */
	@Test
	public void test1() throws Exception{
		String src="E:\\亚盟\\工作安排\\标准API接口需求\\VNC-Viewer-5.2.2-Windows-32bit.exe";
		String desc="F:/test/1.exe";
		InputStream ios=new FileInputStream(new File(src));
		OutputStream fos=new FileOutputStream(new File(desc));
		byte[] data=new byte[1024];
		int len;
		while(-1 != (len=ios.read(data))){
			fos.write(data, 0, len);
		}
		fos.flush();
		fos.close();
		ios.close();
	}
	
	//考虑大文件的拷贝
	@Test
	public void test2() throws Exception{
		Long start = System.currentTimeMillis();  
		FileUtils.copyFile(new File("F:/test/98_尚学堂_高淇_JAVA基础300集最全教程_编程基础和面向对象总复习.mp4"), new File("F:/test/2.MP4"));
		long end = System.currentTimeMillis();  
		System.out.println("读取文件文件花费：" + (end - start) + "毫秒");  
	}
	
}
