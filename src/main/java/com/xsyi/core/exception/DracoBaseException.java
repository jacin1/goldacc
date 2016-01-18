/**
 * 
 *
 */
package com.xsyi.core.exception;

/**
 * <dl>
 *    <dt><b>Title:</b>App异常基类</dt>
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
 * @version , Feb 7, 2013 1:19:30 PM
 * 
 */
public class DracoBaseException extends Exception {

	private static final long serialVersionUID = 5727524228014106815L;
	
	/**
	 * 
	 */
	public DracoBaseException() {
		
	}

	/**
	 * @param message
	 */
	public DracoBaseException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public DracoBaseException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public DracoBaseException(String message, Throwable cause) {
		super(message, cause);
	}

}
