package com.smallchill.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smallchill.common.base.BaseController;
import com.smallchill.common.tool.SysCache;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.cache.CacheKit;
import com.smallchill.core.toolbox.kit.JsonKit;
import com.smallchill.core.toolbox.support.Convert;
import com.smallchill.game.service.CommonService;
import com.smallchill.system.meta.intercept.RoleValidator;
import com.smallchill.system.model.Role;
import com.smallchill.system.service.RoleService;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController implements ConstShiro{
	private static String BASE_PATH = "/system/role/";
	private static String CODE = "role";
	private static String PREFIX = "blade_role";
	
	@Autowired
	RoleService service;
	@Autowired
	private CommonService commonService;

	@DoControllerLog(name="进入角色列表页面")
	@RequestMapping("/")
	//@Permission({ADMINISTRATOR, ADMIN})
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		String roles = ShiroKit.getUser().getRoles() + "," + ShiroKit.getUser().getSubRoles();
		mm.put("roles",roles);
		Object role = Blade.create(Role.class).findById(ShiroKit.getUser().getRoles());
		mm.put("role", role);
		return BASE_PATH + "role.html";
	}
	
	@Json
	@RequestMapping(KEY_LIST)
	//@Permission({ADMINISTRATOR, ADMIN})
	public Object list() {
		Object gird = paginateBySelf("role.all_list");
		return gird;
	}
	@DoControllerLog(name="进入角色新增页面")
	@RequestMapping(KEY_ADD)
	@Permission({ADMINISTRATOR, ADMIN})
	public String add(ModelMap mm) {
		mm.put("code", CODE);
		String[] rids = ShiroKit.getUser().getRoles().split(",");
		for (String string : rids) {
			Role r = Blade.create(Role.class).findById(string);
			if(r!=null && r.getTips().equals("administrator")){
				mm.put("pid", r.getId());
				break;
			} else if(r!=null && r.getTips().equals("admin")){
				mm.put("pid", r.getId());
				break;
			} else if(r!=null && r.getTips().equals("user")){
				mm.put("pid", r.getId());
				break;
		    }
		}
		//mm.put("num", service.findLastNum(Integer.parseInt(ShiroKit.getUser().getRoles())));
		return BASE_PATH + "role_add.html";
	}
	@DoControllerLog(name="进入角色新增页面")
	@RequestMapping(KEY_ADD + "/{id}")
	@Permission({ADMINISTRATOR, ADMIN})
	public String add(@PathVariable Integer id, ModelMap mm) {
		if (null != id) {
			mm.put("pId", id);
			mm.put("num", service.findLastNum(id));
		}
		Role role = Blade.create(Role.class).findById(ShiroKit.getUser().getRoles());
		mm.put("role", role);
		mm.put("code", CODE);
		return BASE_PATH + "role_add.html";
	}
	@DoControllerLog(name="进入角色修改页面")
	@RequestMapping(KEY_EDIT + "/{id}")
	@Permission({ADMINISTRATOR, ADMIN})
	public String edit(@PathVariable Integer id, ModelMap mm) {
		Role role = Blade.create(Role.class).findById(id);
		Role selfrole = Blade.create(Role.class).findById(ShiroKit.getUser().getRoles());
		mm.put("role", role);
		mm.put("selfrole", selfrole);
		mm.put("code", CODE);
		return BASE_PATH + "role_edit.html";
	}

	@RequestMapping(KEY_VIEW + "/{id}")
	//@Permission({ADMINISTRATOR, ADMIN})
	public String view(@PathVariable Integer id, ModelMap mm) {
		Role role = service.findById(id);
		Role parent = service.findById(role.getPid());
		String pname = (null == parent) ? "" : parent.getName();
		CMap cmap = CMap.parse(role);
		cmap.set("deptName", SysCache.getDeptName(role.getDeptid())).set("pname", pname);
		mm.put("model", JsonKit.toJson(cmap));
		mm.put("code", CODE);
		return BASE_PATH + "role_view.html";
	}
	@DoControllerLog(name="进入权限配置页面")
	@RequestMapping("/authority")
	@Permission({ADMINISTRATOR, ADMIN})
	public String authority(ModelMap mm) {
		mm.put("roleId", getParameter("roleId"));
		mm.put("roleName", getParameter("roleName"));
		return BASE_PATH + "role_authority.html";
	}
	@DoControllerLog(name="重新分配角色")
	@Json
	@Before(RoleValidator.class)
	@RequestMapping("/saveAuthority")
	@Permission({ADMINISTRATOR, ADMIN})
	public AjaxResult saveAuthority() {
		String ids = getParameter("ids");
		Integer roleId = getParameterToInt("roleId");
		String[] id = ids.split(",");
		if (id.length <= 1) {
			CacheKit.removeAll(SYS_CACHE);
			return success("设置成功");
		}
		boolean temp = service.grant(ids, roleId);
		if (temp) {
			CacheKit.removeAll(SYS_CACHE);
			return success("设置成功");
		} else {
			return error("设置失败");
		}
	}
	
	//@SystemControllerLog(description = "新增角色")
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission({ADMINISTRATOR, ADMIN})
	public AjaxResult save() {
		Role role = mapping(PREFIX, Role.class);
		role.setDeptid(9);
		boolean temp = service.save(role);
		if (temp) {
			CacheKit.removeAll(SYS_CACHE);
			return success(SAVE_SUCCESS_MSG);
		} else {
			return error(SAVE_FAIL_MSG);
		}
	}
	
	//@SystemControllerLog(description = "更新角色")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission({ADMINISTRATOR, ADMIN})
	public AjaxResult update() {
		Role role = mapping(PREFIX, Role.class);
		boolean temp = service.update(role);
		if (temp) {
			CacheKit.removeAll(SYS_CACHE);
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}
	
	@DoControllerLog(name="删除角色")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission({ADMINISTRATOR, ADMIN})
	public AjaxResult remove() {
		int cnt = service.deleteByIds(getParameter("ids"));
		if (cnt > 0) {
			CacheKit.removeAll(SYS_CACHE);
			return success(DEL_SUCCESS_MSG);
		} else {
			return error(DEL_FAIL_MSG);
		}
	}

	@Json
	@RequestMapping("/getPowerById")
	@Permission({ADMINISTRATOR, ADMIN})
	public AjaxResult getPowerById() {
		int cnt = service.getParentCnt(getParameterToInt("id"));
		if (cnt > 0) {
			return success("success");
		} else {
			return error("error");
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Json
	@RequestMapping("/getRoleList")
	//@Permission({ADMINISTRATOR, ADMIN})
	public AjaxResult getRoleList() {
		String roles = ShiroKit.getUser().getRoles() + "," + ShiroKit.getUser().getSubRoles();
		Map map = new HashMap();
		map.put("ids", Convert.toIntArray(roles));
		List<Map> list = commonService.getInfoList("role.all_list",map);
		return json(list);
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Json
	@RequestMapping("/menuTreeListByUserRoleId")
	public AjaxResult menuTreeListByUserRoleId() {
		Integer roleId = getParameterToInt("roleId");
		Map paras = new HashMap();
		if(!ShiroKit.getUser().getIsSysRole()) {
			paras.put("isSysRole", true);
		}
		paras.put("roleId", roleId);
		List<Map> infoList = commonService.getInfoList("role.authority_list", paras);
		
		return json(infoList);
	}
	
}
