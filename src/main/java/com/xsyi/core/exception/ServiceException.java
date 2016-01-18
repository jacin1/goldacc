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
public class ServiceException extends BaseException {
	
	private static final long serialVersionUID = -8447165735176260580L;

	public ServiceException(){
		super();
	}
	
	public ServiceException(Exception ex){
		super(ex);
	}
	
	public ServiceException(String msg){
		super(msg);
	}
	
	public ServiceException(String msg,Throwable th){
		super(msg,th);
	}

}
