package com.test.j2se;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import com.xsyi.model.Role;

public class TestList extends TestCase {

	public static void test1() {
		System.out.println("parent");
	}

	public static void test2(String str) {
		if (str == null | str.length() == 0) {
			System.out.println("empty");
		} else {
			System.out.println("not empty");
		}
	}

	/**
	 * 测试list 拷贝,深拷贝与浅拷贝 1.
	 */
	public void testCopy() {
		List list = new ArrayList();
		list.add("111");
		list.add("222");
		// List tmpList=list.c

	}

	public void changePri(int i) {
		i += 10;
	}

	public static void main(String[] args) {
		// String aaString=null;
		// test2(aaString);
		TestList testList = new TestList();
		int i = 5;
		testList.changePri(i);
		System.out.println(i);

		A a1 = new A();
		A a2 = new A();
		a1.name = "a1";
		a1 = a2;
		a2.name = "a2";
		System.out.println("a1.name=" + a1.name);
		System.out.println("a2.name=" + a2.name);

		// List list=new ArrayList();
		// list.add("111");
		// list.add("aaa");
		// list.add("bbb");
		// list.add("中国");
		// list.add(new Date());
		// list.add("123");
		// list.add(new Role());
		// list.set(2, "ddd");
		// // list.remove(new Date());
		// list.add(4, "999");
		// // list.remove(new Role());
		// for(Object str:list){
		// System.out.println(str);
		// }

	}

	public void changeA(A obj) {
		obj.name = "a";
	}
	
	/**
	 * 使用正则表达式进行 空格 分割
	 */
	public void testStrSpl(){
		String sq="i am a  student";
		String[] strArray=sq.split("\\s+");
		for(String s:strArray){
			System.out.println(s);
		}
		
	}

}

class A {
	public String name;
}
