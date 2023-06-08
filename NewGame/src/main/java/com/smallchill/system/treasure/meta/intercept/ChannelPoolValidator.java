package com.smallchill.system.treasure.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.kit.HttpKit;

public class ChannelPoolValidator extends BladeValidator {
    @Override
    protected void doValidate(Invocation inv) {
        validateRequired("channelpool.mcId","请选择商户名称");
        validateRequired("channelpool.cid","请选择商大渠道");
        validateRequired("channelpool.currencyType","请填写货币类型");
        validateRequired("channelpool.collectRate","请填写代收手续费");
        validateRequired("channelpool.paymentRate","请填写代付手续费");
        validateRequired("channelpool.fee","请填写固定手续费");
        validateRequired("channelpool.name","请填写小渠道名称");

        validateLong("channelpool.minLimit",0,99999L,"数据不在0-99999范围内");
        validateLong("channelpool.maxLimit",0,99999L,"数据不在0-99999范围内");
        validateMinLeMax("channelpool.minLimit","channelpool.maxLimit","最小范围不能大于最大范围");
        validateRequired("channelpool.giveRatio","额为赠送不能为空");
        validateRequired("channelpool.channelTax","渠道税率不能为空");
        validateRequired("channelpool.exchangeRatio","金币倍率不能为空");
        validateRequired("channelpool.winConf","总赢倍率不能为空");
        validateRequired("channelpool.money","固定手续费(主动收取)不能为空");

        validateSort("channelpool.sort","channelpool.id","channelpool.cid","channelpool.type");
        
    }

    protected void validateMinLeMax(String fieldMin,String fieldMax,String errorMessage) {
        int min = Integer.parseInt(HttpKit.getRequest().getParameter(fieldMin));
        int max = Integer.parseInt(HttpKit.getRequest().getParameter(fieldMax));
        if (min > max) {
            addError(errorMessage);
        }
    }
    protected void validateSort(String fieldSort,String fieldId,String fieldCId,String fieldType){
        String id = request.getParameter(fieldId);
        validateRequired(fieldSort,"排序不能为空");
        validateRequired(fieldType,"请选择订单类型");
        String sort = request.getParameter(fieldSort);
        String cid = request.getParameter(fieldCId);
        String type = request.getParameter(fieldType);
        if (Integer.parseInt(sort)<0){
            addError("排序必须大于0");
        }
        if (id==null){
            // 添加时
            String sql = "select * from Pay_channelpool where sort=#{sort} and type=#{type} and cid=#{cid}";
            boolean temp = Db.isExist(sql, CMap.init().set("sort", sort).set("cid",cid).set("type",type));
            if (temp){
                addError("排序重复");
            }
        }else {
            // 修改
            String sql = "select * from Pay_channelpool where sort=#{sort} and type=#{type} and cid=#{cid} and id!=#{id}";
            boolean temp = Db.isExist(sql, CMap.init().set("sort", sort).set("id",id).set("cid",cid).set("type",type));
            if (temp){
                addError("排序重复");
            }
        }
    }
    
}
