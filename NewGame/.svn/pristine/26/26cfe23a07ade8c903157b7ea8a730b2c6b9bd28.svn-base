package com.smallchill.game.player.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.kit.StrKit;

public class BindPhoneValidator extends BladeValidator {
    @Override
    protected void doValidate(Invocation inv) {
        validateRequired("player.bindPhone", "请输入手机号");
        String phone = request.getParameter("player.bindPhone");
        validateStringEquLenValue(phone,10,"电话号的长度为10位");

    }

    protected void validateStringEquLenValue(String value, int len, String errorMessage) {
        if (StrKit.isBlank(value)) {
            addError(errorMessage);
            return;
        }
        if (!value.startsWith("9")){
            addError("电话号必须以9开头");
            return;
        }
        int length = StrKit.length(value);
        if (length != len) {
            addError(errorMessage);
            return;
        }
        int con= Db.queryInt("select count(1) from [QPGameUserDB].[dbo].[AccountsInfo] where bindPhone=#{phone}",
                CMap.init().set("phone",value));
        if (con!=0){
            addError("该手机号已经被绑定");
            return;
        }
    }
}
