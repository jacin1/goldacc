package com.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;

import com.xsyi.model.Role;

public class SerializableTest {

	public static final String filePath="e:/object.txt";
	//java序列化测试
	public static void main(String[] args) {
//		serialObjectToFile();
		deSerialibObj();
	}

	public static void serialObjectToFile(){
		ObjectOutputStream objStream=null;
		try {
			//创建一个objectoutputstream输出流
			objStream=new ObjectOutputStream(new FileOutputStream(filePath));
			Role role=new Role("1111","测试");
			//将role对象写入输出流
			objStream.writeObject(role);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(objStream != null){
				try {
					objStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("输出完成");
	}
	
	public static void deSerialibObj(){
		ObjectInputStream ois=null;
		try {
			ois=new ObjectInputStream(new FileInputStream(filePath));
			//读取出Role对象
			Role role=(Role) ois.readObject();
			System.out.println(role.getRoleid()+";"+role.getRolename());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
	}
}
