package com.smallchill.system.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.system.model.User;

public class UserValidator2 extends BladeValidator {

	@Override
	protected void doValidate(Invocation inv) {
		
		if (inv.getMethod().toString().indexOf("update") == -1) {
			validateAccount("BLADE_USER.ACCOUNT", "登录名已存在");
			validateStringExt("BLADE_USER.ACCOUNT",  "含有非法字符,请检查");
			validateRequired("BLADE_USER.ACCOUNT",  "请输入登录名");
			validateString("BLADE_USER.ACCOUNT", 1, 16,  "请输入5~16位的登录名");
		}
	}

	protected void validateAccount(String field, String errorMessage) {
		String account = request.getParameter(field);
		if (StrKit.isBlank(account)) {
			addError("请输入登录名!");
		}
		if (Blade.create(User.class).isExist("SELECT * FROM blade_user WHERE account = #{account}", CMap.init().set("account", account))) {
			addError(errorMessage);
		}
	}

}
