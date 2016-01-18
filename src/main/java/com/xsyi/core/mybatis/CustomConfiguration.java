/*
 * @(#)CustomConfiguration.java 1.0 2011-5-24下午08:27:13
 *
 */
package com.xsyi.core.mybatis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.BeanWrapper;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;
import org.apache.ibatis.session.Configuration;

import com.xsyi.core.util.ReflectionUtils;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	改造ibatis，实现下划线分割自动转换成驼峰字串<br>
 *    </dd>
 *    <p>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p> 
 *    <pre>
 *例如：
 *	改造前写“select ENABLE_FLAG as enableFlag from USER_INFO”，才能使返回的vo的enableFlag字段有值
 *	现在只需写 “select ENABLE_FLAG from USER_INFO” 就可以有值
 *    </pre>
 *    </dd>
 * </dl>
 *
 * @author eric
 * @version 1.0, 2011-5-24
 * @since framework
 * 
 */
public class CustomConfiguration extends Configuration{
	
	public CustomConfiguration() {
		super();
	}

	public CustomConfiguration(Environment environment) {
		super(environment);
	}
	
	private static Log _log = LogFactory.getLog(CustomConfiguration.class); 
	/* (non-Javadoc)
	 * @see org.apache.ibatis.session.Configuration#newMetaObject(java.lang.Object)
	 */
	@Override
	public MetaObject newMetaObject(Object object) {
		MetaObject o = super.newMetaObject(object);
		ObjectWrapper ow = o.getObjectWrapper();
		if (ow instanceof BeanWrapper){
			try {
				ReflectionUtils.setFieldValue(
						o, 
						"objectWrapper", 
						new CustomBeanWrapper(
									o,
									ReflectionUtils.getFieldValue(ow, "object")
						)
				);
			} catch (Exception e) {
				_log.error("", e);
			}
		}
		return o;
	}
	
}
