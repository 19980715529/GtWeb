package com.smallchill.game.player.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;

public class PointControlValidator extends BladeValidator {

	@Override
	protected void doValidate(Invocation inv) {
		validateInteger("player.CheatingRateSet", -10000, 10000, "请输入正确范围内的设置作弊率!");
		validateMinInteger("player.CheatingScoreSet", 0, "请输入大于0的作弊分数!");
	}
}
