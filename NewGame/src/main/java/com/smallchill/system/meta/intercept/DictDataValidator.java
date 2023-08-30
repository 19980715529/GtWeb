package com.smallchill.system.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.system.model.DictData;

public class DictDataValidator extends BladeValidator {
    @Override
    protected void doValidate(Invocation inv) {
        // 判断字典编码是否已经存在
        validateRequired("code","请输入字典编码");
        String code = request.getParameter("code");
        String id = request.getParameter("id");
        Blade blade = Blade.create(DictData.class);
        if (id==null){
            boolean temp = blade.isExist("select * from blade_dict_data where code=#{code}", CMap.init().set("code", code));
            if (temp){
                addError("字典编码已经存在");
            }
        }else {
            boolean temp = blade.isExist("select * from blade_dict_data where code=#{code} and id!=#{id}", CMap.init().set("code", code).set("id",id));
            if (temp){
                addError("字典编码已经存在");
            }
        }
        validateInteger("pid",0,999999,"请输入pid");
    }

}
