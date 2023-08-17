package com.smallchill.pay.core.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;

import java.util.Map;

public class GooglePayValidator extends BladeValidator {
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
        Map<String, String[]> parameterMap = request.getParameterMap();
        System.out.println("请求参数："+parameterMap);
        validateInteger("recharge.isFirstCharge","请填写充值类型0普通充值，1首充，2随机充值");
        validateRequired("recharge.userId","用户id必填");
        validateInteger("recharge.pid","父渠道id必填");
        validateInteger("recharge.id","渠道id必填");
        validateInteger("recharge.id","充值挡位id");
        validateRequired("recharge.skuId","订单号");
    }
}
