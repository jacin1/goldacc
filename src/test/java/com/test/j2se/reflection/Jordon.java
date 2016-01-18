package com.test.j2se.reflection;

public class Jordon {
	
	public Jordon(){}

	public Jordon(String name, int age, String ename, String memo) {
		super();
		this.name = name;
		this.age = age;
		this.ename = ename;
		this.memo = memo;
	}
	
	String p;

	public String name;
	
	private int age=12;
	
	private String ename;
	
	private String memo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	//重构
	public void setName(String  name,String name2){
		this.name=new StringBuilder(name).append(name2).toString();
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
}
