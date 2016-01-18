package com.test.guava;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;

import junit.framework.TestCase;

/**
 * guava常用类学习
 * @author Nick
 *
 */
public class Demo01 extends TestCase{

	//guava提供的List只读设置
	public void testImmutable(){
		List<String> immList=ImmutableList.of("a", "b");
//		immList.add("c"); //不能进行修改
		System.out.println(immList.size());
	}
	
	//过滤器
	public void testFilter(){
		//创建List,静态初始化
		List<String> list=Lists.newArrayList("a0a","bca");
		Collection<String> tmpList=Collections2.filter(list, new Predicate<String>() {
			@Override
			public boolean apply(String input) {
				return new StringBuilder(input).reverse().toString().equals(input);
			}
		});
		for(String s:tmpList){
			System.out.println(s);
		}
	}
	
	//函数式编程,以及函数的组合使用
	public void testTransForm(){
		//确保容器中元素字符串长度不超过5，否则截取;然后全部大写
		List<String> list=Lists.newArrayList("a12333","b222","cadfsd","d");
		Function<String,String> f1=new Function<String,String>() {
			@Override
			public String apply(String input) {
				return (input.length() >5?input.substring(0,5):input);
			}
		};
		//执行函数
//		Collection<String> tmpList=Collections2.transform(list, f1);
		//如果函数有先后顺序,一个函数不能完成,需要多个函数完成,则可以使用组合函数式编程
		
		Function<String, String> f2=new Function<String, String>() {
			@Override
			public String apply(String input) {
				return input.toUpperCase();
			}
		};
		Function<String, String> f3=Functions.compose(f1, f2);
		Collection<String> tmpList=Collections2.transform(list, f3);
		for(String s:tmpList){
			System.out.println(s);
		}
	}
	
	//HashMultiSet,存储可重复元素,并统计元素的重复次数
	public void testMultiSet(){
		String s1="this is a good day are you sure? is good !";
		Multiset<String> set=HashMultiset.create();
		for(String s:s1.split(" ")){
			set.add(s);
		}
		//获取所有的单词,不可重复
		Set<String> elementSet=set.elementSet();
		for(String temp:elementSet){
			System.out.println(temp+";"+set.count(temp));
		}
	}
	
	//MultiMap
	public void testMulitMap(){
		
		
	}
}
