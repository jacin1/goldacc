package com.test.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 测试重写tostring，equals，hashcode，compare方法
 * 使用ToStringBuilder,HashCodeBuilder等。
 * @author Nick
 *
 */
public class Car {

	private String name;
	
	private String model;
	
	private String serial;
	
	private String owner;
	
	private int year;

	public Car() {
		super();
	}

	public Car(String name, String model, String serial, String owner, int year) {
		super();
		this.name = name;
		this.model = model;
		this.serial = serial;
		this.owner = owner;
		this.year = year;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(3,17).append(name).append(model).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		boolean equals=false;
		if(obj.getClass()== Car.class){
			Car carBean=(Car) obj;
			equals=new EqualsBuilder().append(name, carBean.name)
						.append(model, carBean.model).isEquals();
		}
		return equals;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE).append("name",name)
				.append("model",model).append("serial",serial).append("year",year).toString();
	}
	
	
}
