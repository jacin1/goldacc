/**
 * 
 *
 */
package com.xsyi.core.validator;

import java.util.List;
import java.util.Map;

import org.springframework.context.MessageSource;


/**
 * Description: <p>draco校验接口</p>
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
public interface Validator {
	
	/**
	 * 返回当前实例的唯一标识
	 * @return
	 */
	public String getInstanceKey();

	/**
	 * 从Map中取出所有参数来校验并返回结果集
	 * @param params
	 * @return
	 */
	public List<ValidatorResult> validate(Map<String,Object> params);
	
	/**
	 * 设置Spring容器的资源消息管理类
	 * @param messageSource
	 * @return
	 */
	public void setMessageSource(MessageSource messageSource);
	
	/**
	 * 是否检验的参数都通过了
	 * @return
	 */
	public boolean isValid(List<ValidatorResult> validatorResult);
	
}
