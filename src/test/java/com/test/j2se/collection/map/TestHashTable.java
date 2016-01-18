package com.test.j2se.collection.map;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Properties;

import junit.framework.TestCase;

/**
 * 了解hashtable，properties类
 * @author Administrator
 *
 */
public class TestHashTable extends TestCase{

	
	public void test1(){
		Hashtable table=new Hashtable();
		table.put("11", "22");
		
	}
	
	//使用Properties类做资源文件的读取管理
	//Properties类继承HashTable，
	//存储配置文件
	public void test2() throws FileNotFoundException, IOException{
		Properties p1=new Properties();
		p1.setProperty("url", "localhost");
		p1.setProperty("port", "88");
		//存储到.properties,.xml文件中,使用相对路径，工程根目录下
//		p1.store(new FileOutputStream("1.properties"), "db配置");
//		p1.storeToXML(new FileOutputStream("1.xml"), "db配置");
		
	}
	
	//读取配置文件
	public void testLoad() throws FileNotFoundException, IOException{
		Properties p=new Properties();
		//根据文件读取,使用相对路径
//		p.load(new FileInputStream(new File("1.properties")));
//		System.out.println(p.getProperty("url"));
		
		//根据类相对路径读取配置文件
		//getResourceAsStream:参数如果不以 / 开头则默认是从类所在的包下取资源;否则 是从classpath下获取。
		//http://www.cnblogs.com/javayuer/archive/2011/01/02/1924192.html
//		p.load(TestHashTable.class.getResourceAsStream("1.properties"));
		System.out.println(p.getProperty("url"));
		
		
	}
	
}
