package com.smallchill.system.meta.intercept;

import com.smallchill.common.tool.SysCache;
import com.smallchill.core.aop.Invocation;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.intercept.BladeValidator;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.kit.CollectionKit;
import com.smallchill.core.toolbox.kit.StrKit;

public class RoleValidator extends BladeValidator {

	@Override
	protected void doValidate(Invocation inv) {
		//validateRole("roleId", "ids", "超级管理员不能去掉角色管理的权限!");
	}

	protected void validateRole(String field1, String field2, String errorMessage) {
		String ids = request.getParameter(field2);
		if (StrKit.isBlank(ids)) {
			addError("请选择权限!");
		} 
		String roleId = request.getParameter(field1);
		String roleAlias = SysCache.getRoleAlias(roleId);
		if(roleAlias.equals(ConstShiro.ADMINISTRATOR)){
			String[] id = ids.split(",");
			String authority = Db.queryStr("select id from blade_menu where code = #{code}", CMap.init().set("code", "role_authority"));
			if(!CollectionKit.contains(id, authority)){
				//超管不包含权限配置则报错
				addError(errorMessage);
			}
		}
	}

}
