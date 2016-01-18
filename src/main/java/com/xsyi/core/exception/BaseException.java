/**
 * 
 *
 */
package com.xsyi.core.exception;

import java.io.PrintStream;
import java.io.PrintWriter;


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
public class BaseException extends Exception {

	private static final long serialVersionUID = 8031904095290374013L;
	
	/** Throwable nested property */
	private Throwable nested;
	
	/**
	 * default consturct
	 *
	 */
	public BaseException() {
		super();
	}
	/**
	 * construct with special message
	 * @param msg
	 */
	public BaseException(String msg){
		super(msg);
	}
	/**
	 * construct whie special message and throwable cause
	 * @param msg
	 * @param cause
	 */
	public BaseException(String msg,Throwable cause){
		super(msg,cause);
		nested = cause;
	}
	/**
	 * 
	 * @param cause
	 */
	public BaseException(Throwable cause){
		super(cause);
		nested = cause;
	}
	/**
	 * 
	 * @param ex
	 */
	public BaseException(Exception ex){
		super( ex.getMessage() );
		nested = ex;
	}
	/**
	 * 
	 */
    public void printStackTrace(PrintStream ps) {
    	super.printStackTrace(ps);
    	if(nested!=null) {
    	    try {
    	    	ps.println("==> Nested exception: ");
    	    } catch(Exception ex) {}
    	    nested.printStackTrace(ps);
    	}
    	
    }
    /**
     * 
     */
    public void printStackTrace(PrintWriter ps) {
    	super.printStackTrace(ps);
    	if(nested!=null) {
    	    try {
    		ps.println("==> Nested exception: ");
    	    } catch(Exception ex) {}
    	    nested.printStackTrace(ps);
    	}
    }

}
