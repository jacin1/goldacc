/**
 * 
 *
 */
package com.xsyi.core.file;

import java.io.File;
import java.io.FilenameFilter;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
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
 * @version , Feb 7, 2013 1:26:09 PM
 * 
 */
public class FileSuffixFilter implements FilenameFilter {

	private String suffix;
	
	public FileSuffixFilter(String suffix) {
		super();
		this.suffix = suffix;
	}

	/* (non-Javadoc)
	 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
	 */
	@Override
	public boolean accept(File dir, String name) {
		if (name.endsWith(suffix)){
			return true;
		} else {
			return false;
		}
	}
}
