/**
 * 
 *
 */
package com.xsyi.core;

import java.util.Locale;

/**
 * <dl>
 *    <dt><b>Title:draco system 系统常量接口</b></dt>
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
 * @version , Feb 6, 2013 3:48:34 PM
 * 
 */
public interface IConstants {

	/** The name of the ResourceBundle used in this application */
    public static final String BUNDLE_KEY = "ApplicationResources";
    
    /** The default cache name for data_process */
    public static final String DEFAULT_DRACO_CACHE_NAME = "dracoCacheName";
    
    //-----------------------------【***FIX】-----------------------//	
  	public static final String CLASSPATH_PREFIX = "classpath:";		
  	public static final String HBMFILES_SUFFIX =  ".hbm.xml";
  	public static final String PROPERTIES_SUFFIX = ".properties";
  	public static final String CLASS_SUFFIX = ".class";	
  	
    //-------------------------------【SYMBOL】-----------------------//
  	public static final String BLANK = " ";
  	public static final String SPACE = " ";
  	public static final String BANG = "!";
  	public static final String QUESTION_MARK = "?";	
  	public static final String COMMA = ",";			
  	public static final String POINT = ".";
  	public static final String COLON = ":";
  	public static final String SEMICOLON = ";";
  	public static final String QUOTE = "'";
  	public static final String SINGLE_QUOTE="\'";
  	public static final String DOUBLE_QUOTE="\"";
  	public static final String STAR = "*";
  	public static final String PLUS = "+";
  	public static final String DASH = "-";
  	public static final String EQUAL = "=";
  	public static final String SLASH = "/";
  	public static final String BACK_SLASH = "\\";
  	public static final String PIPE = "|";
  	public static final String UNDERLINE = "_";
  	public static final String DOLOR = "$";
  	public static final String AT = "@";
  	public static final String CROSS_HATCH = "#";
  	public static final String PERCENT = "%";
  	public static final String AND = "&";
  	public static final String CIRCUMFLEX = "^";	
  	public static final String TILDE = "~";
  	public static final String LEFT_BRACE = "{";
  	public static final String RIGHT_BRACE = "}";
  	public static final String LEFT_BRACKET = "[";
  	public static final String RIGHT_BRACKET = "]";
  	public static final String LEFT_ANGLE_BRACKET = "<";
  	public static final String RIGHT_ANGLE_BRACKET = ">";	
  	public static final String LEFT_PARENTHESES = "(";
  	public static final String RIGHT_PARENTHESES = ")";
  	
  	public static final String LINE_CHANGE_SYMBOL = "\n";
  	public static final String ENTER_SYMBOL = 	"\r";
  	
  	//-----------------------------【LOCALE】-----------------------//
  	public static final String[] ALL_LOCALES_STRING = {
		Locale.ENGLISH.toString(),
		Locale.FRENCH.toString(),
		Locale.GERMAN.toString(),
		Locale.ITALIAN.toString(),
		Locale.JAPANESE.toString(),
		Locale.KOREAN.toString(),
		Locale.CHINESE.toString(),
		Locale.SIMPLIFIED_CHINESE.toString(),
		Locale.TRADITIONAL_CHINESE.toString(),
		Locale.FRANCE.toString(),
		Locale.GERMANY.toString(),
		Locale.ITALY.toString(),
		Locale.JAPAN.toString(),
		Locale.KOREA.toString(),
		Locale.CHINA.toString(),
		Locale.PRC.toString(),
		Locale.TAIWAN.toString(),
		Locale.UK.toString(),
		Locale.US.toString(),
		Locale.CANADA.toString(),
		Locale.CANADA_FRENCH.toString()
	}; 
	
  	//--Spring batch constant 请求运行Job实例参数名
  	public static final String JOB_INSTANCE_NAME_PARAMS = "jobName";
  	public static final String JOB_INSTANCE_PARAMS_VALIDATE_KEY = "jobValidator";
  	//mybatis分页查询时使用的ID号key
  	public static final String JOB_INSTANCE_PARAMS_QUERY_ID = "jobQueryId";
  	//mybatis分页时使用的数据库类型
  	public static final String JOB_INSTANCE_PARAMS_DB_TYPE = "dbType";
  	
  	
  	/**
  	 * POS商户成本核算表报中用到的一些敞亮
  	 */
  	public static final String POS_LOG_SUCCESS_STAT = "S";
  	//POS交易支付收款类型-1001
  	public static final String POS_TRANS_TYPE_1001 = "1001";
  	//POS支付收款冻结余额退款--2055
  	public static final String POS_TRANS_TYPE_2055 = "2055";
  	//POS账户付款（从冻结金额--2056
  	public static final String POS_TRANS_TYPE_2056 = "2056";
  	//POS支付收款退款失败--1008
  	public static final String POS_TRANS_TYPE_1008 = "1008";
  	//POS退款手续费返还
  	public static final String POS_TRANS_TYPE_1208 = "1208";
  	public static final String POS_LOG_HF_TYPE = "01";
  	public static final String POS_LOG_SANDE_TYPE = "02";
  	//POS交易明细网关(包括直连和非直连的网关)
  	public static final String POS_LOG_GATES = "S2,U1,U2,U3,U4,U6,U5,U8,U9,UA,UB,UC,UD,UE";
  	//POS交易行业成本费率类型费率类型--G:通用型,T:封顶型,C:按笔数
  	public static final String POS_COST_RATE_MOENY_UNIT_CN = "元";
  	public static final String POS_COST_RATE_TEYP_G = "通用";
  	public static final String POS_COST_RATE_TEYP_T = "封顶";
  	public static final String POS_COST_RATE_TEYP_C = "按笔计算";
}
