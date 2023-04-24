package com.smallchill.system.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;

public class LoginValidator extends BladeValidator {

	@Override
	protected void doValidate(Invocation inv) {
		validateRequired("account", "请输入您的账号");
		validateRequired("password", "请输入您的密码");
		/*validateRequired("smsCode", "请输入验证码");*/
	}

}
