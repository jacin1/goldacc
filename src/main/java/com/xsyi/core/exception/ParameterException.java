/**
 * 
 *
 */
package com.xsyi.core.exception;


/**
 * Description: <p></p>
 * Content Desc:<p>
 * <p>
 *  
 * </p>
 *<p>
 * @author tangtao7481
 * @version Mar 5, 2013
 * Since draco app
 * 
 */
public class ParameterException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3806767482011792376L;
	

	public ParameterException(){
		super();
	}
	
	public ParameterException(Exception ex){
		super(ex);
	}
	
	public ParameterException(String msg){
		super(msg);
	}
	
	public ParameterException(String msg,Throwable th){
		super(msg,th);
	}

}
