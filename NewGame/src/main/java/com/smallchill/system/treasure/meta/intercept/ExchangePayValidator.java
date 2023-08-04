package com.smallchill.system.treasure.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;

import java.util.Map;

public class ExchangePayValidator extends BladeValidator {
    @Override
    protected void doValidate(Invocation inv) {
        validateRequired("exchange.userId","Wrong order info1");
        validateRequired("exchange.exchangeAmount","Wrong order info2");
        validateRequired("exchange.bankNumber","Wrong order info3");
        validateRequired("exchange.channelName","Wrong order info4");
        String parameter = request.getParameter("exchange.channelName5");
        if (!"pix".equals(parameter)){
            // 菲律宾兑换需要判断下面的字段
            validateRequired("exchange.cardholder","Wrong order info6");
            validateRequired("exchange.phone","Wrong order info7");
        }
    }
}
