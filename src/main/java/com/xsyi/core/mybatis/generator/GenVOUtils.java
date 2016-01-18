/*
 * @(#)GenVOUtils.java 1.0 2011-5-31下午05:18:46
 *
 */
package com.xsyi.core.mybatis.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.xsyi.core.IConstants;
import com.xsyi.core.util.CommonUtils;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	ibatis pojo自动生成工具类
 *    	
 *    </dd><p>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>
 *    	<b>A)</b> 在c盘建立一个test目录<p>
 *    	<b>B)</b> 对数据库所有字段加上注释，如果需要把该字段在生成时定义成枚举型，需要在注释中加入{英文开头字串1:中文描述1,英文开头字串2:中文描述3,...}，
 *    	如：是否为默认对操作员开放,{Y:是,N:否}<p>
 *      <b>C)</b> 将会在c盘的test目录下生成vo和entity-enums.properties，请将它们分别覆盖到ttybase.model.vo和ttybase/src/main/resources下
 *    </dd>
 * </dl>
 *
 * @author eric
 * @version 1.0, 2011-5-31
 * @since framework
 * 
 */
public class GenVOUtils {
	
	// private final static String
	// DB_CON_STR="jdbc:oracle:thin:@10.98.9.43:1521:techdb";
	private final static String DB_CON_STR = "jdbc:oracle:thin:@192.168.29.248:1521:orcl";
	private final static String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	// private final static String DB_USERNAME="ttydbusr";
	private final static String DB_USERNAME = "app";
	// private final static String DB_PASSWORD="ttydbusr";
	private final static String DB_PASSWORD = "app112";
	private final static Map<Integer, String> typesMap = new HashMap<Integer, String>();
	
	static{
		typesMap.put(Types.CHAR, "String");
		typesMap.put(Types.VARCHAR, "String");
		typesMap.put(Types.DECIMAL, "Double");
		typesMap.put(Types.INTEGER, "Integer");
		typesMap.put(Types.DATE, "java.util.Date");		
	}
	
	public static Connection getConn() {
		Connection conn = null;
		try {
			Properties props =new Properties();
			props.put("user",DB_USERNAME);
			props.put("password",DB_PASSWORD);
			props.put("remarksReporting","true");
			props.put("disableDefineColumnType","true");		
			Class.forName(DB_DRIVER);
			conn = DriverManager.getConnection(DB_CON_STR,props);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return conn;
	}


	public static void releaseConn(Connection conn) {
		try {
			if (conn != null) {
				if (!conn.isClosed()) {
					conn.close();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 生成所有表的vo和properties
	 * @throws Exception
	 */
	public static void genVOs()throws Exception{
		Connection c = null;
		try {
			c = getConn();
			DatabaseMetaData db = c.getMetaData();
			ResultSet tables = db.getTables(null, DB_USERNAME.toUpperCase(), null, new String[]{"TABLE"});
			Map<String, String> properties = new TreeMap<String,String>();
			while(tables.next()){ 
				try {
					String originalTable = tables.getString("TABLE_NAME");
				    System.out.println("表名:"+originalTable); 
				    ResultSet columns = db.getColumns(null, DB_USERNAME.toUpperCase(), originalTable.toUpperCase(), null);
				    String aTable = CommonUtils.underscoresToHump(originalTable);
				    Set<ColumnDef> columnSet = new HashSet<ColumnDef>();
				    boolean containEnums = false;
				    while(columns.next()){
				    	String originalColumnName = columns.getString("COLUMN_NAME");
				    	String aColumnName = CommonUtils.underscoresToUncapHump(originalColumnName);
				    	String aColumnType = null;
				    	int dataType = columns.getInt("DATA_TYPE");
				    	if (dataType==3){
							Statement stmt = c.createStatement();
							ResultSet rs = stmt.executeQuery("select data_precision as dp,data_scale as ds from all_tab_columns where table_name='"+originalTable+"' and column_name = '"+originalColumnName+"'");
							while (rs.next()){
								int ds = rs.getInt("ds");
								if (ds!=0){
									int dp = rs.getInt("dp");
									if (dp>15){
										aColumnType = "Double";
									}else{
										aColumnType = "Float";
									}
								}else{
									aColumnType = "Integer";
								}
							}
				    	} else {
				    		aColumnType = typesMap.get(dataType);
				    	}
				    	
				    	
				    	String aColumnComment = columns.getString("REMARKS");
				    	Map<String,String> aColumnEnum = null;
				    	System.out.println("表名:"+aTable+"--->字段名:"+aColumnName+",字段类型:"+aColumnType+",字段描述:"+aColumnComment); 
				    	if ( aColumnComment!=null && aColumnComment.indexOf("{")!=-1 && aColumnComment.indexOf("}")!=-1 ){
				    		if (!containEnums){
				    			containEnums=true;
				    		}
				    		aColumnType = WordUtils.capitalize(aColumnName);
				    		aColumnEnum = new HashMap<String, String>();
				    		String jasonStr = StringUtils.substringBetween(aColumnComment, "{", "}");
				    		jasonStr = jasonStr.replace("：", ":");
				    		jasonStr = jasonStr.replace("，", ",");
				    		String[] temp1 = jasonStr.split(",");
				    		for (String temp2 : temp1) {
								String[] temp3 = temp2.split(":");
								aColumnEnum.put(temp3[0].trim(), temp3[1].trim());
								properties.put(
										aTable
										.concat(IConstants.POINT)
										.concat(WordUtils.capitalize(aColumnName))
										.concat(IConstants.POINT)
										.concat(temp3[0].trim()).toLowerCase(), temp3[1]);
							}
				    	}
				    	
				    	ColumnDef aDef = new ColumnDef();
				    	aDef.setMongoCollectionName(originalTable.toLowerCase());
				    	aDef.setOrginColumnName(originalColumnName.toLowerCase());
				    	aDef.setColumnName(aColumnName);
				    	aDef.setColumnType(aColumnType);
				    	aDef.setColumnComment(aColumnComment);
				    	aDef.setColumnEnum(aColumnEnum);
				    	columnSet.add(aDef);
				    }
				    writeJavaBean(aTable,columnSet,containEnums,originalTable.toLowerCase());
			    
				}catch(Exception e){
					e.printStackTrace();
					continue;
				}
			    
			}
			writeProperties(properties);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if (c!=null){
				releaseConn(c);
			}			
		}
	}
	
	/**
	 * 生成一个表的vo和properties
	 * @param aTable  表名， 如：OPER_INFO
	 * @throws Exception
	 */
	public static void genOneVO(String originalTable)throws Exception{
		Connection c = null;
		try {
			c = getConn();


			DatabaseMetaData db = c.getMetaData();

			Map<String, String> properties = new TreeMap<String,String>();
			try {
			    System.out.println("表名:"+originalTable); 
			    ResultSet columns = db.getColumns(null, DB_USERNAME.toUpperCase(), originalTable.toUpperCase(), null);
			    String aTable = CommonUtils.underscoresToHump(originalTable);
			    Set<ColumnDef> columnSet = new HashSet<ColumnDef>();
			    boolean containEnums = false;
			    while(columns.next()){
			    	String originalColumnName = columns.getString("COLUMN_NAME");
			    	String aColumnName = CommonUtils.underscoresToUncapHump(originalColumnName);
			    	String aColumnType = null;
			    	int dataType = columns.getInt("DATA_TYPE");
			    	if (dataType==3){
						Statement stmt = c.createStatement();
						ResultSet rs = stmt.executeQuery("select data_precision as dp,data_scale as ds from all_tab_columns where table_name='"+originalTable+"' and column_name = '"+originalColumnName+"'");
						while (rs.next()){
							int ds = rs.getInt("ds");
							if (ds!=0){
								int dp = rs.getInt("dp");
								if (dp>15){
									aColumnType = "Double";
								}else{
									aColumnType = "Float";
								}
							}else{
								aColumnType = "Integer";
							}
						}
			    	} else {
			    		aColumnType = typesMap.get(dataType);
			    	}
			    	
			    	
			    	
			    	

			    	
			    	System.out.println("columns.getInt(DATA_TYPE)"+columns.getInt("DATA_TYPE"));
			    	String aColumnComment = columns.getString("REMARKS");
			    	Map<String,String> aColumnEnum = null;
			    	System.out.println("表名:"+aTable+"--->字段名:"+aColumnName+",字段类型:"+aColumnType+",字段描述:"+aColumnComment); 
			    	if ( aColumnComment!=null && aColumnComment.indexOf("{")!=-1 && aColumnComment.indexOf("}")!=-1 ){
			    		if (!containEnums){
			    			containEnums=true;
			    		}
			    		aColumnType = WordUtils.capitalize(aColumnName);
			    		aColumnEnum = new HashMap<String, String>();
			    		String jasonStr = StringUtils.substringBetween(aColumnComment, "{", "}");
			    		jasonStr = jasonStr.replace("：", ":");
			    		jasonStr = jasonStr.replace("，", ",");
			    		String[] temp1 = jasonStr.split(",");
			    		for (String temp2 : temp1) {
							String[] temp3 = temp2.split(":");
							aColumnEnum.put(temp3[0].trim(), temp3[1].trim());
							properties.put(
									aTable
									.concat(IConstants.POINT)
									.concat(WordUtils.capitalize(aColumnName))
									.concat(IConstants.POINT)
									.concat(temp3[0].trim()).toLowerCase(), temp3[1]);
						}
			    	}
			    	
			    	ColumnDef aDef = new ColumnDef();
			    	aDef.setMongoCollectionName(originalTable.toLowerCase());
			    	aDef.setOrginColumnName(originalColumnName.toLowerCase());
			    	aDef.setColumnName(aColumnName);
			    	aDef.setColumnType(aColumnType);
			    	aDef.setColumnComment(aColumnComment);
			    	aDef.setColumnEnum(aColumnEnum);
			    	columnSet.add(aDef);
			    }
			    writeJavaBean(aTable,columnSet,containEnums,originalTable.toLowerCase());
		    
			}catch(Exception e){
				e.printStackTrace();
			}
			writeProperties(properties);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if (c!=null){
				releaseConn(c);
			}			
		}
	}
	
	
	public static void writeJavaBean(String tableName,Set<ColumnDef> fields,boolean containEnums,String mongCollectionName) throws Exception{
		Properties p= new Properties(); 
		p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		VelocityEngine   ve   =   new   VelocityEngine(); 
		ve.init(p);      
        VelocityContext context = new VelocityContext();  
        context.put("tableName", tableName);
        context.put("mongoCollectionName", mongCollectionName);   
        context.put("fields", fields);   
        context.put("stringUtils", new StringUtils());
        context.put("serialVersionUID", new Random().nextLong());
        context.put("containEnums", containEnums);
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("c://test//"+tableName+".java")));   
        Template template =  ve.getTemplate("com/chinapnr/draco/core/mybatis/generator/javabean.vm","UTF-8");   
        template.merge(context, writer);   
        writer.flush();   
        writer.close();  
	}
	
	public static void writeProperties(Map<String, String> properties) throws Exception{
		Properties p= new Properties(); 
		p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		VelocityEngine ve = new  VelocityEngine(); 
		ve.init(p);      
        VelocityContext context = new VelocityContext();  
        context.put("properties", properties);  
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("c://test//entity_enums.properties")));   
        Template template =  ve.getTemplate("com/chinapnr/draco/core/mybatis/generator/properties.vm","UTF-8");   
        template.merge(context, writer);   
        writer.flush();   
        writer.close();  
	}
	
	
	public static void main(String[] args) throws Exception{
		//genVOs();
		//genOneVO("AWARD_INFO");
		genOneVO("WORK_DATE");
	}
	

}
