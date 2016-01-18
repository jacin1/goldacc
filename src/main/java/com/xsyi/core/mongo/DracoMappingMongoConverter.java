package com.xsyi.core.mongo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;

import com.xsyi.core.util.ScanUtils;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
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
 * @version , Mar 27, 2013 8:58:13 PM
 * 
 */
public class DracoMappingMongoConverter extends MappingMongoConverter {

	private String[] packageName;
	
	/**
	 * @param mongoDbFactory
	 * @param mappingContext
	 */
	public DracoMappingMongoConverter(MongoDbFactory mongoDbFactory,
			MappingContext<? extends MongoPersistentEntity<?>, MongoPersistentProperty> mappingContext,String packageName) {
		super(mongoDbFactory, mappingContext);
		this.packageName = new String[]{packageName};
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@SuppressWarnings("rawtypes")
    public void afterPropertiesSet() {
		//初始化自定义的Convert实现类
		super.afterPropertiesSet();
		try{
			List<Class> mongdbCustomerConvert = ScanUtils.scanPackage(packageName, Converter.class);
			if( mongdbCustomerConvert!=null && mongdbCustomerConvert.size()>0 ){
				List<Object> converters = new ArrayList<Object>();
				for(Class cls:mongdbCustomerConvert){
					converters.add(Class.forName(cls.getName()).newInstance());
					this.conversionService.addConverter((org.springframework.core.convert.converter.Converter<?, ?>) Class.forName(cls.getName()).newInstance());
				}
				CustomConversions cc = new CustomConversions(converters);
				this.setCustomConversions(cc);
			}
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}

}
