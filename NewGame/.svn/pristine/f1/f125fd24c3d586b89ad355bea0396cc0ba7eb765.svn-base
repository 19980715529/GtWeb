package com.smallchill.system.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.system.model.User;

public class PasswordValidator extends BladeValidator {

	@Override
	protected void doValidate(Invocation inv) {
		validateOldPwd("user.id", "user.oldPassword", "原密码错误!");
		validateRequired("user.newPassword", "新密码不能为空");
		validateRequired("user.newPasswordr", "确认密码不能为空");
		validateTwoNotEqual("user.oldPassword", "user.newPassword", "新密码不能与原密码相同!");
		validateTwoEqual("user.newPassword", "user.newPasswordr", "确认密码与新密码不相同!");
	}

	protected void validateOldPwd(String field1, String field2, String errorMessage) {
		String userId = request.getParameter(field1);
		String password = request.getParameter(field2);
		if (StrKit.isBlank(password)) {
			addError("请输入原密码!");
		}
		Blade blade = Blade.create(User.class);
		User user = blade.findById(userId);
		if(null == user){
			addError("未找到该用户!");
		}
		String pwd = user.getPassword();
		String salt = user.getSalt();
		boolean temp = (ShiroKit.md5(password, salt).equals(pwd));
		if (!temp) {
			addError(errorMessage);
		}
	}

}
