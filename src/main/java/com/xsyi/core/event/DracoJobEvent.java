/**
 * 
 *
 */
package com.xsyi.core.event;

import java.util.Map;

import org.springframework.batch.core.ExitStatus;

/**
 * <dl>
 *    <dt><b>Title:</b>spring batch job 执行完事件</dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>
 *    </dd>
 * </dl>
 *
 * @author tangtao7481
 * @version , Mar 20, 2013 6:32:10 PM
 * 
 */
public class DracoJobEvent extends DracoBaseEvent {

	private static final long serialVersionUID = 1266045673208759912L;

	//Job名称
	private String jobName;
	//任务执行状态
	private ExitStatus exitStatus;
	//任务执行时附带的参数
	private Map<String,Object> jobParameters;

	/**
	 * @param source
	 */
	public DracoJobEvent(Object source) {
		super(source);
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
	 * @return the exitStatus
	 */
	public ExitStatus getExitStatus() {
		return exitStatus;
	}

	/**
	 * @param exitStatus the exitStatus to set
	 */
	public void setExitStatus(ExitStatus exitStatus) {
		this.exitStatus = exitStatus;
	}

	/**
	 * @return the jobParameters
	 */
	public Map<String, Object> getJobParameters() {
		return jobParameters;
	}

	/**
	 * @param jobParameters the jobParameters to set
	 */
	public void setJobParameters(Map<String, Object> jobParameters) {
		this.jobParameters = jobParameters;
	}

}
