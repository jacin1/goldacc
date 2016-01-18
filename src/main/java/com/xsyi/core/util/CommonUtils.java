/**
 * 
 *
 */
package com.xsyi.core.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;

import com.xsyi.core.IConstants;

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
 * @version , Feb 27, 2013 5:29:44 PM
 * 
 */
public class CommonUtils {

	protected static final Log logger = LogFactory.getLog(CommonUtils.class);
	
	public static final int SIZE=100;
	
	/**
	 * 将下划线分割的字串转换为首字母小写的驼峰式字串
	 * @param input “aaa_bbb_ccc”
	 * @return output "aaaBbbCcc"
	 */
	public static String underscoresToUncapHump(String input){
		return WordUtils.uncapitalize(underscoresToHump(input));
	}
	
	/**
	 * 将下划线分割的字串转换为驼峰式字串
	 * @param input “aaa_bbb_ccc”
	 * @return output "AaaBbbCcc"
	 */
	public static String underscoresToHump(String input){
		return StringUtils.remove(WordUtils.capitalize(input.toLowerCase(), IConstants.UNDERLINE.toCharArray()), IConstants.UNDERLINE);
	}
	
	/**
	 * 将Spring JobParameters 中的参数全部取出来放入到HashMap中
	 * @param jobParams
	 */
	public static Map<String,Object> convertJobParametersToHashMap(JobParameters jobParameters){
		try{
			//通过反射机制获取JobParameter神中的parameters值得
			@SuppressWarnings("unchecked")
			Map<String,Object> jobParams = (Map<String,Object>)ReflectionUtils.getFieldValue(jobParameters, "parameters");
			if( jobParams!=null && jobParams.size()>0 ){
				Map<String,Object> parameterValues = new HashMap<String,Object>(jobParams.size());
				for(Iterator<String> it=jobParams.keySet().iterator();it.hasNext();){
					String key = it.next();
					parameterValues.put(key, ((JobParameter)jobParams.get(key)).getValue());
				}
				return parameterValues;
			}
		}catch(NoSuchFieldException ex){
			logger.error("获取JobInstance执行时使用的参数失败",ex);
		}
		return null;
	}
	
	
	
	
	/**
	 * 将List<String> 拼接成str型，如'a','b','c',用于select * where ... in(strIds)
	 * 
	 * @param listIds
	 * @return strIds
	 */
	public static String strIdsFromListIds(List<String> listIds) {
		String strIds = "";
		StringBuffer idsBuffer = new StringBuffer();
		for (String id : listIds) {
			idsBuffer.append(IConstants.QUOTE).append(id).append(IConstants.QUOTE)
					.append(IConstants.COMMA);
		}
		strIds = StringUtils.removeEnd(idsBuffer.toString(), IConstants.COMMA);
		if (StringUtils.isBlank(strIds)) {
			strIds = "''";
		}
		return strIds;
	}
	
	/**
	 * 将指定商户下所有开户的用户
	 * 的custId拼接成类似如：'1000','1001','1002'这样的字符串，
	 * 每个长度为100个custId,最后一个字符串长度根据实际情况而定  
	 * @param usrOpenMerIds
	 * @return
	 */
	public static List<String> getSqlFromList(List<String> usrOpenFundList) {
		
		//开通指定商户的客户记录总数
		int custIdSize=usrOpenFundList.size();
		 // 获得需要分组的组数
		int groupNum = custIdSize%SIZE==0?custIdSize / SIZE:custIdSize / SIZE+1 ;

		List<String> sqlFromList=new ArrayList<String>();
		for (int i = 1; i <= groupNum; i++) {
			
			int begin = SIZE * (i-1);
			
            // 获得最终的index
            int end = SIZE * i > custIdSize ? custIdSize-1 : SIZE * i - 1;
        	
            List<String> pageListIds = new ArrayList<String>();//每个分页里的informId列表
            
            for (int j = begin; j <= end; j++) {
            	pageListIds.add(usrOpenFundList.get(j));
            }
            
            String strIds=strIdsFromListIds(pageListIds);
            sqlFromList.add(strIds);
		}
		
		return sqlFromList;
	}
	
	/**
	 * Description:将相应的时间跟改为Unix_时间
	 * 
	 * 
	 */
	
	public static int  getUnixTime(Date date){
		int unixTime=0;
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		   System.out.println(format.format(date));
		  Date date1;
		try {
			date1 = format.parse(format.format(date));
			unixTime=(int)(date1.getTime()/1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return unixTime;
	}
}
