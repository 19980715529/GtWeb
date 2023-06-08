package com.smallchill.system.treasure.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;

public class ExchangePayValidator extends BladeValidator {
    @Override
    protected void doValidate(Invocation inv) {
        validateRequired("exchange.userId","Wrong order info");
        validateRequired("exchange.exchangeAmount","Wrong order info");
        validateRequired("exchange.bankNumber","Wrong order info");
        validateRequired("exchange.channelName","Wrong order info");
        validateRequired("exchange.cardholder","Wrong order info");
        validateRequired("exchange.phone","Wrong order info");
    }
}
