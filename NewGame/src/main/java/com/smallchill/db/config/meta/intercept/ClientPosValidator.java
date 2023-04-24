package com.smallchill.db.config.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;

public class ClientPosValidator extends BladeValidator {
    @Override
    protected void doValidate(Invocation inv) {
        validateRequired("accounttypename.clientType","请输入包id");
        validateRequired("accounttypename.name","请输入包名字");
        validateRequired("accounttypename.ratio","请输入金币比率");
    }
}
