package com.xsyi.mongo.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.xsyi.core.annotation.MongodbFiledType;

@Document(collection="user")
public class MongoUser extends MongoBaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MongoUser(){}

	public MongoUser(String name, String title, int age) {
		super();
		this.name = name;
		this.title = title;
		this.age = age;
	}

	/**
	 * 注解field对应Mongo DB中collection的字段
	 */
	@Field(value="name")
	@MongodbFiledType(type=MongodbFiledType.Types.String) //默认String
	private String name;
	
	@Field(value="title")
	private String title;
	
	@Field(value="age")
	@MongodbFiledType(type=MongodbFiledType.Types.INT) //默认String
	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
}
