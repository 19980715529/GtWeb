package com.smallchill.system.treasure.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;

public class RechargeValidator extends BladeValidator {

	@Override
	protected void doValidate(Invocation inv) {
		
		validateRequired("recharge.buymoney",  "请输入充值金额");
		validateDouble("recharge.buymoney", "请输入正确充值金额");
		
		validateRequired("recharge.score",  "请输入对应金币数");
		validateLong("recharge.score", "请输入正确对应金币数");

		validateRequired("recharge.sendscore",  "请输入额外赠送金币数");
		validateLong("recharge.sendscore", "请输入正确额外赠送金币数");
	}
}
