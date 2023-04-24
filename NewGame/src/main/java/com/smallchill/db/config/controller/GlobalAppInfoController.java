package com.smallchill.db.config.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.db.config.meta.intercept.GlobalAppInfoValidator;
import com.smallchill.game.newmodel.gameuserdb.AaRecharge;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/globalappinfo")
public class GlobalAppInfoController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/globalappinfo/";
	private static String CODE = "globalappinfo";
	private static String LIST_SOURCE = "db_globalappinfo.new_list";
	private static String PREFIX = "globalappinfo";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入手机支付配置列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "globalappinfo.html";
	}
	
	@Json
	@RequestMapping(KEY_LIST)
	@Permission(ADMINISTRATOR)
	public Object list() {
		Object gird = paginate(LIST_SOURCE);
		return gird;
	}
	
	@RequestMapping(KEY_ADD)
	@Permission(ADMINISTRATOR)
	public String add(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "globalappinfo_add.html";
	}
	
	@DoControllerLog(name="新增手机支付配置")
	@Before(GlobalAppInfoValidator.class)
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		AaRecharge boxItem = mapping(PREFIX, AaRecharge.class);
		boolean temp = Blade.create(AaRecharge.class).save(boxItem);
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
		map.put("Buy_Id", id);
		Map globalappinfo = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("globalappinfo", globalappinfo);
		mm.put("code", CODE);
		return BASE_PATH + "globalappinfo_edit.html";
	}
	
	@DoControllerLog(name="修改手机支付配置")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		AaRecharge boxItem = mapping(PREFIX, AaRecharge.class);
		boolean temp = Blade.create(AaRecharge.class).update(boxItem);
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
		map.put("Buy_Id", id);
		Map bloodpondconfig = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("globalappinfo", bloodpondconfig);
		mm.put("code", CODE);
		return BASE_PATH + "globalappinfo_view.html";
	}
	
	@DoControllerLog(name="删除手机支付配置")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove() {
		String ids = getParameter("ids");
		boolean temp = Blade.create(AaRecharge.class).deleteByIds(ids) > 0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Json
	@RequestMapping("/getAaRecharge")
	@Permission(ADMINISTRATOR)
	public AjaxResult getPlatformInfo() {
		List<Map> list = commonService.getInfoList(LIST_SOURCE,null);
		return json(list);
	}
}