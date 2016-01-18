/**
 * 
 *
 */
package com.xsyi.core.event;

import org.springframework.context.ApplicationEvent;


/**
 * Description: <p>draoc app core base event</p>
 * Content Desc:<p>
 * <p>
 *  事件基类,所有draco event事件定义需继承该类
 * </p>
 *<p>
 * @author tangtao7481
 * @version Mar 7, 2013
 * Since draco app
 * 
 */
public abstract class DracoBaseEvent extends ApplicationEvent {

	private static final long serialVersionUID = -7611155674266486243L;
	
	/**
	 * 
	 * @param source
	 */
	public DracoBaseEvent(Object source) {
		super(source);
	}

}
