package com.smallchill.db.config.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;

public class PhoneLimitValidator extends BladeValidator {

	@Override
	protected void doValidate(Invocation inv) {
		String value = request.getParameter("lphonelimit.account");
		validateMobile(value, "玩家账号必须是手机号.<br/>请输入正确的玩家账号.");
	}
}
