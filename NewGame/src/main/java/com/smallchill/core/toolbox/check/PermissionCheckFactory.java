package com.smallchill.core.toolbox.check;

import javax.servlet.http.HttpServletRequest;

import com.smallchill.common.vo.ShiroUser;
import com.smallchill.core.constant.Cst;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.kit.CollectionKit;
import com.smallchill.core.toolbox.kit.HttpKit;

/**
 * 权限自定义检查
 */
public class PermissionCheckFactory implements ICheck {

	@Override
	public boolean check(Object[] permissions) {
		ShiroUser user = ShiroKit.getUser();
		if (null == user) {
			return false;
		}
		String join = CollectionKit.join(permissions, ",");
		if(ShiroKit.hasAnyRoles(join)){
			return true;
		}
		return false;
	}

	@Override
	public boolean checkAll() {
		HttpServletRequest request = HttpKit.getRequest();
		ShiroUser user = ShiroKit.getUser();
		if (null == user) {
			return false;
		}
		String requestURI = request.getRequestURI().replace(Cst.me().getContextPath(), "");
		String[] str = requestURI.split("/");
		if (str.length > 3) {
			requestURI = str[1] + "/" + str[2];
		}
		if(ShiroKit.hasPermission(requestURI)){
			return true;
		}
		return false;
	}

}
