package com.test.j2se.javassit;

import java.lang.reflect.Method;
import java.util.Arrays;

//import javassist.ClassPool;
//import javassist.CtClass;
//import javassist.CtMethod;
//import javassist.Modifier;

import org.junit.Before;
import org.junit.Test;

import com.test.j2se.reflection.Jordon;

public class Demo01 {
	
//	CtClass ct=null;
//	
//	@Before
//	public void startUp() throws Exception{
//		ClassPool pool=ClassPool.getDefault();
//		ct=pool.get("com.test.j2se.reflection.Jordon");
//	}
//
//	@Test
//	@SuppressWarnings("all")
//	public void test1() throws Exception{
//		//字节数组
//		byte[] btyes=ct.toBytecode();
//		System.out.println(Arrays.toString(btyes));
//		//获取类名
//		String fullClassName=ct.getName();
//		System.out.println(fullClassName);
//	}
//	
//	//通过javassit,生成新的方法
//	@Test
//	public void test2() throws Exception{
//		CtMethod method=new CtMethod(CtClass.intType, "add", new CtClass[]{CtClass.intType,CtClass.charType}, ct);
//		method.setModifiers(Modifier.PUBLIC);
//		method.setBody("{System.out.println(\"hell world\");return $2;}");
//		ct.addMethod(method);
//		
//		//通过反射调用新生成的方法,进行验证
//		Class clazz=ct.toClass();
//		Object o=clazz.newInstance();//调用无参构造器，创建新的对象
//		Method m=clazz.getDeclaredMethod("add", int.class,char.class);
//		Object result=m.invoke(o, 100,'a');
//		System.out.println(result);
//	}
//	
//	//实现类似AOP的功能,方法执行前后,加入其它逻辑
//	@Test
//	public void test3() throws Exception{
//		CtMethod cm=ct.getDeclaredMethod("setAge", new CtClass[]{CtClass.intType});
//		cm.insertBefore("System.out.println($1);System.out.println(\"123等待\");");
//		//反射调用
//		cm.insertAfter("System.out.println(\"end\");");
//		Class clazz=ct.toClass();
//		Jordon o=(Jordon) clazz.newInstance();//调用无参构造器，创建新的对象
////		Method m=clazz.getDeclaredMethod("setAge", int.class);
////		Object result=m.invoke(o, 100);
////		System.out.println(result);
//		o.setAge(200);
//		
//	}
	
}
