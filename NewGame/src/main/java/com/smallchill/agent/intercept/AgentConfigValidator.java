package com.smallchill.agent.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;

public class AgentConfigValidator extends BladeValidator {
    @Override
    protected void doValidate(Invocation inv) {
//        validateRequired("agentConfig.name","请输入数据");
        validateInteger("agentConfig.value",0,9999,"请输入正确的数据格式格式");
    }
}
