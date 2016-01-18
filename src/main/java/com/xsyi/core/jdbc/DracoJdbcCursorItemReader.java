/**
 * 
 */
package com.xsyi.core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.database.AbstractCursorItemReader;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import com.xsyi.core.mybatis.MyBatisSql;
import com.xsyi.core.mybatis.MyBatisSqlUtils;
import com.xsyi.core.util.ReflectionUtils;

/**
 * 
 * @author tangtao7481
 * @version $Id: DracoJdbcCursorItemReader.java, v 0.1 Apr 2, 2013 1:33:02 PM tangtao7481 Exp $
 */
@SuppressWarnings("rawtypes")
public class DracoJdbcCursorItemReader<T> extends AbstractCursorItemReader<T> implements StepExecutionListener {
    
    // 日志记录对象
    protected Log logger = LogFactory.getLog(getClass());
    
    private static final String JOB_PARAM_FILED_NAME = "parameters";

    PreparedStatement preparedStatement;

    PreparedStatementSetter preparedStatementSetter;

    String sql;

    RowMapper rowMapper;
    
    private SqlSessionFactory sqlSessionFactory;
    
    private StepExecution stepExecution;
    
    //是否使用原有系统中采用的jdbc cursor方式来访问数据
    private boolean useNative = Boolean.TRUE.booleanValue();
    //运行时指定获取的mybatis中指定的map id值
    private String mybatisQueryId;
    
    public DracoJdbcCursorItemReader() {
        super();
        setName(ClassUtils.getShortName(DracoJdbcCursorItemReader.class));
    }

    /**
     * Set the RowMapper to be used for all calls to read().
     * 
     * @param rowMapper
     */
    public void setRowMapper(RowMapper rowMapper) {
        this.rowMapper = rowMapper;
    }

    /**
     * Set the SQL statement to be used when creating the cursor. This statement
     * should be a complete and valid SQL statement, as it will be run directly
     * without any modification.
     * 
     * @param sql
     */
    public void setSql(String sql) {
        this.sql = sql;
    }

    /**
     * Set the PreparedStatementSetter to use if any parameter values that need
     * to be set in the supplied query.
     * 
     * @param preparedStatementSetter
     */
    public void setPreparedStatementSetter(PreparedStatementSetter preparedStatementSetter) {
        this.preparedStatementSetter = preparedStatementSetter;
    }

    /**
     * Assert that mandatory properties are set.
     * 
     * @throws IllegalArgumentException if either data source or sql properties
     * not set.
     */
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        if( useNative ) {
            //使用原有逻辑进行操作
            Assert.notNull(sql, "The SQL query must be provided");
            Assert.notNull(rowMapper, "RowMapper must be provided");
        }else{
            //使用扩展的JDBC Cursor服务来操作
            Assert.notNull(sqlSessionFactory,"The SqlSessionFactory must be provided");
            Assert.notNull(mybatisQueryId, "The Mybatis SQL query id must be provided");
        }
    }

    /**
     * Setter method for property <tt>sqlSessionFactory</tt>.
     * 
     * @param sqlSessionFactory value to be assigned to property sqlSessionFactory
     */
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    protected void openCursor(Connection con) { 
        try {
            //如果采用从mybatis中获取动态运行的参数，则从mybatis中动态获取产生的mysql
            if( !useNative ){
                logger.info("JDBCCursor从Mybatis配置的xml文件中获取sql语句...");
                Assert.notNull(stepExecution, "The Runtime JobParameters value must must be provided");
                MyBatisSql myBatisSql = MyBatisSqlUtils.getMyBatisSql(this.mybatisQueryId, this.convertJobParametersToMapParams(stepExecution.getJobParameters()), sqlSessionFactory);
                if( myBatisSql ==null ){
                    Assert.notNull(myBatisSql,"无法获取指定mybatis框架映射文件中指定的SqlId");
                }
                this.sql = myBatisSql.toString();
            }
            Assert.notNull(sql, "The SQL query must be provided");
            if (isUseSharedExtendedConnection()) {
                preparedStatement = con.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY,
                        ResultSet.HOLD_CURSORS_OVER_COMMIT);
            }
            else {
                preparedStatement = con.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            }
            logger.info("JDBCCursor执行的SQL语句：" + this.sql);
            applyStatementSettings(preparedStatement);
            if( useNative ){
                if (this.preparedStatementSetter != null) {
                    preparedStatementSetter.setValues(preparedStatement);
                }
            }
            this.rs = preparedStatement.executeQuery();
            handleWarnings(preparedStatement);
        }
        catch (SQLException se) {
            close();
            throw getExceptionTranslator().translate("Executing query", getSql(), se);
        }
    }

    @SuppressWarnings("unchecked")
    protected T readCursor(ResultSet rs, int currentRow) throws SQLException {
        return (T) rowMapper.mapRow(rs, currentRow);
    }
    
    /**
     * Close the cursor and database connection.
     */
    protected void cleanupOnClose() throws Exception {
        JdbcUtils.closeStatement(this.preparedStatement);
    }

    /**
     * Setter method for property <tt>useNative</tt>.
     * 
     * @param useNative value to be assigned to property useNative
     */
    public void setUseNative(boolean useNative) {
        this.useNative = useNative;
    }

    @Override
    public String getSql() {
        return this.sql;
    }
    
    /**
     * Setter method for property <tt>mybatisQueryId</tt>.
     * 
     * @param mybatisQueryId value to be assigned to property mybatisQueryId
     */
    public void setMybatisQueryId(String mybatisQueryId) {
        this.mybatisQueryId = mybatisQueryId;
    }
    
    /**
     * 把JobParameters参数转换成Map<String,Object>参数
     * 
     * @param jobParameters
     * @return
     */
    private Map<String,Object> convertJobParametersToMapParams(JobParameters jobParameters) throws SQLException{
        if( jobParameters!=null ){
            try{
                //通过反射机制获取JobParameter神中的parameters值得
                @SuppressWarnings("unchecked")
                Map<String,Object> jobParams = (Map<String,Object>)ReflectionUtils.getFieldValue(jobParameters, JOB_PARAM_FILED_NAME);
                if( jobParams!=null && jobParams.size()>0 ){
                    Map<String,Object> parameterValues = new HashMap<String,Object>(jobParams.size());
                    for(Iterator<String> it=jobParams.keySet().iterator();it.hasNext();){
                        String key = it.next();
                        parameterValues.put(key, ((JobParameter)jobParams.get(key)).getValue());
                    }
                    return parameterValues;
                }
            }catch(NoSuchFieldException ex){
               throw new SQLException("动态获取Job请求参数失败");
            }
        }
        return null;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
       this.stepExecution = stepExecution;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return stepExecution.getExitStatus();
    }

}
