/**
 * 
 *
 */
package com.xsyi.core.validator;

import com.xsyi.core.IConstants;


/**
 * Description: <p>draco校验接口结果</p>
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
public class ValidatorResult {

	//校验的Key参数名
	private String key;
	//校验的Value值
	private String value;
	//是否校验通过
	private boolean isValid;
	//校验结果
	private String resultMsg;
	
	/**
	 * 
	 */
	public ValidatorResult(){
		
	}
	
	/**
	 * @param key
	 * @param value
	 * @param isValid
	 */
	public ValidatorResult(String key, String value, boolean isValid) {
		super();
		this.key = key;
		this.value = value;
		this.isValid = isValid;
	}
	/**
	 * 
	 * @param key
	 * @param value
	 * @param isValid
	 * @param resultMsg
	 */
	public ValidatorResult(String key, String value, boolean isValid,
			String resultMsg) {
		super();
		this.key = key;
		this.value = value;
		this.isValid = isValid;
		this.resultMsg = resultMsg;
	}
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the isValid
	 */
	public boolean isValid() {
		return isValid;
	}
	/**
	 * @param isValid the isValid to set
	 */
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	/**
	 * @return the resultMsg
	 */
	public String getResultMsg() {
		return resultMsg;
	}
	/**
	 * @param resultMsg the resultMsg to set
	 */
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new StringBuffer(this.key).append(IConstants.BLANK).append(this.value)
            .append(IConstants.BLANK).append(this.resultMsg).toString();
    }
	
}
