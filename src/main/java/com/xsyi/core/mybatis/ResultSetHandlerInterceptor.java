/*
 * @(#)ResultSetHandlerInterceptor.java 1.0 2011-5-19上午09:39:03
 *
 */
package com.xsyi.core.mybatis;

import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.resultset.FastResultSetHandler;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;

import com.xsyi.core.util.ReflectionUtils;


/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	分页和驼峰字串改造拦截器
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author eric
 * @version 1.0, 2011-5-19
 * @since framework
 * 
 */
@Intercepts({@Signature(type=ResultSetHandler.class,method="handleResultSets",args={Statement.class})})
public class ResultSetHandlerInterceptor implements Interceptor {

	private static Log _log = LogFactory.getLog(ResultSetHandlerInterceptor.class); 
	
	/* (non-Javadoc)
	 * @see org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin.Invocation)
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		//分页查询
		FastResultSetHandler resultSet = (FastResultSetHandler)invocation.getTarget();	       
        RowBounds rowBounds = (RowBounds)ReflectionUtils.getFieldValue(resultSet,"rowBounds");
        if (rowBounds.getLimit()>0 && rowBounds.getLimit()<RowBounds.NO_ROW_LIMIT){
        	ReflectionUtils.setFieldValue(resultSet, "rowBounds", new RowBounds());
        }
        //字段名转换
        try {
			Configuration configuration = (Configuration)ReflectionUtils.getFieldValue(resultSet, "configuration");
			
			if (!(configuration instanceof CustomConfiguration)){
				
				CustomConfiguration customConfiguration = configuration.getEnvironment()!=null?new CustomConfiguration(configuration.getEnvironment()):new CustomConfiguration();			
				
				ReflectionUtils.setFieldValue(customConfiguration, "lazyLoadingEnabled", ReflectionUtils.getFieldValue(configuration, "lazyLoadingEnabled"));
				ReflectionUtils.setFieldValue(customConfiguration, "aggressiveLazyLoading", ReflectionUtils.getFieldValue(configuration, "aggressiveLazyLoading"));
				ReflectionUtils.setFieldValue(customConfiguration, "multipleResultSetsEnabled", ReflectionUtils.getFieldValue(configuration, "multipleResultSetsEnabled"));
				ReflectionUtils.setFieldValue(customConfiguration, "useGeneratedKeys", ReflectionUtils.getFieldValue(configuration, "useGeneratedKeys"));
				ReflectionUtils.setFieldValue(customConfiguration, "useColumnLabel", ReflectionUtils.getFieldValue(configuration, "useColumnLabel"));
				ReflectionUtils.setFieldValue(customConfiguration, "cacheEnabled", ReflectionUtils.getFieldValue(configuration, "cacheEnabled"));
				ReflectionUtils.setFieldValue(customConfiguration, "defaultStatementTimeout", ReflectionUtils.getFieldValue(configuration, "defaultStatementTimeout"));
				ReflectionUtils.setFieldValue(customConfiguration, "defaultExecutorType", ReflectionUtils.getFieldValue(configuration, "defaultExecutorType"));
				ReflectionUtils.setFieldValue(customConfiguration, "autoMappingBehavior", ReflectionUtils.getFieldValue(configuration, "autoMappingBehavior"));
				ReflectionUtils.setFieldValue(customConfiguration, "variables", ReflectionUtils.getFieldValue(configuration, "variables"));
				ReflectionUtils.setFieldValue(customConfiguration, "objectFactory", ReflectionUtils.getFieldValue(configuration, "objectFactory"));
				ReflectionUtils.setFieldValue(customConfiguration, "objectWrapperFactory", ReflectionUtils.getFieldValue(configuration, "objectWrapperFactory"));
				ReflectionUtils.setFieldValue(customConfiguration, "mapperRegistry", ReflectionUtils.getFieldValue(configuration, "mapperRegistry"));
				ReflectionUtils.setFieldValue(customConfiguration, "interceptorChain", ReflectionUtils.getFieldValue(configuration, "interceptorChain"));
				ReflectionUtils.setFieldValue(customConfiguration, "typeHandlerRegistry", ReflectionUtils.getFieldValue(configuration, "typeHandlerRegistry"));
				ReflectionUtils.setFieldValue(customConfiguration, "mappedStatements", ReflectionUtils.getFieldValue(configuration, "mappedStatements"));
				ReflectionUtils.setFieldValue(customConfiguration, "caches", ReflectionUtils.getFieldValue(configuration, "caches"));
				ReflectionUtils.setFieldValue(customConfiguration, "resultMaps", ReflectionUtils.getFieldValue(configuration, "resultMaps"));
				ReflectionUtils.setFieldValue(customConfiguration, "parameterMaps", ReflectionUtils.getFieldValue(configuration, "parameterMaps"));
				ReflectionUtils.setFieldValue(customConfiguration, "keyGenerators", ReflectionUtils.getFieldValue(configuration, "keyGenerators"));
				ReflectionUtils.setFieldValue(customConfiguration, "loadedResources", ReflectionUtils.getFieldValue(configuration, "loadedResources"));
				ReflectionUtils.setFieldValue(customConfiguration, "sqlFragments", ReflectionUtils.getFieldValue(configuration, "sqlFragments"));
				//ReflectionUtils.setFieldValue(customConfiguration, "statementNodesToParse", ReflectionUtils.getFieldValue(configuration, "statementNodesToParse"));
				//ReflectionUtils.setFieldValue(customConfiguration, "resourceMap", ReflectionUtils.getFieldValue(configuration, "resourceMap"));
				//ReflectionUtils.setFieldValue(customConfiguration, "cacheRefMap", ReflectionUtils.getFieldValue(configuration, "cacheRefMap"));

				ReflectionUtils.setFieldValue(resultSet, "configuration", customConfiguration);
				
			}
		} catch (Exception e) {
			_log.error("", e);
		}
        return invocation.proceed();
	}

	/* (non-Javadoc)
	 * @see org.apache.ibatis.plugin.Interceptor#plugin(java.lang.Object)
	 */
	@Override
	public Object plugin(Object obj) {
		return Plugin.wrap(obj, this);
	}

	/* (non-Javadoc)
	 * @see org.apache.ibatis.plugin.Interceptor#setProperties(java.util.Properties)
	 */
	@Override
	public void setProperties(Properties properties) {
	}

}
