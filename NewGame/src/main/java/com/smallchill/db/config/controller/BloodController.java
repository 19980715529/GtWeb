package com.smallchill.db.config.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.smallchill.db.config.model.Bloodpondconfig;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/blood")
public class BloodController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/blood/";
	private static String CODE = "blood";
	private static String LIST_SOURCE = "db_blood.list";
	private static String PREFIX = "blood";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入血池配置列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "blood.html";
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
		return BASE_PATH + "blood_add.html";
	}
	
	@DoControllerLog(name="新增血池")
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		Bloodpondconfig bloodpondconfig = mapping(PREFIX, Bloodpondconfig.class);
		bloodpondconfig.setCreatetime(new Date());
		boolean temp = Blade.create(Bloodpondconfig.class).save(bloodpondconfig);
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
		Map bloodpondconfig = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("blood", bloodpondconfig);
		mm.put("code", CODE);
		return BASE_PATH + "blood_edit.html";
	}
	
	@DoControllerLog(name="修改血池")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		Bloodpondconfig bloodpondconfig = mapping(PREFIX, Bloodpondconfig.class);
		boolean temp = Blade.create(Bloodpondconfig.class).update(bloodpondconfig);
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
		mm.put("blood", bloodpondconfig);
		mm.put("code", CODE);
		return BASE_PATH + "blood_view.html";
	}
	
	@DoControllerLog(name="删除血池")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove() {
		String ids = getParameter("ids");
		boolean temp = Blade.create(Bloodpondconfig.class).deleteByIds(ids) > 0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Json
	@RequestMapping("/getBloodPondConfig")
	@Permission(ADMINISTRATOR)
	public AjaxResult getBloodPondConfig() {
		List<Map> list = commonService.getInfoList(LIST_SOURCE,null);
		return json(list);
	}
}
