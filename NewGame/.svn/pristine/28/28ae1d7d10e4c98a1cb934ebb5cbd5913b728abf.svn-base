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
import com.smallchill.game.model.ConfigInfo;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/configinfo")
public class ConfigInfoController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/configinfo/";
	private static String CODE = "configinfo";
	private static String PREFIX = "configinfo";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入站点配置页面")
	@RequestMapping("/{id}")
	@Permission(ADMINISTRATOR)
	public String index(@PathVariable String id,ModelMap mm) {
		mm.put("ConfigKey", id);
		mm.put("code", CODE);
		return BASE_PATH + "configinfo.html";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(KEY_EDIT + "/{id}")
	@Permission(ADMINISTRATOR)
	public String edit(@PathVariable String id, ModelMap mm) {
		Map paras = new HashMap();
		paras.put("ConfigKey", id);
		Map infoByOne = commonService.getInfoByOne("db_configinfo.list", paras);
		mm.put("ConfigKey", id);
		mm.put("code", CODE);
		if(infoByOne == null) {
			return BASE_PATH + "configinfo_add.html";
		} else {
			mm.put("configinfo", infoByOne);
			return BASE_PATH + "configinfo_edit.html";
		}
	}
	
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		ConfigInfo configInfo = mapping(PREFIX, ConfigInfo.class);
		configInfo.setSortid(0);
		boolean temp = Blade.create(ConfigInfo.class).save(configInfo);
		if (temp) {
			return success(SAVE_SUCCESS_MSG);
		} else {
			return error(SAVE_FAIL_MSG);
		}
	}
	
	@DoControllerLog(name="修改站点配置")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		ConfigInfo configInfo = mapping(PREFIX, ConfigInfo.class);
		boolean temp = Blade.create(ConfigInfo.class).update(configInfo);
		if (temp) {
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}
}