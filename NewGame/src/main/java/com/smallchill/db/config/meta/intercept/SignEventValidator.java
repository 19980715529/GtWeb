package com.smallchill.db.config.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;

/**
 * @Description TODO
 * @classNameSignEvent
 * @Date 2023/2/23 19:57
 * @Version 1.0
 **/
public class SignEventValidator extends BladeValidator {
    @Override
    protected void doValidate(Invocation inv) {
        validateInteger("signinconfig.reward","请输入整数");
        validateInteger("signinconfig.reward1","请输入整数");
        validateInteger("signinconfig.rewardType","请选择奖励类型");
        validateInteger("signinconfig.signInDays","请输入整数");
        Integer reward = Integer.valueOf(request.getParameter("signinconfig.reward"));
        Integer reward1 = Integer.valueOf(request.getParameter("signinconfig.reward1"));
        if (reward<0){
            addError("奖励最大值必须大于0");
        }
        if (reward>reward1){
            addError("奖励最大值编写大于奖励最小值");
        }
    }
}
