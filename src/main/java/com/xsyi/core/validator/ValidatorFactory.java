/**
 * 
 *
 */
package com.xsyi.core.validator;

/**
 * Description: <p>参数校验工厂</p>
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
public interface ValidatorFactory {

    /**
     * 获取系统的的校验实例
     * @param beanName
     * @param params
     * @param errors
     * @return
     */
    public Validator getValidator(String beanName);

}