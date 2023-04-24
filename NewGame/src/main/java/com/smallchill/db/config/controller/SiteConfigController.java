package com.smallchill.db.config.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.game.model.ConfigInfo;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/siteconfig")
public class SiteConfigController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/configinfo/";
	private static String CODE = "siteconfig";
	private static String PREFIX = "siteconfig";
	private static String LIST_SOURCE = "db_siteconfig.list";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入站点配置页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "platform.html";
	}
	@DoControllerLog(name="进入站点配置页面")
	@RequestMapping("/main")
	@Permission(ADMINISTRATOR)
	public String main(@RequestParam(name="id", required=false) Integer platformID, ModelMap mm) {
		mm.put("code", CODE);
		mm.put("PlatformID", platformID);
		return BASE_PATH + "siteconfig.html";
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
	public String add(@RequestParam(name="PlatformID", required=false) Integer platformID,ModelMap mm) {
		mm.put("code", CODE);
		mm.put("PlatformID", platformID);
		return BASE_PATH + "siteconfig_add.html";
	}
	
	@DoControllerLog(name="新增宝箱类型")
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		ConfigInfo boxItem = mapping(PREFIX, ConfigInfo.class);
		boolean temp = Blade.create(ConfigInfo.class).save(boxItem);
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
		map.put("ConfigID", id);
		Map siteconfig = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("siteconfig", siteconfig);
		mm.put("code", CODE);
		return BASE_PATH + "siteconfig_edit.html";
	}
	
	@DoControllerLog(name="修改宝箱类型")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		ConfigInfo boxItem = mapping(PREFIX, ConfigInfo.class);
		boolean temp = Blade.create(ConfigInfo.class).update(boxItem);
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
		map.put("ConfigID", id);
		Map bloodpondconfig = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("siteconfig", bloodpondconfig);
		mm.put("code", CODE);
		return BASE_PATH + "siteconfig_view.html";
	}
	
	@DoControllerLog(name="删除宝箱类型")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove() {
		String ids = getParameter("ids");
		boolean temp = Blade.create(ConfigInfo.class).deleteByIds(ids) > 0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
}