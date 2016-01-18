/*
 * @(#)CustomBeanWrapper.java 1.0 2011-5-24下午07:53:14
 *
 */
package com.xsyi.core.mybatis;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.wrapper.BeanWrapper;

import com.xsyi.core.IConstants;
import com.xsyi.core.util.CommonUtils;

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
 *    </dd>
 * </dl>
 *
 * @author eric
 * @version 1.0, 2011-5-24
 * @since framework
 * 
 */
public class CustomBeanWrapper extends BeanWrapper {
	public CustomBeanWrapper(MetaObject metaObject, Object object) {
		super(metaObject, object);
	}
	/* (non-Javadoc)
	 * @see org.apache.ibatis.reflection.wrapper.BeanWrapper#findProperty(java.lang.String)
	 */
	@Override
	public String findProperty(String columnName, boolean useCamelCaseMapping) {
		if (columnName.contains(IConstants.UNDERLINE)){
			String propertyName;
			if ((propertyName=super.findProperty(CommonUtils.underscoresToUncapHump(columnName),Boolean.TRUE))!=null){
				return propertyName;
			}
		}
		return super.findProperty(columnName,useCamelCaseMapping);
	}
	

}
