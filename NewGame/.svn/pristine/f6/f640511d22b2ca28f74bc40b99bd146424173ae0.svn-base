package com.smallchill.system.nativeweb.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;

public class NewsValidator extends BladeValidator {

	@Override
	protected void doValidate(Invocation inv) {
		validateRequired("news.subject",  "请输入公告名称");
		validateRequired("news.body",  "请输入公告内容");
		validateRequired("news.interval",  "请输入间隔时间");
		validateRequired("news.deployTime",  "请选择时间");
	}
}
