package com.smallchill.db.config.serverinfo.controller;

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
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.game.newmodel.serverinfodb.LFishscoreconfig;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/lfishscoreconfig")
public class LFishscoreconfigController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/lfishscoreconfig/";
	private static String CODE = "lfishscoreconfig";
	private static String LIST_SOURCE = "db_lfishscoreconfig.list";
	private static String PREFIX = "lfishscoreconfig";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入鱼分比例配置列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "lfishscoreconfig.html";
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
		return BASE_PATH + "lfishscoreconfig_add.html";
	}
	
	@DoControllerLog(name="新增鱼分比例配置")
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		LFishscoreconfig boxItem = mapping(PREFIX, LFishscoreconfig.class);
		int temp = Db.insert("insert into [QPServerInfoDB].[dbo].[L_FishScoreConfig] (serverID,fishScorePercent) values("+boxItem.getServerid()+","+boxItem.getFishscorepercent()+")", null);
		if (temp>0) {
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
		map.put("NewType", id);
		Map lfishscoreconfig = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("lfishscoreconfig", lfishscoreconfig);
		mm.put("code", CODE);
		return BASE_PATH + "lfishscoreconfig_edit.html";
	}
	
	@DoControllerLog(name="修改鱼分比例配置")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		LFishscoreconfig boxItem = mapping(PREFIX, LFishscoreconfig.class);
		StringBuilder builder = new StringBuilder();
		builder.append("fishScorePercent="+boxItem.getFishscorepercent());
		
		boolean temp = Blade.create(LFishscoreconfig.class).updateBy(builder.toString(), "serverID="+boxItem.getServerid(), null);
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
		map.put("NewType", id);
		Map bloodpondconfig = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("lfishscoreconfig", bloodpondconfig);
		mm.put("code", CODE);
		return BASE_PATH + "lfishscoreconfig_view.html";
	}
	
	@DoControllerLog(name="删除鱼分比例配置")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove() {
		String ids = getParameter("ids");
		boolean temp = Blade.create(LFishscoreconfig.class).deleteBy("serverID in ("+ids+")", null) > 0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
}