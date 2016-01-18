package com.test.j2se.collection.common;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.time.FastDateFormat;
import org.junit.Before;
import org.junit.Test;

import com.test.model.Car;

import junit.framework.TestCase;

/**
 * 了解apache jakarta下的常用工具包
 * @author Nick
 *
 */
public class UtilTest {
	
	Set<Integer> s1=null;
	Set<Integer> s2=null;
	
	public void startUp(){
		s1=new HashSet();
		s1.add(1);
		s1.add(2);
		s1.add(3);
		
		s2=new HashSet();
		s2.add(2);
		s2.add(3);
		s2.add(3);
		s2.add(4);
	}

	//取并集:CollectionUtils.union()
	@Test
	public void test1(){
		startUp();
		//取并集
		Collection coll=CollectionUtils.union(s1, s2);
		Iterator iterator=coll.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
	}
	
	//测试ToStringBuilder
	@Test
	public void test2(){
		Car car=new Car("a","AZ0001","","",0);
		System.out.println(car);
 	}
	
	//测试HashCodeBuilder
	//重写equals,必须重写hashcode
	@Test
	public void test3(){
		Car car=new Car("a","AZ0001","","",0);
		Car car2=new Car("a","AZ0002","","my",2014);
		System.out.println(car.equals(car2));
	}

	@Test
	public void test4(){
		
		
	}
	
}
