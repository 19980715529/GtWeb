package com.smallchill.system.treasure.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;
import com.smallchill.core.toolbox.kit.StrKit;

public class ExchangeReviewValidator extends BladeValidator {
    @Override
    protected void doValidate(Invocation inv) {
        Boolean b = validateIsSix("exchange_review.status");
        if (b){
            validateRequired("exchange_review.feedback","请填写退回反馈原因");
            validateStringMaxLenValue(200,"exchange_review.feedback","输入的字符大于200给字符");
        }
    }
    protected Boolean validateIsSix(String field) {
        int progress = Integer.parseInt(request.getParameter(field));
        if (progress == 5){
            // 状态为5：代表可以退回，需要验证反馈消息是否为空
            return true;
        } else if (progress == 1) {
            validateRequired("exchange_review.channelId","请填写小渠道");
        }
        return false;
    }
    protected void validateStringMaxLenValue(int maxLen,String value,String errorMessage){
        String parameter = request.getParameter(value);
        if (StrKit.isBlank(parameter)) {
            addError(errorMessage);
            return;
        }
        int length = StrKit.length(parameter);
        if (length > maxLen) {
            addError(errorMessage);
            return;
        }
    }
}
