/*
 * @(#)StatementHandlerInterceptor.java 1.0 2011-5-19上午09:03:51
 *
 */
package com.xsyi.core.mybatis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.RowBounds;

import com.xsyi.core.util.ReflectionUtils;


/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	分页改造拦截器
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
@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class})})
public class StatementHandlerInterceptor implements Interceptor {

	/* (non-Javadoc)
	 * @see org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin.Invocation)
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		
		if (invocation.getTarget() instanceof RoutingStatementHandler){
			
			RoutingStatementHandler statement = (RoutingStatementHandler)invocation.getTarget();
			BaseStatementHandler handler = (BaseStatementHandler)ReflectionUtils.getFieldValue(statement,"delegate");
						
			BoundSql boundSql = handler.getBoundSql();
            String sql = boundSql.getSql();
            boolean shouldPagedQuery = false;
         
						
			RowBounds rowBounds = (RowBounds)ReflectionUtils.getFieldValue(handler,"rowBounds");
			if (rowBounds.getLimit()>0 && rowBounds.getLimit()<RowBounds.NO_ROW_LIMIT){
				shouldPagedQuery = true;
	        }
	        	
	        if (rowBounds instanceof Pager) {
	        	
	            //*****************
	            String countsql =  Pager.generateCountSql(sql);
	            Connection c = (Connection)invocation.getArgs()[0];
	            PreparedStatement ps = c.prepareStatement(countsql);
	            ParameterHandler parameterHandler = (ParameterHandler)ReflectionUtils.getFieldValue(handler,"parameterHandler");
	            parameterHandler.setParameters(ps);
	            if (ps.execute()){
	            	ResultSet r = ps.getResultSet();
		            while(r.next()){
		            	((Page)rowBounds).setTotalCount(r.getInt(1));
		            }
	            }
	            ps.close();
	            //*****************
	            shouldPagedQuery = true;
	        }
			
			if (shouldPagedQuery){
	            sql = Pager.generatePagedQuerySql(sql, rowBounds.getOffset(), rowBounds.getLimit());           
	            ReflectionUtils.setFieldValue(boundSql, "sql", sql);
			}
			
		}

		return invocation.proceed();
	}

	/* (non-Javadoc)
	 * @see org.apache.ibatis.plugin.Interceptor#plugin(java.lang.Object)
	 */
	@Override
	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
	}

	/* (non-Javadoc)
	 * @see org.apache.ibatis.plugin.Interceptor#setProperties(java.util.Properties)
	 */
	@Override
	public void setProperties(Properties arg0) {
	}
	
}