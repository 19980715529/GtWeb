package com.smallchill.system.service.impl;

import org.springframework.stereotype.Service;

import com.smallchill.core.base.service.BaseService;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.support.Convert;
import com.smallchill.system.model.Menu;
import com.smallchill.system.service.MenuService;

@Service
public class MenuServiceImpl extends BaseService<Menu> implements MenuService {

	@Override
	public int findLastNum(String code) {
		try{
			Blade blade = Blade.create(Menu.class);
			Menu menu = blade.findFirstBy("pCode = #{pCode} order by num desc", CMap.init().set("pCode", code));
			return menu.getNum() + 1;
		}
		catch(Exception ex){
			return 1;
		}
	}

	@Override
	public boolean isExistCode(String code) {
		Blade blade = Blade.create(Menu.class);
		String sql = "select * from blade_menu where code = #{code}";
		boolean temp = blade.isExist(sql, CMap.init().set("code", code));
		return temp;
	}

	@Override
	public boolean updateStatus(String ids, Integer status) {
		CMap paras = CMap.init().set("status", status).set("ids", Convert.toIntArray(ids));
		Blade blade = Blade.create(Menu.class);
		boolean temp = blade.updateBy("status=#{status}", "id in (#{join(ids)})", paras);
		return temp;
	}

	@Override
	public boolean updateRoleLevel(String ids, String level) {
		CMap paras = CMap.init().set("rolelevel", level).set("ids", Convert.toIntArray(ids));
		Blade blade = Blade.create(Menu.class);
		boolean temp = blade.updateBy("rolelevel=#{rolelevel}", "id in (#{join(ids)})", paras);
		return temp;
	}

}
