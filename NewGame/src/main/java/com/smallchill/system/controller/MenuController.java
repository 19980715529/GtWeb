package com.smallchill.system.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.Func;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.core.toolbox.cache.ILoader;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.core.toolbox.support.Convert;
import com.smallchill.system.meta.intercept.MenuValidator;
import com.smallchill.system.model.Menu;
import com.smallchill.system.model.Relation;
import com.smallchill.system.service.MenuService;

@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController implements ConstShiro{
	private static String LIST_SOURCE = "menu.list";
	private static String BASE_PATH = "/system/menu/";
	private static String CODE = "menu";
	private static String PREFIX = "blade_menu";

	@Autowired
	MenuService service;
	@DoControllerLog(name="进入菜单列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "menu.html";
	}

	@RequestMapping(KEY_ADD)
	@Permission({ADMINISTRATOR})
	public String add(ModelMap mm) {
		if(ShiroKit.lacksRole(ADMINISTRATOR)){
			return REDIRECT_UNAUTH;
		}
		mm.put("code", CODE);
		return BASE_PATH + "menu_add.html";
	}
	
	@RequestMapping(KEY_ADD + "/{id}")
	@Permission(ADMINISTRATOR)
	public String add(@PathVariable Integer id, ModelMap mm) {
		if (null != id) {
			Menu menu = service.findById(id);
			mm.put("PCODE", menu.getCode());
			mm.put("LEVELS", menu.getLevels() + 1);
			mm.put("NUM", service.findLastNum(menu.getCode()));
		}
		mm.put("code", CODE);
		return BASE_PATH + "menu_add.html";
	}

	@RequestMapping(KEY_EDIT + "/{id}")
	@Permission(ADMINISTRATOR)
	public String edit(@PathVariable Integer id, ModelMap mm) {
		Menu menu = service.findById(id);
		mm.put("model", JsonKit.toJson(menu));
		mm.put("code", CODE);
		return BASE_PATH + "menu_edit.html";
	}

	@RequestMapping(KEY_VIEW + "/{id}")
	@Permission(ADMINISTRATOR)
	public String view(@PathVariable Integer id, ModelMap mm) {
		Menu menu = service.findById(id);
		mm.put("model", JsonKit.toJson(menu));
		mm.put("code", CODE);
		return BASE_PATH + "menu_view.html";
	}

	@Json
	@RequestMapping(KEY_LIST)
	@Permission(ADMINISTRATOR)
	public Object list() {
		Object gird = paginate(LIST_SOURCE);
		return gird;
	}

	@SuppressWarnings("rawtypes")
	@Json
	@Before(MenuValidator.class)
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		Menu menu = mapping(PREFIX, Menu.class);
		menu.setStatus(1);
		int temp = service.saveRtId(menu);
		if (temp != 0) {
			// 默认高级管理员拥有权限
			List<Map> rolePids = Db.selectList("select * from BLADE_ROLE where pid = 0");
			List<Relation> relations = new ArrayList<>();
			for (Map rolePid : rolePids) {
				Relation relation = new Relation();
				relation.setMenuid(temp);
				relation.setRoleid(Convert.toInt(rolePid.get("id")));
				relations.add(relation);
			}
			Blade dao = Blade.create(Relation.class);
			dao.saveBatch(relations);
			
			CacheKit.removeAll(SYS_CACHE);
			return success(SAVE_SUCCESS_MSG);
		} else {
			return error(SAVE_FAIL_MSG);
		}
	}

	@Json
	@Before(MenuValidator.class)
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		Menu menu = mapping(PREFIX, Menu.class);
		boolean temp = service.update(menu);
		if (temp) {
			CacheKit.removeAll(SYS_CACHE);
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}

	@Json
	@RequestMapping(KEY_DEL)
	@Permission(ADMINISTRATOR)
	public AjaxResult del() {
		boolean temp = service.updateStatus(getParameter("ids"), 2);
		if (temp) {
			CacheKit.removeAll(SYS_CACHE);
			return success(DEL_SUCCESS_MSG);
		} else {
			return error(DEL_FAIL_MSG);
		}
	}

	@Json
	@RequestMapping(KEY_RESTORE)
	@Permission(ADMINISTRATOR)
	public AjaxResult restore(@RequestParam String ids) {
		boolean temp = service.updateStatus(ids, 1);
		if (temp) {
			CacheKit.removeAll(SYS_CACHE);
			return success(RESTORE_SUCCESS_MSG);
		} else {
			return error(RESTORE_FAIL_MSG);
		}

	}

	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove(@RequestParam String ids) {
		int cnt = service.deleteByIds(ids);
		if (cnt > 0) {
			CacheKit.removeAll(SYS_CACHE);
			return success(DEL_SUCCESS_MSG);
		} else {
			return error(DEL_FAIL_MSG);
		}
	}
	
	@Json
	@RequestMapping("/resetmenurole")
	@Permission(ADMINISTRATOR)
	public AjaxResult resetmenurole() {
		boolean temp = service.updateRoleLevel(getParameter("ids"), getParameter("level"));
		if (temp) {
			CacheKit.removeAll(SYS_CACHE);
			return success("设置成功");
		} else {
			return error("设置失败!");
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Json
	@RequestMapping("/getMenu")
	public List<Map> getMenu(){
		final Integer userId = getParameterToInt("userId");
		final Integer roleId = getParameterToInt("roleId");

		Map<String, Object> userRole = CacheKit.get(SYS_CACHE, ROLE_EXT + userId, new ILoader() {
			@Override
			public Object load() {
				return Db.selectOne("select * from BLADE_ROLE_EXT where USERID = #{userId}", CMap.init().set("userId", userId));
			}
		}); 


		String roleIn = "0";
		String roleOut = "0";
		if (!Func.isEmpty(userRole)) {
			CMap cmap = CMap.parse(userRole);
			roleIn = cmap.getStr("ROLEIN");
			roleOut = cmap.getStr("ROLEOUT");
		}
		final StringBuilder sql = new StringBuilder();
		sql.append("select * from BLADE_MENU ");
		sql.append(" where ( ");
		sql.append("	 (status=1)");
		sql.append("	 and (icon is not null and icon not LIKE '%btn%' and icon not LIKE '%icon%' ) ");
		sql.append("	 and (id in (select menuId from BLADE_RELATION where roleId in (#{join(roleId)})) or id in (#{join(roleIn)}))");
		sql.append("	 and id not in(#{join(roleOut)})");
		sql.append("	)");
		sql.append(" order by levels,pCode,num");

		List<Map> sideBar = Db.selectListByCache(SYS_CACHE, SIDEBAR + userId, sql.toString(),
				CMap.init()
				.set("roleId", Convert.toIntArray(roleId.toString()))
				.set("roleIn", Convert.toIntArray(roleIn))
				.set("roleOut", Convert.toIntArray(roleOut)));
		return sideBar;
	}

}
