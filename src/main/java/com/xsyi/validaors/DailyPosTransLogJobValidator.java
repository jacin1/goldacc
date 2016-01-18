/**
 * 
 */
package com.xsyi.validaors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.xsyi.core.validator.AbstracValidator;
import com.xsyi.core.validator.ValidatorResult;


/**
 * 
 * @author tangtao7481
 * @version $Id: DailyPosTransLogJobValidator.java, v 0.1 Mar 31, 2013 2:28:57 PM tangtao7481 Exp $
 */
public class DailyPosTransLogJobValidator extends AbstracValidator {

    /** 
     */
    @Override
    public List<ValidatorResult> validate(Map<String, Object> params) {
        //检查提交的日期中是否包含必要的参数：startDate,endDate
        String startDate = (String)params.get("startDate");
        String endDate = (String)params.get("endDate");
        List<ValidatorResult> validatorResult = new ArrayList<ValidatorResult>();
        if( StringUtils.isEmpty(startDate) ){
            validatorResult.add(new ValidatorResult("startDate",startDate,Boolean.FALSE.booleanValue(),"startDate参数不能为空"));
        }
        if( StringUtils.isEmpty(endDate) ){
            validatorResult.add(new ValidatorResult("endDate",startDate,Boolean.FALSE.booleanValue(),"endDate参数不能为空"));
        }
        return validatorResult;
    }

}
