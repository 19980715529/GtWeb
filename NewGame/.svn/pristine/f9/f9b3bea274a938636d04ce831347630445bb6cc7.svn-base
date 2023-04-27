package com.smallchill.db.config.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.kit.StrKit;

/**
 * @Description TODO
 * @classNameEmailSendValidator
 * @Date 2023/3/15 11:11
 * @Version 1.0
 **/
public class EmailSendValidator extends BladeValidator {
    @Override
    protected void doValidate(Invocation inv) {
        validateRequired("emailrecords.title","请输入邮件标题");
        validateRequired("emailrecords.senderId","请输入发件人");
        validateRequired("emailrecords.userId","请输入收件人id");
        validateRequired("emailrecords.emailType","请选择邮件类型");
        validateRequired("emailrecords.amount","请输输入数量");
        validateRequired("emailrecords.attachment","请选择附件类型");
        String content = request.getParameter("emailrecords.content");
        validateStringMaxLenValue(content,200,"邮件字数不能超过200个字符");

    }
    protected void validateStringMaxLenValue(String value, int maxLen, String errorMessage) {
        if (StrKit.isBlank(value)) {
            addError("请输入邮件内容");
            return;
        }
        int length = StrKit.length(value);
        if (length > maxLen) {
            addError(errorMessage);
            return;
        }
    }
}
