package com.smallchill.system.treasure.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.kit.HttpKit;

public class PaymentChannelValidator extends BladeValidator {
    @Override
    protected void doValidate(Invocation inv) {
        validateRequired("paymentChannel.cid","请选择关联渠道");
        validateRequired("paymentChannel.clientType","请选择包来源");
        validateRequired("paymentChannel.min","单次最小充值不能为空");
        validateLong("paymentChannel.min",0L,999999,"渠道最小值为数字类型范围为0~999999");
        validateRequired("paymentChannel.max","单次最大充值不能为空");
        validateLong("paymentChannel.max",0L,999999,"渠道最大值为数字类型范围为0~999999");
        validateMinLeMax();
        validateRequired("paymentChannel.give","渠道外赠送不能为空");
        validateRequired("paymentChannel.channelTaxRate","渠道汇率不能为空");
        validateRequired("paymentChannel.unit","请填写单位");
        validateRequired("paymentChannel.goldProportion","请填写金币倍率");
        validateInteger("paymentChannel.goldProportion","请填写正确金币倍率数据");
        validateInteger("paymentChannel.sort","请填写排序");
        validateRequired("paymentChannel.isOpen","请选择是否开启");
        String id = request.getParameter("paymentChannel.id");
        validateType();
        // 判断选择的包中排序是否存在
        int cid = Integer.parseInt(request.getParameter("paymentChannel.cid"));
        int clientType = Integer.parseInt(request.getParameter("paymentChannel.clientType"));
        int sort = Integer.parseInt(request.getParameter("paymentChannel.sort"));
        validateIsExist(id,cid,clientType,sort);
    }

    private void validateIsExist(String id,Integer cid,Integer clientType,Integer sort) {
        if (id==null){
            // 判断当前包中是否存在该渠道
            Integer i = Db.queryInt("select count(1) from paymentChannel where cid=#{cid} and clientType=#{clientType}",
                    CMap.init().set("cid", cid).set("clientType", clientType));
            if (i>0){
                addError("包"+clientType+"已经存在该渠道");
            }
            Integer j = Db.queryInt("select count(1) from paymentChannel where clientType=#{clientType} and sort=#{sort}",
                    CMap.init().set("clientType", clientType).set("sort",sort));
            if (j>0){
                addError("排序重复");
            }
        }else {
            // 判断当前包中是否存在该渠道
            Integer i = Db.queryInt("select count(1) from paymentChannel where cid=#{cid} and clientType=#{clientType} and id!=#{id}",
                    CMap.init().set("cid", cid).set("clientType", clientType).set("id",id));
            if (i>0){
                addError("包"+clientType+"已经存在该渠道");
            }
            Integer j = Db.queryInt("select count(1) from paymentChannel where clientType=#{clientType} and sort=#{sort} and id!=#{id}",
                    CMap.init().set("clientType", clientType).set("sort",sort).set("id",id));
            if (j>0){
                addError("排序重复");
            }
        }

    }

    protected void validateMinLeMax(){
        int min = Integer.parseInt(HttpKit.getRequest().getParameter("paymentChannel.min"));
        int max = Integer.parseInt(HttpKit.getRequest().getParameter("paymentChannel.max"));
        if (min>max){
            addError("渠道最小值必须小于渠道最大值");
        }
    }
    protected void validateType(){
        String type = request.getParameter("paymentChannel.type");
        if ("1".equals(type)){
            validateRequired("paymentChannel.winConf","总赢倍率不能为空");
        }
    }
}
