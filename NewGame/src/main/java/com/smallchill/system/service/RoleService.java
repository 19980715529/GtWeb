package com.smallchill.system.service;

import com.smallchill.core.annotation.DoLog;
import com.smallchill.core.base.service.IService;
import com.smallchill.system.model.Role;

public interface RoleService extends IService<Role> {
	int findLastNum(Integer id);

	@DoLog(name = "设置权限")
	boolean grant(String ids, Integer roleId);

	int getParentCnt(Integer id);
}
