package com.smallchill.system.service;

import com.smallchill.core.base.service.IService;
import com.smallchill.system.model.Menu;

public interface MenuService extends IService<Menu> {
	int findLastNum(String code);

	boolean isExistCode(String code);

	boolean updateStatus(String ids, Integer status);

	boolean updateRoleLevel(String ids, String level);
}
