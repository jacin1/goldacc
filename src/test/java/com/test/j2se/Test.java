package com.test.j2se;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

import com.test.j2se.classload.Animal;
import com.test.model.Car;

import junit.framework.TestCase;

public class Test extends TestCase{

	public void test1(){
//		new PeopleImpl().execute();
		System.out.println((int)Math.sqrt(17));
	}
	
	/**
	 * 使用scanner类接受键盘的输入
	 */
	public void testScanner(){
		Scanner sc=new Scanner(System.in);
		String input=sc.next();
		System.out.println(input);
		
	}
	
	//局部变量必须初始化才可以使用
	public void test3(){
		int a;
//		System.out.println(a);
	}
	
	//numberAnimals为static的,每一次创建Animal实例时，静态成员变量numberAnimals都会不断递增，
	//并且所有实例都会访问同一个静态成员变量numberAnimals
	//类的静态成员变量与类的具体实例对象无关
	public void test4(){
		Animal a1=new Animal();
		Animal a2=new Animal();
		System.out.println(Animal.numberAnimals);
		
		
	}
	
	public void test5(){
		int x=3;
		System.out.println("x="+(++x)+",x1="+x);
		
		
	}
	
	private static int test(int x,int y){
		x=x+y;
		return x*y;
	}
	
	
	public static void main(String[] args) {
		for(File file: File.listRoots()){
			if(file.isDirectory()){
				System.out.println("Disk:\t"+file.getAbsolutePath());
			}
			
		}
//		List<E>
		Car car=test6();
		System.out.println(car.getName());
	}
	
	public static Car test6(){
		int x=1;
		Car car=new Car();
		try{
			car.setName("111");
		}catch(Exception e){
			
		}finally{
			x=4;
			car.setName("222");
		}
		return car;
	}
	
	public void test7(){
		String s1="-123";
		int a=Integer.parseInt(s1,16);
		System.out.println(a);
		
	}
}
