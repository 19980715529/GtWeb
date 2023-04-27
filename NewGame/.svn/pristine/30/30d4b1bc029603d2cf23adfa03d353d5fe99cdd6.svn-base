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
import com.smallchill.game.newmodel.gameuserdb.LVipconfig;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/lvipconfig")
public class LVipconfigController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/lvipconfig/";
	private static String CODE = "lvipconfig";
	private static String LIST_SOURCE = "db_lvipconfig.list";
	private static String PREFIX = "lvipconfig";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入VIP条件配置列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "lvipconfig.html";
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
		return BASE_PATH + "lvipconfig_add.html";
	}
	
	@DoControllerLog(name="新增VIP条件配置")
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		LVipconfig boxItem = mapping(PREFIX, LVipconfig.class);
		boxItem.setBuytimes(0);
		boolean temp = Blade.create(LVipconfig.class).save(boxItem);
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
		map.put("vipLevel", id);
		Map lvipconfig = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("lvipconfig", lvipconfig);
		mm.put("code", CODE);
		return BASE_PATH + "lvipconfig_edit.html";
	}
	
	@DoControllerLog(name="修改VIP条件配置")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		LVipconfig boxItem = mapping(PREFIX, LVipconfig.class);
		boolean temp = Blade.create(LVipconfig.class).updateBy("[buyOnceValue]="+boxItem.getBuyoncevalue(), "vipLevel="+boxItem.getViplevel(), null);
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
		map.put("vipLevel", id);
		Map bloodpondconfig = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("lvipconfig", bloodpondconfig);
		mm.put("code", CODE);
		return BASE_PATH + "lvipconfig_view.html";
	}
	
	@DoControllerLog(name="删除VIP条件配置")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove() {
		String ids = getParameter("ids");
		boolean temp = Blade.create(LVipconfig.class).deleteBy("vipLevel in ("+ids+")", null) > 0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
}