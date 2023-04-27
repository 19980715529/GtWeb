package com.smallchill.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smallchill.common.base.BaseController;
import com.smallchill.common.tool.SysCache;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.Const;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.system.model.User;
import com.smallchill.core.constant.ConstShiro;

@Controller
@RequestMapping("/main")
public class MainController extends BaseController {
	private static String CODE = "user";
	
	@GetMapping
	public String index() {
		return Const.INDEX_MAIN_REALPATH;
	}

	@RequestMapping(KEY_VIEW + "/{id}")
	public String view(@PathVariable Integer id, ModelMap mm) {
		User user = Blade.create(User.class).findById(id);
		CMap cmap = CMap.parse(user);
		cmap.set("deptName", SysCache.getDeptName(user.getDeptid()))
			.set("roleName", SysCache.getRoleName(user.getRoleid()))
			.set("sexName", SysCache.getDictName(101, user.getSex()));
		mm.put("user", cmap);
		mm.put("code", CODE);
		return Const.INDEX_MAIN_REALPATH;
	}
}
