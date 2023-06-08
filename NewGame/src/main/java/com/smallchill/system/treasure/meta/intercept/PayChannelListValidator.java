package com.smallchill.system.treasure.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.kit.HttpKit;

public class PayChannelListValidator extends BladeValidator {
    @Override
    protected void doValidate(Invocation inv) {
//        validateExist("channellist.chPoolId","channellist.clientType","channellist.id");
//        validateLong("channellist.minLimit",0,99999L,"数据不在0-99999范围内");
//        validateLong("channellist.maxLimit",0,99999L,"数据不在0-99999范围内");
//        validateMinLeMax("channellist.minLimit","channellist.maxLimit","最小范围不能大于最大范围");
//        validateRequired("channellist.giveRatio","额为赠送不能为空");
//        validateRequired("channellist.channelTax","渠道税率不能为空");
//        validateRequired("channellist.exchangeRatio","金币倍率不能为空");
//        validateRequired("channellist.winConf","总赢倍率不能为空");
//        validateRequired("channellist.fee","固定手续费不能为空");
//        validateSort("channellist.sort","channellist.clientType","channellist.id","channellist.chName","channellist.chPoolId");
    }
    protected void validateExist(String file,String file1,String file2) {
        // 判断是否
        String chPoolId = request.getParameter(file);
        String clientType = request.getParameter(file1);
        String id = request.getParameter(file2);
        validateRequired(file,"请选择商户名");
        validateRequired(file1,"请选择包来源");
        // 获取大渠道id
        if (id==null){
            // 添加
            String sql = "select * from Pay_ChannelList where chPoolId=#{chPoolId} and clientType=#{clientType}";
            boolean temp = Db.isExist(sql,
                    CMap.init().set("chPoolId", chPoolId).set("clientType", clientType));
            if (temp){
                addError("包"+clientType+"中已经存在该充值");
            }
        }else {
            // 修改
            String sql = "select * from Pay_ChannelList where chPoolId=#{chPoolId} and clientType=#{clientType} and id!=#{id}";
            boolean temp = Db.isExist(sql,
                    CMap.init().set("chPoolId", chPoolId).set("clientType", clientType).set("id",id));
            if (temp){
                addError("包"+clientType+"中已经存在该充值");
            }
        }

    }

    protected void validateMinLeMax(String fieldMin,String fieldMax,String errorMessage){
        Integer min = Integer.valueOf(HttpKit.getRequest().getParameter(fieldMin));
        Integer max = Integer.valueOf(HttpKit.getRequest().getParameter(fieldMax));
        if (min>max){
            addError(errorMessage);
        }
    }
    protected void validateSort(String fieldSort,String fieldCli,String fieldId,String cname,String fieldChId){
        String id = request.getParameter(fieldId);
        validateRequired(fieldSort,"排序不能为空");
        validateRequired(fieldSort,"排序不能为空");
        String sort = request.getParameter(fieldSort);
        String cli = request.getParameter(fieldCli);
        String chName = request.getParameter(cname);
        String chId = request.getParameter(fieldChId);
        Integer type = Db.queryInt("select type FROM Pay_ChannelPool where id=#{id}", CMap.init().set("id", chId));
        if (Integer.parseInt(sort)<0){
            addError("排序必须大于0");
        }
        if (id==null){
            // 添加时
            String sql = "select * from Pay_ChannelList a join Pay_ChannelPool as b on a.chPoolId=b.id where clientType=#{cli} and sort=#{sort} and chName=#{chName} and b.type=#{type}";
            boolean temp = Db.isExist(sql, CMap.init().set("cli", cli).set("sort", sort).set("chName",chName).set("type",type));
            if (temp){
                addError("排序重复");
            }
        }else {
            // 修改
            String sql = "select * from Pay_ChannelList a join Pay_ChannelPool as b on a.chPoolId=b.id where clientType=#{cli} and sort=#{sort} and a.id!=#{id} and chName=#{chName} and b.type=#{type}";
            boolean temp = Db.isExist(sql, CMap.init().set("cli", cli).set("sort", sort).set("id",id).set("chName",chName).set("type",type));
            if (temp){
                addError("排序重复");
            }
        }
    }

}
