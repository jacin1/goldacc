package com.xsyi.model;

import java.io.Serializable;

public class UserR implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;  
    
    private String name;  
      
    private String password;
    
    public UserR(){}

	public UserR(String id, String name, String password) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    
}
