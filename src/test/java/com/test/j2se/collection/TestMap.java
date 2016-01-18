package com.test.j2se.collection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import junit.framework.TestCase;

public class TestMap extends TestCase{

	public void test1(){
		System.out.println("11");
		Map m1=new HashMap();
		m1.put("4", "222");
		System.out.println(m1.put("4", "222"));
		System.out.println(m1.get("4"));
		
	}
	
	//分拣思路：存储单词出现的次数
	public void test2(){
		String str="this is a cat and this is a mice and where is the food";
		String strArr[]=str.split(" ");
		Map<String, Letter> les=new HashMap<String, Letter>();
		for(String tmp:strArr){
			Letter le=les.get(tmp);
//			if(null == le){
//				l=new Letter(tmp, 1);
//				les.put(tmp, le);
//			}else{
//				l.setCount(l.getCount()+1);
//				les.put(tmp, le);
//			}
			if(les.containsKey(tmp)){
				le.setCount(le.getCount()+1);
			}else{
				le=new Letter(tmp, 1);
			}
			les.put(tmp, le);
		}
		for(Iterator iterator=les.entrySet().iterator();iterator.hasNext();){
			Entry entry=(Entry) iterator.next();
			String name=(String) entry.getKey();
			Letter l=(Letter) entry.getValue();
			System.out.println(name+"---"+l.getCount());
		}
		
	}
}

class Letter{
	
	private String name;
	private int count;
	
	public Letter(){}
	public Letter(String name, int count) {
		super();
		this.name = name;
		this.count = count;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
