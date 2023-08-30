package com.smallchill.pay.google.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;

public class GooglePayValidate extends BladeValidator {
    @Override
    protected void doValidate(Invocation inv) {
        validateInteger("cancel","请输入cancel");
        validateRequired("orderId","请输入orderId");
        validateRequired("userId","请输入userId");
        validateRequired("packageName","packageName");
        validateRequired("productId","productId");
        validateRequired("purchaseToken","purchaseToken");
    }
}
