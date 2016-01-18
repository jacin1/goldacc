/**
 * 
 */
package com.xsyi.core.db;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.xsyi.core.db.CustomerContextHolder.DatasourceType;

/**
 * 动态路由AOP拦截
 * @author tangtao7481
 * @version $Id: DataSourceRouteAdvice.java, v 0.1 May 2, 2013 4:45:12 PM tangtao7481 Exp $
 */
public class DataSourceRouteAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        CustomerContextHolder.setDatasourceType(DatasourceType.JIRA);
        Object ret = invocation.proceed();
        CustomerContextHolder.clearDatasourceType();
        return ret;
    }

}
