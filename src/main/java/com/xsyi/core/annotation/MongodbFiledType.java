/**
 * 
 */
package com.xsyi.core.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 
 * @author tangtao7481
 * @version $Id: MongodbFiledType.java, v 0.1 Mar 29, 2013 10:30:49 AM tangtao7481 Exp $
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface MongodbFiledType {

    public enum Types{
        INT, //整形
        String,//整形
        Boolean,//布尔值类型
        Date,//日期类型
        Double,//双精度型号
        IGNORE//忽视
    }
    /*
     * 自定义输出到mongdb类型:
     * String -- 字符串
     * Boolean -- 布尔值类型
     * Date -- 日期类型
     * Int --整形
     * Double -- 双精度型号
     * @return
     */
    Types type() default Types.String;
    
    /**
     * 输出到Mongodb时，如果是Double类型
     * 默认保留小数位
     * @return
     */
    int length() default 2;
}
