package com.smallchill.system.treasure.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.system.model.Menu;

/**
 * @Description TODO
 * @classNameChannelEditValidator
 * @Date 2023/3/9 12:41
 * @Version 1.0
 **/
public class ChannelAddValidator extends BladeValidator {
    @Override
    protected void doValidate(Invocation inv) {
        String parameter = HttpKit.getRequest().getParameter("channel.pid");
        validateRequired("channel.pid","");
        if ("0".equals(parameter)){
            validateRequired("channel.mcName","商户名不能为空");
            return;
        }
        validateRequired("channel.channelName","大渠道名称不能为空");
        validateRequired("channel.min","单次最小充值不能为空");
        validateLong("channel.min",0L,999999,"渠道最小值为数字类型范围为0~999999");
        validateRequired("channel.max","单次最大充值不能为空");
        validateLong("channel.max",0L,999999,"渠道最大值为数字类型范围为0~999999");
        validateMinLeMax("channel.min","channel.max","渠道最小值必须小于渠道最大值");
        validateRequired("channel.give","渠道外赠送不能为空");
        validateRequired("channel.channelTaxRate","渠道汇率不能为空");
        validateRequired("channel.unit","请填写单位");
        validateRequired("channel.goldProportion","请填写金币倍率");
        validateInteger("channel.goldProportion","请填写正确金币倍率数据");
        validateRequired("channel.type","请填写渠道类型");
        validateType("channel.winConf","channel.type","总赢倍率不能为空");
    }
    protected void validateMinLeMax(String fieldMin,String fieldMax,String errorMessage){
        Integer min = Integer.valueOf(HttpKit.getRequest().getParameter(fieldMin));
        Integer max = Integer.valueOf(HttpKit.getRequest().getParameter(fieldMax));
        if (min>max){
            addError(errorMessage);
        }
    }
    protected void validateType(String field,String field1, String errorMessage){
        String type = request.getParameter(field1);
        if ("1".equals(type)){
            validateRequired(field,"总赢倍率不能为空");
        }
    }


}
