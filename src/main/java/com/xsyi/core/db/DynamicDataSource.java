/**
 * 
 *
 */
package com.xsyi.core.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


/**
 * Description: <p>动态数据库连接池源</p>
 * Content Desc:<p>
 * <p>
 *  
 * </p>
 *<p>
 * @author tangtao7481
 * @version Mar 15, 2013
 * Since draco app
 * 
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#determineCurrentLookupKey()
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		return CustomerContextHolder.getDatasourceType();
	}

}
