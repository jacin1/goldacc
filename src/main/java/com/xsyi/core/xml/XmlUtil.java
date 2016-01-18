/**
 * 
 *
 */
package com.xsyi.core.xml;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.core.io.Resource;

import com.xsyi.core.IConstants;
import com.xsyi.core.vo.JobParam;


/**
 * Description: <p></p>
 * Content Desc:<p>
 * <p>
 *  
 * </p>
 *<p>
 * @author tangtao7481
 * @version Mar 14, 2013
 * Since draco app
 * 
 */
public class XmlUtil {

	private static final String XM_LPARAM = "JobParam";
	private static final String XML_PARAM_KEY = "jobKey";
	
	/**
	 * 解析XML文件产生相应的Java Bean
	 * @param file 
	 * @return java.util.List<TreeNode>
	 */
	public static Map<String,JobParam> parseXmlToTreeNode(Resource file) throws Exception{
		return parseXmlToTreeNode(file.getFile().getAbsolutePath());
	}
	
	/**
	 * 解析XML文件产生相应的Java Bean
	 * @param file 
	 * @return java.util.List<TreeNode>
	 */
	public static Map<String,JobParam> parseXmlToTreeNode(String file) throws Exception{
		Map<String,JobParam> allActionParam = new HashMap<String,JobParam>();
		SAXBuilder builder=new SAXBuilder(Boolean.FALSE.booleanValue());
		Document doc = builder.build(file);
		@SuppressWarnings("unchecked")
		List<Element> jobParams= doc.getRootElement().getChildren(XM_LPARAM);
		for (@SuppressWarnings("rawtypes")
		Iterator iter = jobParams.iterator(); iter.hasNext();) {
			Element jobParam = (Element) iter.next();
			JobParam param = new JobParam(jobParam.getAttributeValue(XML_PARAM_KEY),
					jobParam.getAttributeValue(IConstants.JOB_INSTANCE_NAME_PARAMS),
					jobParam.getAttributeValue(IConstants.JOB_INSTANCE_PARAMS_VALIDATE_KEY),
					jobParam.getAttributeValue(IConstants.JOB_INSTANCE_PARAMS_QUERY_ID),
					jobParam.getAttributeValue(IConstants.JOB_INSTANCE_PARAMS_DB_TYPE));
			allActionParam.put(param.getJobKey(),param);
		}
		return allActionParam;
	}

	
}
