package com.smallchill.system.treasure.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.system.model.Menu;

public class PayChannelValidator extends BladeValidator {
    @Override
    protected void doValidate(Invocation inv) {

        validateRequired("paychannel.cname","渠道名称不能为空");
        validateRequired("paychannel.exchangeGear","挡位");
        validateSort("paychannel.sort","paychannel.id","请输入正确的排序格式");
    }

    protected void validateSort(String field,String filed1, String errorMessage) {
        String sort = request.getParameter(field);
        validateInteger(field,"请输入正确的排序格式");
        if (Integer.parseInt(sort)<=0){
            addError("排序不能小于等于0!");
        }
        String id = request.getParameter(filed1);
        if (id==null){
            // 添加时
            String sql = "select * FROM Pay_Channel where sort=#{sort}";
            boolean temp = Db.isExist(sql, CMap.init().set("sort", sort).set("sort",sort));
            if (temp) {
                addError("排序重复");
            }
        }else {
            // 修改时
            String sql = "select * FROM Pay_Channel where sort=#{sort} and id!={id}";
            boolean temp = Db.isExist(sql, CMap.init().set("sort", sort).set("sort",sort).set("id",id));
            if (temp) {
                addError("排序重复");
            }
        }

    }

}
