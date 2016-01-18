/*
 * @(#)SystemInitListener.java 1.0 2011-6-28下午10:03:19
 *
 */
package com.xsyi.core.init;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.util.ResourceUtils;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.util.Log4jWebConfigurer;

import com.xsyi.core.config.SystemConfigUtils;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author tanks
 * @version 1.0, 2011-6-28
 * @since framework
 * 
 */
public class SystemInitListener implements ServletContextListener {

	static {
		// 类加载的时候设置log4j的初始化规则，采用自定义模式来初始化
		System.setProperty("log4j.defaultInitOverride", "false");
	}
	
	/**
	 * 
	 */
	public SystemInitListener() {}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		Log4jWebConfigurer.shutdownLogging(event.getServletContext());
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		SystemConfigUtils.initSystemConfigProps(event);
		this.setSystemProperties(event);
		this.initLogging(event.getServletContext());
	}
	
	/**
	 * 初始化Log4j
	 * @param servletContext
	 */
	private void initLogging(ServletContext servletContext){
		String location = servletContext.getInitParameter(Log4jWebConfigurer.CONFIG_LOCATION_PARAM);
		servletContext.log("Log4j config path:" + location);
		if( StringUtils.isBlank(location) ) throw new RuntimeException(Log4jWebConfigurer.CONFIG_LOCATION_PARAM + " not set,please set the correct value");
		InputStream in = null;
		try {
			if (!ResourceUtils.isUrl(location)) {
				location = SystemPropertyUtils.resolvePlaceholders(location);
				servletContext.log("Log4j config file path:" + location);
			}
			servletContext.log("Initializing log4j from [" + location + "]");
			in = servletContext.getResourceAsStream(location);
			if(location.toLowerCase().endsWith(".xml")){
    	    	(new DOMConfigurator()).doConfigure(in, LogManager.getLoggerRepository());
    	    } else {
    			Properties p = new Properties();
    			p.load(in);
    	    	(new PropertyConfigurator()).doConfigure(p, LogManager.getLoggerRepository());
    	    }
		}catch (Exception ex) {
			throw new IllegalArgumentException("Invalid 'log4jConfigLocation' parameter2: " + ex.getMessage());
		}finally{
			if( in!=null ){
				try {
					in.close();
				} catch (IOException ex) {
					servletContext.log("Initializing log4j from [" + location + "],error:",ex);
				}
			}
		}
	}
	
	/**
	 * 设置系统配置中参数内容
	 */
	private void setSystemProperties(ServletContextEvent event){
		try {
			String systemName = event.getServletContext().getServletContextName().toLowerCase();
			String logDir = SystemConfigUtils.getSysconfigProperty("log.out."+systemName+".dir");
			String rootLogLevel = SystemConfigUtils.getSysconfigProperty("log.root.level");
			String logSqlLevel = SystemConfigUtils.getSysconfigProperty("log.sql.level");
			String logDracoLevel = SystemConfigUtils.getSysconfigProperty("log.draco.level");
			String logThirdComponentLeve = SystemConfigUtils.getSysconfigProperty("log.third.component.leve");
			
			event.getServletContext().log("系统初始化======> reading config file:"+", 'log.out.dir' = "+logDir);
			boolean isAbsolute = ((new URI(logDir)).isAbsolute());
			//判断是否为linux系统
			String osName = System.getProperty("os.name");
			if( StringUtils.isNotBlank(osName) && osName.toLowerCase().contains("linux") && logDir.startsWith("/")) {
				event.getServletContext().log("系统初始化======> osName = " + osName);
				isAbsolute = Boolean.TRUE.booleanValue();
			}
			event.getServletContext().log("系统初始化======> isAbsolute = "+isAbsolute);
			if (!isAbsolute){
				boolean isUrl = ResourceUtils.isUrl(logDir);
				event.getServletContext().log("系统初始化======> isUrl = "+isUrl);
	            if(!isUrl){		            	
	            	logDir = "${user.dir}/"+logDir;
	            	logDir = SystemPropertyUtils.resolvePlaceholders(logDir);
	            	event.getServletContext().log("系统初始化======> add ${user.dir}, after spring resolves placeholder, logDir = "+logDir);
	            }
			}
    		File logDirPath = new File(logDir);	
    		boolean isExists = logDirPath.exists();
    		event.getServletContext().log("系统初始化======> " + logDir + " isExists = "+isExists);
    		if (!isExists) {
    			event.getServletContext().log("系统初始化======> mk dir ,is success ="+logDirPath.mkdirs());
    		}

    		event.getServletContext().log("系统初始化======> Setting LogFile Output Dir : "+logDir);
    		System.setProperty("LogDir", logDir);
    		System.setProperty("log.root.level", rootLogLevel);	
    		System.setProperty("log.sql.level", logSqlLevel);	
    		System.setProperty("log.draco.level", logDracoLevel);
    		System.setProperty("log.third.component.leve", logThirdComponentLeve);
    		//如果这三个值没有设置直接报系统异常，人为解决在重新发布
			if (StringUtils.isBlank(logDir) 
					|| StringUtils.isBlank(rootLogLevel)
					|| StringUtils.isBlank(logSqlLevel) 
					|| StringUtils.isBlank(logDracoLevel)
					|| StringUtils.isEmpty(logThirdComponentLeve) ) {
				throw new RuntimeException("系统日志路径参数未配置");
			}
		} catch (Exception ex) {
			event.getServletContext().log("系统初始化日志参数失败",ex);
		} 
	}
	
}
