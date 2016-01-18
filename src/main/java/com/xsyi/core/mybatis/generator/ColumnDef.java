/*
 * @(#)ColumnDef.java 1.0 2011-6-1上午10:42:08
 */
package com.xsyi.core.mybatis.generator;

import java.util.Map;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	字段封装类
 *    </dd>
 *    <p>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author eric
 * @version 1.0, 2011-6-1
 * @since framework
 * 
 */
public class ColumnDef {
	private String mongoCollectionName;
	private String orginColumnName;
	private String columnName;
	private String columnType;
	private String columnComment;
	private Map<String,String> columnEnum;

	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	public String getColumnComment() {
		return columnComment;
	}
	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}
	public void setColumnEnum(Map<String,String> columnEnum) {
		this.columnEnum = columnEnum;
	}
	public Map<String,String> getColumnEnum() {
		return columnEnum;
	}
	public String getOrginColumnName() {
		return orginColumnName;
	}
	public void setOrginColumnName(String orginColumnName) {
		this.orginColumnName = orginColumnName;
	}
	public String getMongoCollectionName() {
		return mongoCollectionName;
	}
	public void setMongoCollectionName(String mongoCollectionName) {
		this.mongoCollectionName = mongoCollectionName;
	}
	
}
