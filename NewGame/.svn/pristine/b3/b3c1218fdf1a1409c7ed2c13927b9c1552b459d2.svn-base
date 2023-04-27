package com.smallchill.system.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.system.model.Menu;

public class MenuValidator extends BladeValidator {

	@Override
	protected void doValidate(Invocation inv) {
		
		if (inv.getMethod().toString().indexOf("update") == -1) {
			validateRequired("blade_menu.pcode", "请输入菜单父编号");
			validateCode("blade_menu.code", "菜单编号已存在!");
		}
		validateSql("blade_menu.source", "含有非法字符,请仔细检查!");
		
	}

	protected void validateCode(String field, String errorMessage) {
		String code = request.getParameter(field);
		if (StrKit.isBlank(code)) {
			addError("请输入菜单编号!");
		}
		Blade blade = Blade.create(Menu.class);
		String sql = "select * from blade_menu where code = #{code}";
		boolean temp = blade.isExist(sql, CMap.init().set("code", code));
		if (temp) {
			addError(errorMessage);
		}
	}

}
