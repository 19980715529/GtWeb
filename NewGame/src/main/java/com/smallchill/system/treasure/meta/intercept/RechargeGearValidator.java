package com.smallchill.system.treasure.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.CMap;

public class RechargeGearValidator extends BladeValidator {
    @Override
    protected void doValidate(Invocation inv) {
        validateLong("rechargegear.amount",0,999999999,"请输入正确的格式,范围在0-999999999");
        validateLong("rechargegear.gold",0,999999999,"请输入金币正确的格式,范围在0-999999999");
        validateRequired("rechargegear.getExtra","请输入获得比率");
        validateSort("rechargegear.sort","rechargegear.id");
    }
    protected void validateSort(String fieldSort,String fieldId){
        String id = request.getParameter(fieldId);
        validateInteger(fieldSort,0,9999,"排序不能为空,范围在0-9999");
        String sort = request.getParameter(fieldSort);
        if (id==null){
            // 添加时
            String sql = "select * from Pay_RechargeGear where sort=#{sort}";
            boolean temp = Db.isExist(sql, CMap.init().set("sort", sort));
            if (temp){
                addError("排序重复");
            }
        }else {
            // 修改
            String sql = "select * from Pay_RechargeGear where sort=#{sort} and id!=#{id}";
            boolean temp = Db.isExist(sql, CMap.init().set("sort", sort).set("id",id).set("id",id));
            if (temp){
                addError("排序重复");
            }
        }
    }
}
