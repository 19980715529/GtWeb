package com.smallchill.game.player.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;

public class PlayerPasswordValidator extends BladeValidator {

	@Override
	protected void doValidate(Invocation inv) {
		validateRequired("player.newPassword", "新密码不能为空");
		validateRequired("player.newPasswordr", "确认密码不能为空");
		validateTwoEqual("player.newPassword", "player.newPasswordr", "确认密码与新密码不相同!");
	}
}
