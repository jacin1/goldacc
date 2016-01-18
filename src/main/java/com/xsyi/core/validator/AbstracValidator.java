/**
 * 
 *
 */
package com.xsyi.core.validator;

import java.util.List;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;


/**
 * Description: <p>draco内部校验实现基础类</p>
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
public abstract class AbstracValidator implements Validator {
	
    //日志记录对象
    protected Log logger = LogFactory.getLog(getClass());
    
	private MessageSource messageSource;
	
	/**
	 * 设置校验的参数结果消息
	 * @param key
	 * @param args
	 * @param local
	 * @return
	 */
	public String getValidResultMessage(String key,String[] args,Locale local){
		return messageSource.getMessage(key, args, local);
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/* (non-Javadoc)
	 * @see com.draco.core.validator.Validator#getInstanceKey()
	 */
	@Override
	public String getInstanceKey() {
		return this.getClass().getSimpleName();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.draco.core.validator.Validator#isValid()
	 */
	public boolean isValid(List<ValidatorResult> validatorResult){
		if( validatorResult==null ) return Boolean.TRUE.booleanValue();
		for(int i=0;i<validatorResult.size();i++ ){
			if( !validatorResult.get(i).isValid() ) return Boolean.FALSE.booleanValue();
		}
		return Boolean.TRUE.booleanValue();
	}
	
}
