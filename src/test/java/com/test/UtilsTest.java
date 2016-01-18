package com.test;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.quartz.utils.DBConnectionManager;

import com.xsyi.model.Role;

import junit.framework.TestCase;

public class UtilsTest extends TestCase {

    private static final Logger log=Logger.getLogger(UtilsTest.class);
    
    public void test1(){
//        log.info("11");
    	Role r1=new Role();
    	Role r2=new Role();
    	r1.setRoleid("111");
    	r2.setRoleid("111");
        log.info(r1.equals(r2));
        
        Set<Role> setList=new HashSet<Role>();
        setList.add(r1);
        setList.add(r2);
        log.info(setList.size());
    }
    
    public void test2(){
    	String s1="200001|04|20140925150541|1409251505416451|00|TY009888|01|2002000009||20140925|";
    	System.out.println(s1.length());
    	String temp =  "5cb6363bbdf4f09545e79237df250eca";
		//4.将摘要内容的前8个字节与后8个字节直接进行异或处理
		String front8 = temp.substring(0, 16);
		String last8 = temp.substring(16, 32);
		temp = "";
		StringBuffer sb = new StringBuffer("");
		System.out.println(Integer.parseInt("c",16));
		for (int i = 0; i < front8.length(); i++) {
			System.out.println(front8.charAt(i));
			System.out.println(last8.charAt(i));
			temp = Integer.toHexString(Integer.parseInt(front8.charAt(i) + "",
					16) ^ Integer.parseInt(last8.charAt(i) + "", 16));
			sb.append(temp);
			
		}
		System.out.println(sb.toString());
    }
    
    public void test4(){
    	
    	TestRole tr=getEg();
    	System.out.println(tr.getRoles()[0].getRolename());
    }
    
    private TestRole getEg(){
    	Role r1=new Role("1", "111");
    	Role r2=new Role("2", "r222");
    	Role r3=new Role("3","reee");
    	
    	Role[] roles=new Role[3];
    	roles[0]=r1;roles[1]=r2;roles[2]=r3;
    	
    	TestRole tr=new TestRole();
    	tr.setId("t1");
    	tr.setRoles(roles);
    	roles=null;
    	return tr;
    }
    
    
    public void test5(){
    	StringBuffer sBuffer=new StringBuffer();
    	sBuffer.append("11").append("2222222222222");
    	System.out.println(sBuffer.toString());
    }
    
    public void test6(){
    	String s1="20140408";
    	System.out.println(s1.substring(6));
    	
    	System.out.println(1.0/4);
    }
    
    public void test7(){
    	String pawd="test";
    	String despwd=DigestUtils.md5Hex(pawd);
    	
    	System.out.println(despwd);
    }
    
}
