package com.smallchill.system.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.system.model.Dict;

public class DictValidator extends BladeValidator {

	@Override
	protected void doValidate(Invocation inv) {
		validateDict("该字典序号已存在!");
	}

	protected void validateDict(String errorMessage) {
		String num = request.getParameter("blade_dict.num");
		if (StrKit.notBlank(num)) {
			String code = "";
			String id = request.getParameter("blade_dict.id");
			if (StrKit.notBlank(id)) {
				Dict dict = Blade.create(Dict.class).findById(id);
				code = dict.getCode();
			} else {
				code = request.getParameter("blade_dict.code");
			}
			
			boolean temp = Blade.create(Dict.class).isExist("select * from blade_dict where code = #{code} and num = #{num}", CMap.init().set("code", code).set("num", num));
			
			if (temp) {
				addError(errorMessage);
			}
		} 
	}

}
