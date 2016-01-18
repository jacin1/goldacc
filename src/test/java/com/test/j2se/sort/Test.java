package com.test.j2se.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 测试使用Arrays
 * @author Nick
 *
 */
public class Test {

	@org.junit.Test
	public void test1(){
		List list=new ArrayList();
		Student s1=new Student("aa",12,122);
		Student s2=new Student("bb",12,12);
		Student s3=new Student("cc",32,82);
		Student s4=new Student("dd",12,36);
		list.add(s1);
		list.add(s2);
		list.add(s3);
		list.add(s4);
		Collections.sort(list, new StudentCompart());
		
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i));
		}
	}
}


class StudentCompart implements Comparator<Student>{

	@Override
	public int compare(Student o1, Student o2) {
		return o1.score - o2.score;
	}
	
}