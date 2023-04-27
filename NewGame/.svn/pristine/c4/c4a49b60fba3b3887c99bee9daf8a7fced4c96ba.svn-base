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
import com.smallchill.core.toolbox.kit.CollectionKit;
import com.smallchill.game.service.CommonService;
import com.smallchill.system.treasure.model.GlobalShareInfo;

@Controller
@RequestMapping("/globalshareinfo")
public class GlobalShareInfoConfigController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/globalshareinfo/";
	private static String CODE = "globalshareinfo";
	private static String LIST_SOURCE = "db_globalshareinfo.list";
	private static String PREFIX = "globalshareinfo";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入充值类型配置列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "globalshareinfo.html";
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
		return BASE_PATH + "globalshareinfo_add.html";
	}
	
	@DoControllerLog(name="新增充值类型")
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		GlobalShareInfo boxItem = mapping(PREFIX, GlobalShareInfo.class);
		boolean temp = Blade.create(GlobalShareInfo.class).save(boxItem);
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
		map.put("ShareID", id);
		Map globalshareinfo = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("globalshareinfo", globalshareinfo);
		mm.put("code", CODE);
		return BASE_PATH + "globalshareinfo_edit.html";
	}
	
	@DoControllerLog(name="修改充值类型")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		GlobalShareInfo boxItem = mapping(PREFIX, GlobalShareInfo.class);
		boolean temp = Blade.create(GlobalShareInfo.class).update(boxItem);
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
		map.put("ShareID", id);
		Map bloodpondconfig = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("globalshareinfo", bloodpondconfig);
		mm.put("code", CODE);
		return BASE_PATH + "globalshareinfo_view.html";
	}
	
	@DoControllerLog(name="删除充值类型")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove() {
		String ids = getParameter("ids");
		String[] split = ids.split(",");
		String[] strs ={"402","100"};
		for (String string : split) {
			if(CollectionKit.contains(strs, string)) {
				return error("<span class=\"text-red\">ID为" + string + "的充值类型为固定设定，不能删除!</span>");
			}
		}
		boolean temp = Blade.create(GlobalShareInfo.class).deleteByIds(ids) > 0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
}