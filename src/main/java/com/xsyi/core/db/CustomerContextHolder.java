/**
 * 
 *
 */
package com.xsyi.core.db;

import org.springframework.util.Assert;

/**
 * Description:
 * <p>
 * </p>
 * Content Desc:
 * <p>
 * <p>
 * 
 * </p>
 * <p>
 * 
 * @author tangtao7481
 * @version Mar 15, 2013 Since draco app
 * 
 */
public class CustomerContextHolder {

	public enum DatasourceType {
		RAC, JIRA
	}

	private static final ThreadLocal<DatasourceType> contextHolder = new ThreadLocal<DatasourceType>();

	public static void setDatasourceType(DatasourceType datasourceType) {
		Assert.notNull(datasourceType, "DatasourceType cannot be null");
		contextHolder.set(datasourceType);
	}

	public static DatasourceType getDatasourceType() {
		return (DatasourceType) contextHolder.get();
	}

	public static void clearDatasourceType() {
		contextHolder.remove();
	}

}
