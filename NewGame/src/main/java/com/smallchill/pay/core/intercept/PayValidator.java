package com.smallchill.pay.core.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;

public class PayValidator extends BladeValidator {
    /**
     *  * 需要的参数
     *  * recharge.isFirstCharge:0普通充值，1首充，2随机充值
     *  * recharge.userId:用户id
     *  * recharge.pid: 父渠道id
     *  * recharge.id: 渠道id
     *  * recharge.gear:新加：充值挡位id
     * @param inv
     */
    @Override
    protected void doValidate(Invocation inv) {
        validateInteger("recharge.isFirstCharge","请填写充值类型0普通充值，1首充，2随机充值");
        validateRequired("recharge.userId","用户id必填");
        validateInteger("recharge.pid","父渠道id必填");
        validateInteger("recharge.id","渠道id必填");
        validateInteger("recharge.id","充值挡位id");
    }
}
