/**
 * 
 *
 */
package com.xsyi.core.batch;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import com.xsyi.core.IConstants;
import com.xsyi.core.db.CustomerContextHolder;
import com.xsyi.core.util.ReflectionUtils;

/**
 * Description: <p></p>
 * Content Desc:<p>
 * <p>
 *  
 * </p>
 *<p>
 * @author tangtao7481
 * @version Mar 13, 2013
 * Since draco app
 * 
 */
public class MyBatisPagingItemReader<T> extends AbstractPagingItemReader<T> implements StepExecutionListener,IConstants{

	protected Log logger = LogFactory.getLog(getClass());

	private static final String JOB_PARAM_FILE_NAME = "parameters";
	
	private String queryId;

	private SqlSessionFactory sqlSessionFactory;

	private SqlSessionTemplate sqlSessionTemplate;

	private Map<String, Object> parameterValues;

	public MyBatisPagingItemReader() {
		setName(ClassUtils.getShortName(MyBatisPagingItemReader.class));
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	public void setQueryId(String queryId) {
		this.queryId = queryId;
	}

	/**
	 * The parameter values to be used for the query execution.
	 * 
	 * @param parameterValues
	 *            the values keyed by the parameter named used in the query
	 *            string.
	 */
	public void setParameterValues(Map<String, Object> parameterValues) {
		this.parameterValues = parameterValues;
	}

	/**
	 * Check mandatory properties.
	 * 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		Assert.notNull(sqlSessionFactory);
		sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
	}

	@Override
	protected void doReadPage() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		String queryIdParam = null;
		if (parameterValues != null) {
			parameters.putAll(parameterValues);
			if( StringUtils.isNotBlank((String)parameters.get(JOB_INSTANCE_PARAMS_QUERY_ID)) ){
				queryIdParam = (String)parameters.get(JOB_INSTANCE_PARAMS_QUERY_ID);
			}
			String dbType = (String)parameterValues.get(IConstants.JOB_INSTANCE_PARAMS_DB_TYPE);
			if( StringUtils.isNotBlank(dbType) ){
				//设置使用的数据源
				if( CustomerContextHolder.DatasourceType.RAC.toString().equals(dbType) ){
					CustomerContextHolder.setDatasourceType(CustomerContextHolder.DatasourceType.RAC);
				}else if( CustomerContextHolder.DatasourceType.JIRA.toString().equals(dbType)){
					CustomerContextHolder.setDatasourceType(CustomerContextHolder.DatasourceType.JIRA);
				}
			}
		}
		parameters.put("_page", getPage());
		parameters.put("_pagesize", getPageSize());
		parameters.put("_skiprows", getPage() * getPageSize());
		if (results == null) {
			results = new CopyOnWriteArrayList<T>();
		} else {
			results.clear();
		}
		results.addAll(sqlSessionTemplate.<T> selectList(queryIdParam==null ? queryId:queryIdParam, parameters));
		CustomerContextHolder.clearDatasourceType();
	}

	@Override
	protected void doJumpToPage(int itemIndex) {
		//do nothing
	}
	
	public void beforeJob(JobExecution jobExecution){
		
	}

    @Override
    public void beforeStep(StepExecution stepExecution) {
        JobParameters jobParameters = stepExecution.getJobParameters();
        if( jobParameters!=null ){
            try{
                //通过反射机制获取JobParameter神中的parameters值得
                @SuppressWarnings("unchecked")
                Map<String,Object> jobParams = (Map<String,Object>)ReflectionUtils.getFieldValue(jobParameters, JOB_PARAM_FILE_NAME);
                if( jobParams!=null && jobParams.size()>0 ){
                    parameterValues = new HashMap<String,Object>(jobParams.size());
                    for(Iterator<String> it=jobParams.keySet().iterator();it.hasNext();){
                        String key = it.next();
                        parameterValues.put(key, ((JobParameter)jobParams.get(key)).getValue());
                    }
                    this.setParameterValues(parameterValues);
                }
            }catch(NoSuchFieldException ex){
                logger.error("获取JobInstance执行时使用的参数失败",ex);
            }
        }
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return stepExecution.getExitStatus();
    }

	
}
