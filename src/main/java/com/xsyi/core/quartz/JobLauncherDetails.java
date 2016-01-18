/**
 * 
 *
 */
package com.xsyi.core.quartz;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * <dl>
 *    <dt><b>Title:</b>使用第三方调度器JobDetail</dt>
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
 * @version , Feb 28, 2013 1:29:22 PM
 * 
 */
public class JobLauncherDetails extends QuartzJobBean {

	/**
	 * Special key in job data map for the name of a job to run.
	 */
	static final String JOB_NAME = "jobName";

	private static Log log = LogFactory.getLog(JobLauncherDetails.class);

	private JobLocator jobLocator;

	private JobLauncher jobLauncher;

	/**
	 * Public setter for the {@link JobLocator}.
	 * @param jobLocator the {@link JobLocator} to set
	 */
	public void setJobLocator(final JobLocator jobLocator) {
		this.jobLocator = jobLocator;
	}

	/**
	 * Public setter for the {@link JobLauncher}.
	 * @param jobLauncher the {@link JobLauncher} to set
	 */
	public void setJobLauncher(final JobLauncher jobLauncher) {
		this.jobLauncher = jobLauncher;
	}

	@SuppressWarnings("unchecked")
	protected void executeInternal(final JobExecutionContext context) {
		Map<String,Object> jobDataMap = context.getMergedJobDataMap();
		String jobName = (String)jobDataMap.get(JOB_NAME);
		log.info("Quartz trigger firing with Spring Batch jobName="+jobName);
		JobParameters jobParameters = getJobParametersFromJobMap(jobDataMap);
		try {
			jobLauncher.run(jobLocator.getJob(jobName), jobParameters);
		} catch (NoSuchJobException e) {
		  log.info("Does job with jobname=" + jobName + " exist?");
		}
		catch (JobExecutionException e) {
			log.error("Could not execute job.", e);
		}
	}

	/**
	 * Copy parameters that are of the correct type over to
	 * {@link JobParameters}, ignoring jobName.
	 * 
	 * @return a {@link JobParameters} instance
	 */
  @SuppressWarnings("unchecked")
  private JobParameters getJobParametersFromJobMap(final Map<String, Object> jobDataMap) {
		JobParametersBuilder builder = new JobParametersBuilder();
		for (Iterator iterator = jobDataMap.entrySet().iterator(); iterator.hasNext();) {
			Entry entry = (Entry) iterator.next();
			String key = (String) entry.getKey();
			Object value = entry.getValue();
			if (value instanceof String && !key.equals(JOB_NAME)) {
				builder.addString(key, (String) value);
			}
			else if (value instanceof Float || value instanceof Double) {
				builder.addDouble(key, (Double) value);
			}
			else if (value instanceof Integer || value instanceof Long) {
				builder.addLong(key, (Long) value);
			}
			else if (value instanceof Date) {
				builder.addDate(key, (Date) value);
			}
			else {
				log.debug("JobDataMap contains values which are not job parameters (ignoring).");
			}
		}
		
		builder.addLong("currTime", new Long(System.currentTimeMillis()));
		
		return builder.toJobParameters();
	}

}
