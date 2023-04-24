package com.smallchill.db.config.gameuser.controller;

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
import com.smallchill.game.newmodel.gameuserdb.AaSystem;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/aasystem")
public class AaSystemController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/aasystem/";
	private static String CODE = "aasystem";
	private static String LIST_SOURCE = "db_aasystem.list";
	private static String PREFIX = "aasystem";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入基础系统配置列表页面")
	@RequestMapping("")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "aasystem.html";
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
		return BASE_PATH + "aasystem_add.html";
	}
	
	@DoControllerLog(name="新增系统信息")
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		AaSystem boxItem = mapping(PREFIX, AaSystem.class);
		boolean temp = Blade.create(AaSystem.class).save(boxItem);
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
		map.put("System_Id", id);
		Map aasystem = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("aasystem", aasystem);
		mm.put("code", CODE);
		return BASE_PATH + "aasystem_edit.html";
	}
	
	@DoControllerLog(name="修改系统信息")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		AaSystem boxItem = mapping(PREFIX, AaSystem.class);
		boolean temp = Blade.create(AaSystem.class).update(boxItem);
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
		map.put("System_Id", id);
		Map bloodpondconfig = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("aasystem", bloodpondconfig);
		mm.put("code", CODE);
		return BASE_PATH + "aasystem_view.html";
	}
	
	@DoControllerLog(name="删除系统信息")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove() {
		String ids = getParameter("ids");
		boolean temp = Blade.create(AaSystem.class).deleteByIds(ids) > 0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
}