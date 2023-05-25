package com.smallchill.system.treasure.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.system.model.Menu;

public class PayChannelListValidator extends BladeValidator {
    @Override
    protected void doValidate(Invocation inv) {
        validateExist("channellist.chPoolId","channellist.clientType","channellist.id");
        validateLong("channellist.minLimit",0,99999L,"数据不在0-99999范围内");
        validateLong("channellist.maxLimit",0,99999L,"数据不在0-99999范围内");
        validateMinLeMax("channellist.minLimit","channellist.maxLimit","最小范围不能大于最大范围");
        validateRequired("channellist.giveRatio","额为赠送不能为空");
        validateRequired("channellist.channelTax","渠道税率不能为空");
        validateRequired("channellist.exchangeRatio","金币倍率不能为空");
        validateRequired("channellist.winConf","总赢倍率不能为空");
        validateRequired("channellist.fee","固定手续费不能为空");
        validateSort("channellist.sort","channellist.clientType","channellist.id");
    }
    protected void validateExist(String file,String file1,String file2) {
        // 判断是否
        String chPoolId = request.getParameter(file);
        String clientType = request.getParameter(file1);
        String id = request.getParameter(file2);
        validateRequired(file,"请选择商户名");
        validateRequired(file1,"请选择包来源");
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
    protected void validateSort(String fieldSort,String fieldCli,String fieldId){
        String id = request.getParameter(fieldId);
        validateRequired(fieldSort,"排序不能为空");
        String sort = request.getParameter(fieldSort);
        String cli = request.getParameter(fieldCli);
        if (Integer.parseInt(sort)<0){
            addError("排序必须大于0");
        }
        System.out.println();
        if (id==null){
            // 添加时
            String sql = "select * from Pay_ChannelList where clientType=#{cli} and sort=#{sort}";
            boolean temp = Db.isExist(sql, CMap.init().set("cli", cli).set("sort", sort));
            if (temp){
                addError("排序重复");
            }
        }else {
            // 修改
            String sql = "select * from Pay_ChannelList where clientType=#{cli} and sort=#{sort} and id!=#{id}";
            boolean temp = Db.isExist(sql, CMap.init().set("cli", cli).set("sort", sort).set("id",id));
            if (temp){
                addError("排序重复");
            }
        }
    }

}
