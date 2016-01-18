/**
 * 
 *
 */
package com.xsyi.core.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletContextEvent;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.core.io.UrlResource;

import com.xsyi.core.IConstants;
import com.xsyi.core.file.FileSuffixFilter;
import com.xsyi.core.vo.JobParam;
import com.xsyi.core.xml.XmlUtil;

/**
 * <dl>
 *    <dt><b>Title:</b>系统配置项目读取</dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b>读取配置项内容，提供子模块和应用服务</dt>
 *    <dd>
 *    	<p>
 *    </dd>
 * </dl>
 *
 * @author tangtao7481
 * @version , Feb 7, 2013 2:50:17 PM
 * 
 */
public class SystemConfigUtils {

	/**
	 * 所有配置项内容
	 */
	private static Map<String,String> sysConfigProps = new TreeMap<String,String>();
	private static Map<String,Object> jobActionParams = new HashMap<String,Object>();
	
	/**
	 * 读取化系统配置属性
	 * @param event
	 */
	public static void initSystemConfigProps(ServletContextEvent event){
		if( sysConfigProps!=null && sysConfigProps.size()>0 ) return;
		initSystemConfigProperties(event);
	}
	
	/**
	 * 手动添加系统设置属性
	 * @param key
	 * @param value
	 * @param override
	 * @throws Exception
	 */
	public static void addSysConfigProperty(String key,String value,boolean override) throws Exception{
		if( !override ){
			if( sysConfigProps.get(key)!=null ) throw new Exception("已经存在属性,不能原有属性");
		}
		sysConfigProps.put(key, value);
	}
	
	/**
	 * 获取系统中配置的Job参数
	 * @param key
	 * @return
	 */
	public static JobParam getJobParam(String key){
		return (JobParam)jobActionParams.get(key);
	}
	
	/**
	 * 获取系统配置属性
	 * @param key
	 * @return
	 */
	public static String getSysconfigProperty(String key){
		return sysConfigProps.get(key);
	}
	
	/**
	 * 初始化系统配置参数
	 * @param event
	 */
	private static void initSystemConfigProperties(ServletContextEvent event){
		String[] filesPath = getSystemProperty(event);
		Set<String> filespathSet = new HashSet<String>();
		if (!ArrayUtils.isEmpty(filesPath)) {
			for (String aFilePath : filesPath) {
				if (StringUtils.isNotBlank(aFilePath)) {
					try {
						File dir = new File(aFilePath);
						if (dir.exists()) {
							File[] files = dir.listFiles(new FileSuffixFilter(IConstants.PROPERTIES_SUFFIX));
							if (!ArrayUtils.isEmpty(files)) {
								for (int i = 0; i < files.length; i++) {
									Properties props = new Properties();
									props.load(new FileInputStream(files[i]));
									Enumeration<Object> e = props.keys();
									while (e.hasMoreElements()) {
										String s = (String) e.nextElement();
										sysConfigProps.put(s,props.getProperty(s));
									}
									filespathSet.add(files[i].getName());
								}
							}
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						continue;
					}
				}
			}
		}
		//初始化系统中任务调度的配置参数
		try{
			Map<String,JobParam> jobParams = XmlUtil.parseXmlToTreeNode(new UrlResource(Thread.currentThread().getContextClassLoader().getResource("dracoJobActionParams.xml")));
			if( jobParams!=null ) jobActionParams.putAll(jobParams);
		}catch(Exception ex){
			event.getServletContext().log("初始化系统参数失败",ex);
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * 获取applicationContext-core.xml文件中系统配置路径的属性内容
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static String[] getSystemProperty(ServletContextEvent event) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL contextPath = classLoader.getResource("applicationContext-core.xml");
		event.getServletContext().log("系统初始化======> reading config file applicationContext-core.xml from "+ contextPath);
		SAXBuilder sax = new SAXBuilder();
		String[] filesPath = null; 
		try {
			InputStream file = classLoader.getResourceAsStream("applicationContext-core.xml");
			Document doc = sax.build(file);// 获得文档对象
			Element root = doc.getRootElement();// 获得根节点
			List<Element> list = root.getChildren();
			for (Element bean : list) {
				//获取ID值指定的Bean的xml
				if (StringUtils.isNotBlank(bean.getName()) && "property-placeholder".equals(bean.getName())) {
					String configLocation = bean.getAttributeValue("location");
					event.getServletContext().log("系统配置文件路径======> system config path file: " + configLocation);
					//去掉classpath前缀，然后再分割路径
					String[] configPaths = configLocation.split(",");
					if( configPaths!=null && configPaths.length>0 ){
						for( int i=0;i<configPaths.length;i++ ){
							if( configPaths[i].startsWith("file") ){
								configPaths[i] = configPaths[i].substring(5,configPaths[i].lastIndexOf("/")+1);
							}else{
								configPaths[i] = configPaths[i].substring(0,configPaths[i].lastIndexOf("/")+1);
							}
						}
						return configPaths;
					}
				}
			}
		} catch (JDOMException ex) {
			event.getServletContext().log("初始化系统参数失败",ex);
		} catch (IOException ex) {
			event.getServletContext().log("初始化系统参数失败",ex);
		}
		return filesPath;
	}
	
}
