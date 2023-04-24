package com.smallchill.system.nativeweb.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;

public class LoginNewsValidator extends BladeValidator {

	@Override
	protected void doValidate(Invocation inv) {
		validateRequired("loginnews.subject",  "请输入公告名称");
		validateRequired("loginnews.formattedBody",  "请输入公告内容");
	}
}
