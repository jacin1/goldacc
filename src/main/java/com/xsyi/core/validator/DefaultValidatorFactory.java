/**
 * 
 *
 */
package com.xsyi.core.validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;

import com.xsyi.core.exception.ParameterException;
import com.xsyi.core.util.ScanUtils;



/**
 * Description: <p></p>
 * Content Desc:<p>
 * <p>
 *  
 * </p>
 *<p>
 * @author tangtao7481
 * @version Mar 6, 2013
 * Since draco app
 * 
 */
public class DefaultValidatorFactory implements ValidatorFactory, InitializingBean,MessageSourceAware {
	
	// 日志记录对象
	protected Log logger = LogFactory.getLog(DefaultValidatorFactory.class);
	
	//spring容器
	private MessageSource messageSource;
	
	private Map<String,Validator> validators;
	private String[] packageName;
	
	@Override
	public Validator getValidator(String beanName) {
		return validators.get(beanName);
	}
	
	@Override
	public void setMessageSource(MessageSource messagesource) {
		this.messageSource = messagesource;
	}

	@SuppressWarnings("rawtypes")
    @Override
	public void afterPropertiesSet() throws Exception {
		if (this.packageName == null) {
            throw new FatalBeanException("Unable to locate validation configuration. Property [classPath] is required.");
        }
		validators = new HashMap<String,Validator>();
		List<Class> validatorCustomerConvert = ScanUtils.scanPackage(packageName, Validator.class);
		if( validatorCustomerConvert!=null && validatorCustomerConvert.size()>0 ){
			for(Class cls: validatorCustomerConvert){
				Validator validator = (Validator)Class.forName(cls.getName()).newInstance();
				if( validators.containsKey(validator.getInstanceKey()) ) throw new ParameterException(cls.getSimpleName() + "在容器中存在重复的InstantKey值");
				validator.setMessageSource(messageSource);
				validators.put(validator.getInstanceKey(), validator);
			}
		}
	}

	/**
	 * @param packageName the packageName to set
	 */
	public void setPackageName(String[] packageName) {
		this.packageName = packageName;
	}

}
