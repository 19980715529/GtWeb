package com.smallchill.system.treasure.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;

public class ChannelPoolValidator extends BladeValidator {
    @Override
    protected void doValidate(Invocation inv) {
        validateRequired("channelpool.mcId","请选择商户名称");
        validateRequired("channelpool.cid","请选择商打渠道");
        validateRequired("channelpool.currencyType","请填写货币类型");
        validateRequired("channelpool.formalitiesCost","请填写手续费");
        validateRequired("channelpool.fee","请填写固定手续费");
        validateRequired("channelpool.name","请填写小渠道名称");
    }
}
