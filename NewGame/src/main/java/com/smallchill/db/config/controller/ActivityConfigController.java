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
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.game.model.ActivityConfig;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/activityconfig")
public class ActivityConfigController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/activityconfig/";
	private static String CODE = "activityconfig";
	private static String LIST_SOURCE = "db_activityconfig.list";
	private static String PREFIX = "activityconfig";
	
	@Autowired
	private CommonService commonService;

	@DoControllerLog(name="进入活动配置列表")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "activityconfig.html";
	}
	
	@Json
	@RequestMapping(KEY_LIST)
	@Permission(ADMINISTRATOR)
	public Object list() {
		Object gird = paginateBySelf(LIST_SOURCE);
		return gird;
	}
	@DoControllerLog(name="进入新增活动配置页面")
	@RequestMapping(KEY_ADD)
	@Permission(ADMINISTRATOR)
	public String add(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "activityconfig_add.html";
	}
	@DoControllerLog(name="新增活动配置")
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		ActivityConfig boxItem = mapping(PREFIX, ActivityConfig.class);
		boolean temp = Blade.create(ActivityConfig.class).save(boxItem);
		if (temp) {
			return success(SAVE_SUCCESS_MSG);
		} else {
			return error(SAVE_FAIL_MSG);
		}
	}
	@DoControllerLog(name="进入修改活动配置页面")
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(KEY_EDIT + "/{id}")
	@Permission(ADMINISTRATOR)
	public String edit(@PathVariable Integer id, ModelMap mm) {
		Map map = new HashMap();
		map.put("ID", id);
		Map activityconfig = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("activityconfig", activityconfig);
		mm.put("code", CODE);
		return BASE_PATH + "activityconfig_edit.html";
	}
	@DoControllerLog(name="修改活动配置")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		ActivityConfig boxItem = mapping(PREFIX, ActivityConfig.class);
		boolean temp = Blade.create(ActivityConfig.class).update(boxItem);
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
		mm.put("activityconfig", bloodpondconfig);
		mm.put("code", CODE);
		return BASE_PATH + "activityconfig_view.html";
	}
	@DoControllerLog(name="删除活动配置")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove() {
		String ids = getParameter("ids");
		boolean temp = Blade.create(ActivityConfig.class).deleteByIds(ids) > 0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
}