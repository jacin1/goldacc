package com.test.j2se.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

/**
 * 通过反射获取泛型信息
 * @author Nick
 *
 */
public class Demo04 {

	Class c=null;
	
	@Before
	public void startUp() throws Exception{
		c=Class.forName("com.test.j2se.reflection.Jordon");
	}
	
	@Test
	public void test1() throws Exception{
		
		//获得指定方法参数泛型信息
		Method m = Demo04.class.getMethod("test01", Map.class,List.class);
		Type[] t = m.getGenericParameterTypes();
		for (Type paramType : t) {
			System.out.println("#"+paramType);
			if(paramType instanceof ParameterizedType){
				Type[] genericTypes = ((ParameterizedType) paramType).getActualTypeArguments();
				for (Type genericType : genericTypes) {
					System.out.println("泛型类型："+genericType);
				}
			}
		}
		
		//获得指定方法返回值泛型信息
		Method m2 = Demo04.class.getMethod("test02", null);
		Type returnType = m2.getGenericReturnType();
		if(returnType instanceof ParameterizedType){
				Type[] genericTypes = ((ParameterizedType) returnType).getActualTypeArguments();

				for (Type genericType : genericTypes) {
					System.out.println("返回值，泛型类型："+genericType);
				}
				
		}
		
	}
	
	public void test01(Map<String,Jordon> map,List<Jordon> list){
		System.out.println("Demo04.test01()");
	}
	
	public Map<Integer,Jordon> test02(){
		System.out.println("Demo04.test02()");
		return null;
	}
}
