package com.smallchill.system.treasure.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.system.model.Menu;

public class ChannelEditValidator extends BladeValidator {
    @Override
    protected void doValidate(Invocation inv) {
        validateRequired("channel.unit","单位不能为空");
        validateRequired("channel.give","渠道外赠送不能为空");
        validateSql("channel.sort", "含有非法字符,请仔细检查!");
        validateRequired("channel.min","单次最小充值不能为空");
        validateLong("channel.min",0L,999999,"渠道最小值为数字类型范围为0~999999");
        validateRequired("channel.max","单次最大充值不能为空");
        validateLong("channel.max",0L,999999,"渠道最大值为数字类型范围为0~999999");
        validateMinLeMax("channel.min","channel.max","渠道最小值必须小于渠道最大值");
        validateRequired("channel.channelTaxRate","渠道汇率不能为空");
        validateType("channel.winConf","channel.type","总赢倍率不能为空");
        validateSort("channel.sort","channel.channelName","channel.type","channel.id","该排序已经存在");
    }
    protected void validateMinLeMax(String fieldMin,String fieldMax,String errorMessage){
        Integer min = Integer.valueOf(HttpKit.getRequest().getParameter(fieldMin));
        Integer max = Integer.valueOf(HttpKit.getRequest().getParameter(fieldMax));
        if (min>max){
            addError(errorMessage);
        }
    }
    protected void validateSort(String field,String field1,String field2,String field3, String errorMessage) {
        String sort = request.getParameter(field);
        String channelName = request.getParameter(field1);
        String type = request.getParameter(field2);
        String id = request.getParameter(field3);
        if (StrKit.isBlank(sort)) {
            addError("请输入游戏中的排序!");
        }
        if (Integer.parseInt(sort)<=0){
            addError("排序不能小于等于0!");
        }
        Blade blade = Blade.create(Menu.class);
        String sql = "select * FROM Channel as c where c.channelName=#{channelName} and c.pid!=0 and c.type=#{type} and c.sort=#{sort} and c.id!=#{id}";
        boolean temp = blade.isExist(sql, CMap.init().set("sort", sort).set("channelName",channelName).set("type",type).set("id",id));
        if (temp) {
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
