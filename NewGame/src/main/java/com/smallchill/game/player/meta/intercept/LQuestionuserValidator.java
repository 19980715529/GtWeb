package com.smallchill.game.player.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;

public class LQuestionuserValidator extends BladeValidator {

	@Override
	protected void doValidate(Invocation inv) {
		validateRequired("lquestionuser.userid", "请输入玩家ID");
		validateRequired("lquestionuser.reson", "请输入加入原因");
		validateInteger("lquestionuser.userid", "请输入正确的玩家ID");
		String value = request.getParameter("lquestionuser.reson");
		validateStringMinLenValue(value, 4, "加入原因不得少于两个汉字");
	}
}
