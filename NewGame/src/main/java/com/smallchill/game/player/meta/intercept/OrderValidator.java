package com.smallchill.game.player.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;

public class OrderValidator extends BladeValidator {
    @Override
    protected void doValidate(Invocation inv) {
        validateInteger("player.id","请填写");
        validateInteger("player.orderStatus","请填写订单状态");
    }
}
