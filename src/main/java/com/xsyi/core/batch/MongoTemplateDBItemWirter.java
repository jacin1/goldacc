/**
 * 
 *
 */
package com.xsyi.core.batch;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.Assert;

/**
 * <dl>
 * <dt><b>Title:</b>自定义实现Spring batch ItemWriter接口</dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b>使用MongoTemplate来操作事项Mongdb写入操作</dt>
 * <dd>
 * <p></dd>
 * </dl>
 * 
 * @author tangtao7481
 * @version , Feb 27, 2013 11:01:40 AM
 * 
 */
public class MongoTemplateDBItemWirter implements ItemWriter<Object>,InitializingBean {

	protected Log logger = LogFactory.getLog(getClass());
		
	/**
	 * use spring mongo tepmlate to persiste VO object to mongdb
	 */
	protected MongoTemplate mongoTemplate;

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(mongoTemplate, "A MongoTemplate instance is required");
	}

	@Override
	public void write(List<? extends Object> list) throws Exception {
		mongoTemplate.insertAll(list);
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	
}
