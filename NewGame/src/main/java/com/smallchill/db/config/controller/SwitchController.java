package com.smallchill.db.config.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.support.Convert;
import com.smallchill.game.model.PlatformActiveNullityConfig;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/switch")
public class SwitchController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/switch/";
	private static String CODE = "switch";
	private static String LIST_SOURCE = "db_switch.list";
	private static String PREFIX = "switchInfo";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入配置项开关配置列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "switch.html";
	}
	
	@Json
	@RequestMapping(KEY_LIST)
	@Permission(ADMINISTRATOR)
	public Object list() {
		Object gird = paginateBySelf(LIST_SOURCE);
		return gird;
	}
	
	@RequestMapping(KEY_ADD)
	@Permission(ADMINISTRATOR)
	public String add(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "switch_add.html";
	}
	
	@DoControllerLog(name="新增配置项")
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		PlatformActiveNullityConfig boxItem = mapping(PREFIX, PlatformActiveNullityConfig.class);
		boolean temp = Blade.create(PlatformActiveNullityConfig.class).save(boxItem);
		if (temp) {
			return success(SAVE_SUCCESS_MSG);
		} else {
			return error(SAVE_FAIL_MSG);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(KEY_EDIT + "/{id}")
	@Permission(ADMINISTRATOR)
	public String edit(@PathVariable Integer id, ModelMap mm) {
		Map map = new HashMap();
		map.put("ID", id);
		Map switchInfo = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("switchInfo", switchInfo);
		mm.put("code", CODE);
		return BASE_PATH + "switch_edit.html";
	}
	
	@DoControllerLog(name="更新配置项")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		PlatformActiveNullityConfig boxItem = mapping(PREFIX, PlatformActiveNullityConfig.class);
		boolean temp = Blade.create(PlatformActiveNullityConfig.class).update(boxItem);
		if (temp) {
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(KEY_VIEW + "/{id}")
	@Permission(ADMINISTRATOR)
	public String view(@PathVariable Integer id, ModelMap mm) {
		Map map = new HashMap();
		map.put("ID", id);
		Map bloodpondconfig = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("switchInfo", bloodpondconfig);
		mm.put("code", CODE);
		return BASE_PATH + "switch_view.html";
	}
	
	@DoControllerLog(name="删除配置项")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove() {
		String ids = getParameter("ids");
		boolean temp = Blade.create(PlatformActiveNullityConfig.class).deleteByIds(ids) > 0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
	// 禁用
	@DoControllerLog(name="禁用配置项")
		@Json
		@RequestMapping("/nullity")
		@Permission(ADMINISTRATOR)
		public AjaxResult nullity() {
			String ids = getParameter("ids");
			CMap updateMap = CMap.init().set("ids", Convert.toIntArray(ids));
			boolean temp = Blade.create(PlatformActiveNullityConfig.class).updateBy("Nullity = 1", "ID in (#{join(ids)})", updateMap);
			if (temp) {
				return success("禁用成功!");
			} else {
				return error("禁用失败!");
			}
		}

		// 启用
	@DoControllerLog(name="启用配置项")
		@Json
		@RequestMapping("/unNullity")
		@Permission(ADMINISTRATOR)
		public AjaxResult unNullity() {
			String ids = getParameter("ids");
			CMap updateMap = CMap.init().set("ids", Convert.toIntArray(ids));
			// 启用当前条
			boolean temp = Blade.create(PlatformActiveNullityConfig.class).updateBy("Nullity = 0", "ID in (#{join(ids)})", updateMap);
			if (temp) {
				return success("启用成功!");
			} else {
				return error("启用失败!");
			}
		}
}
