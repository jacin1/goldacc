/**
 * 
 */
package com.xsyi.core.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * 系统启动时进行系统检查
 * @author tangtao7481
 * @version $Id: SystemRunCheck.java, v 0.1 Apr 18, 2013 12:57:40 PM tangtao7481 Exp $
 */
//@Component
public class SystemRunCheck implements InitializingBean {
    
    // 日志记录对象
    protected Log logger = LogFactory.getLog(getClass());

    @Autowired
    private MongoTemplate mongoTemplate;
    
    /** 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        checkSystemStatus();
    }
    
    /*
     * 检查系统启动时外部数据库是否连接正常
     */
    private void checkSystemStatus() throws Exception{
        //检查Mongodb连接是否正常，主要是因为mongdb使用时才去获取连接，所有启动时做个检查
        logger.info("系统启动时,检查Mongodb连接是否正常，检查中....");
        mongoTemplate.createCollection("mongodb_conn_test");
        mongoTemplate.dropCollection("mongodb_conn_test");
        logger.info("Mongodb连接正常，检查结束.....");
    }

}
