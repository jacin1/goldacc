/**
 * 
 *
 */
package com.xsyi.core.vo;

import org.apache.commons.lang.StringUtils;

import com.xsyi.core.db.CustomerContextHolder;


/**
 * Description: <p></p>
 * Content Desc:<p>
 * <p>
 *  
 * </p>
 *<p>
 * @author tangtao7481
 * @version Mar 14, 2013
 * Since draco app
 * 
 */
public class JobParam {

	private String jobKey;
	private String jobName;
	private String jobValidator;
	private String jobQueryId;
	private String dbType;
	
	/**
	 * 
	 * @param key
	 * @param jobName
	 * @param jobValidator
	 * @param jobQueryId
	 */
	public JobParam(String jobKey, String jobName, String jobValidator, String jobQueryId,String dbType) {
		this.jobKey = jobKey;
		this.jobName = jobName;
		this.jobValidator = jobValidator;
		this.jobQueryId = jobQueryId;
		if( StringUtils.isNotBlank(dbType) 
				&& (CustomerContextHolder.DatasourceType.RAC.toString().equals(dbType) 
				 || CustomerContextHolder.DatasourceType.JIRA.toString().equals(dbType) )){
			this.dbType = dbType;
		}else{
			//默认使用oracle数据的连接
			this.dbType = CustomerContextHolder.DatasourceType.RAC.toString();
		}
	}

	/**
	 * @return the jobKey
	 */
	public String getJobKey() {
		return jobKey;
	}


	/**
	 * @param jobKey the jobKey to set
	 */
	public void setJobKey(String jobKey) {
		this.jobKey = jobKey;
	}


	/**
	 * @return the jobName
	 */
	public String getJobName() {
		return jobName;
	}

	/**
	 * @param jobName the jobName to set
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	/**
	 * @return the jobValidator
	 */
	public String getJobValidator() {
		return jobValidator;
	}

	/**
	 * @param jobValidator the jobValidator to set
	 */
	public void setJobValidator(String jobValidator) {
		this.jobValidator = jobValidator;
	}

	/**
	 * @return the jobQueryId
	 */
	public String getJobQueryId() {
		return jobQueryId;
	}

	/**
	 * @param jobQueryId the jobQueryId to set
	 */
	public void setJobQueryId(String jobQueryId) {
		this.jobQueryId = jobQueryId;
	}

	/**
	 * @return the dbType
	 */
	public String getDbType() {
		return dbType;
	}

	/**
	 * @param dbType the dbType to set
	 */
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	
}
