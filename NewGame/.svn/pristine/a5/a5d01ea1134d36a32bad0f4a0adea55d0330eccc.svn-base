package com.smallchill.game.player.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;

public class GiveTransferValidator extends BladeValidator {

	@Override
	protected void doValidate(Invocation inv) {
		validateRequired("player.userid", "请输入玩家ID");
		validateRequired("player.score", "请输入赠送金币");
	}
}
