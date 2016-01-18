package com.test.j2se.collection.map;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class TestMap {
	
	static int i;

	public static void main(String[] args) {
		System.out.println(i);

		Map<String, String> map=new HashMap<String, String>();
		map.put("a", "1111111111");
		map.put("b", "2222222222");
		map.put("c", "33333333333");
		map.remove("a");
		map.put("d", "44444444");
		for(Entry<String, String> entry:map.entrySet()){
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
		}
		
		String s1=new String("aaa");
		String s2=new String("aaa");
		System.out.println(s1 == s2);
	}

}
