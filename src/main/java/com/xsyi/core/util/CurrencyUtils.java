/*
 * @(#)CurrencyUtils.java 1.0 2011-6-15下午08:53:56
 *
 */
package com.xsyi.core.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * 金额工具类</dd>
 * <p>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author eric
 * @version 1.0, 2011-6-15
 * @since framework
 * 
 */
public class CurrencyUtils {

	private static Log _logger = LogFactory.getLog(CurrencyUtils.class);

	private CurrencyUtils() { }
	
	/**
	 * 元格式， 如：XXXX.YZ，代表XXXX元Y分Z角
	 */
	public static String YUAN_FORMAT = "[\\d]+\\.[\\d]{2}";
	/**
	 * 分格式 ，如：XXXX，代表 XXXX分
	 */
	public static String CENT_FORMAT = "[\\d]+";

	public static String[] CHINESE_NUMBER_WORDS = new String[] { "零", "壹", "贰","叁", "肆", "伍", "陆", "柒", "捌", "玖" };

	public static String[] CHINESE_UNIT_WORDS = new String[] { "分", "角", "","元", "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿" };

	/**
	 * 将分格式的数值转换为元格式的数值 如1234->12.34,123->1.23
	 * 
	 * @param amount
	 * @return
	 */
	public static String transferCent2Yuan(String amount) {
		if (StringUtils.isBlank(amount) || !StringUtils.isNumeric(amount)) {
			return amount;
		}
		if (amount.length() == 1) {
			return "0.0" + amount;
		} else if (amount.length() == 2) {
			return "0." + amount;
		} else {
			return amount.substring(0, amount.length() - 2) + "."
					+ amount.substring(amount.length() - 2);
		}
	}

	/**
	 * 将以元为格式的数值转换为分为单位的数值 如12.34->1234
	 * 
	 * @param amount
	 * @return
	 */
	public static String transferYuan2Cent(String amount) {
		if (amount == null) {
			return "0";
		} else if (amount.trim().equals("")) {
			return "0";
		}

		try {
			double sAmt = Double.parseDouble(amount) * 100;
			java.text.DecimalFormat dmf = new java.text.DecimalFormat("#");
			return dmf.format(sAmt);
		} catch (Exception e) {
			_logger.error("金额转换出错[" + amount + "]");
			return "0";
		}
	}

	/**
	 * 将浮点数转换为0.00格式字串，如13.4->13.40,2->2.00
	 * 
	 * @param amount
	 * @return
	 */
	public static String format2YuanFormat(double amount) {
		return new DecimalFormat("0.00").format(amount);
	}

	public static String format2FourFormat(double amount) {
		return new DecimalFormat("0.0000").format(amount);
	}

	/**
	 * 将浮点数转换为含千分位的###,##0.00格式字串，如如 1234567转换后为1,234,567.00
	 * 
	 * @param amount
	 * @return
	 */
	public static String format2CurrencyFormat(double amount) {
		return new DecimalFormat("###,##0.00").format(amount);
	}

	/**
	 * 将单个数字变成大写的汉字，如1->壹
	 * 
	 * @param number
	 *            单个数字
	 * @return 对应汉字
	 */
	public static String format2Chinese(int number) {
		return CHINESE_NUMBER_WORDS[number];
	}

	/**
	 * 对double数据进行取精度.
	 * 
	 * @param value
	 *            double数据.
	 * @param scale
	 *            精度位数(保留的小数位数). BigDecimal.ROUND_HALF_UP 是我们大多数人在小学就学过的舍入模式
	 * @param roundingMode
	 *            精度取值方式.
	 * @return 精度计算后的数据.
	 */
	public static double roundDouble(double value, int scale, int roundingMode) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(scale, roundingMode);
		double d = bd.doubleValue();
		bd = null;
		return d;
	}

	/**
	 * 对double数据进行取精度.
	 * 
	 * @param value
	 *            double数据.
	 * @param scale
	 *            精度位数(保留的小数位数). BigDecimal.ROUND_HALF_UP 是我们大多数人在小学就学过的舍入模式
	 * @param roundingMode
	 *            精度取值方式.
	 * @return 精度计算后的数据.
	 */
	public static double roundDouble(String value, int scale, int roundingMode) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(scale, roundingMode);
		double d = bd.doubleValue();
		bd = null;
		return d;
	}

	/**
	 * double 相加
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double sumDouble(double d1, double d2) {
		return sumDouble(Double.toString(d1), Double.toString(d2));
	}

	/**
	 * double 相加
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double sumDouble(String d1, String d2) {
		BigDecimal bd1 = new BigDecimal(d1);
		BigDecimal bd2 = new BigDecimal(d2);
		return bd1.add(bd2).doubleValue();
	}

	/**
	 * double 相减
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double subDouble(double d1, double d2) {
		BigDecimal bd1 = new BigDecimal(Double.toString(d1));
		BigDecimal bd2 = new BigDecimal(Double.toString(d2));
		return bd1.subtract(bd2).doubleValue();
	}

	public static double subDouble(String d1, String d2) {
		BigDecimal bd1 = new BigDecimal(d1);
		BigDecimal bd2 = new BigDecimal(d2);
		return bd1.subtract(bd2).doubleValue();
	}

	/**
	 * double 乘法
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double mulDouble(double d1, double d2) {
		BigDecimal bd1 = new BigDecimal(Double.toString(d1));
		BigDecimal bd2 = new BigDecimal(Double.toString(d2));
		return bd1.multiply(bd2).doubleValue();
	}
	
	/**
	 * double 除法
	 * 
	 * @param d1
	 * @param d2
	 * @param scale
	 *            四舍五入 小数点位数
	 * @return
	 */
	public static double divDouble(double d1, double d2, int scale) {
		// 当然在此之前，你要判断分母是否为0，
		// 为0你可以根据实际需求做相应的处理
		BigDecimal bd1 = new BigDecimal(Double.toString(d1));
		BigDecimal bd2 = new BigDecimal(Double.toString(d2));
		return bd1.divide(bd2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	
	/**
	 * 
	 * @param bd1
	 * @param bd2
	 * @return
	 */
	public static BigDecimal divBigDecimal(BigDecimal bd1, BigDecimal bd2) {
		return bd1.divide(bd2, 2, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 
	 * @param bd1
	 * @param bd2
	 * @param scale
	 * @return
	 */
	public static BigDecimal divDouble(BigDecimal bd1, BigDecimal bd2, int scale) {
		return bd1.divide(bd2, scale, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 把double类型的转换为 字符串，防止科学计数法
	 * 
	 * @param amt
	 * @return
	 */
	public static String convertDoubleToString(double amt) {
		BigDecimal toAmt = new BigDecimal(amt);
		return toAmt.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	}
	
	/**
	 * 把double类型的转换为 字符串，防止科学计数法
	 * 
	 * @param amt
	 * @return
	 */
	public static double convertStringToDouble(String amt) {
		return new BigDecimal(amt).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	
	/**
     * 把double类型的转换为 字符串，防止科学计数法
     * 
     * @param amt
     * @return
     */
    public static double convertStringToDouble(String amt,int scale) {
        return new BigDecimal(amt).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
	/**
	 * 将BigDecimal转换成double类型
	 * @param bd
	 * @return
	 */
	public static double convertBigDecimalToDouble(BigDecimal bd){
		if( bd==null ) return 0.0;
		return bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 按指定的保留小数将BigDecimal转换成double类型
	 * @param bd
	 * @param scale
	 * @return
	 */
	public static double convertBigDecimalToDouble(BigDecimal bd,int scale){
        if( bd==null ) return 0.0;
        return bd.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
	
	/**
	 * 
	 * @param bi
	 * @return
	 */
	public static long convertBigIntegerToLong(BigInteger bi){
		if( bi==null ) return 0;
		return bi.longValue();
	}
	
	/**
	 * 
	 * @param bi
	 * @return
	 */
	public static int convertBigIntegerToInt(BigInteger bi){
		if( bi==null ) return 0;
		return bi.intValue();
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static int convertStringToInt(String value){
		if( StringUtils.isEmpty(value) ) return 0;
		if( !NumberUtils.isNumber(value) ) return 0;
		try{
			return Integer.parseInt(value);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return 0;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static long convertStringToLong(String value){
		if( StringUtils.isEmpty(value) ) return 0;
		if( !NumberUtils.isNumber(value) ) return 0;
		try{
			return Long.parseLong(value);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return 0;
	}
}
