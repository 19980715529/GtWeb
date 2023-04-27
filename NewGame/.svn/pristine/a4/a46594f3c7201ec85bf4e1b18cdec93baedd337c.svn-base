package com.smallchill.game.player.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;

public class UpdateGoldValidator extends BladeValidator {

	@Override
	protected void doValidate(Invocation inv) {
		validateRequired("player.remark", "请输入操作理由");
		String value = request.getParameter("player.remark");
		validateStringMinLenValue(value, 4, "操作理由不得少于两个汉字");
	}
}
