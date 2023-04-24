package com.smallchill.db.config.treasure.controller;

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
import com.smallchill.game.newmodel.treasuredb.Gamerevenue;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/gamerevenue")
public class GamerevenueController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/gamerevenue/";
	private static String CODE = "gamerevenue";
	private static String LIST_SOURCE = "db_gamerevenue.list";
	private static String PREFIX = "gamerevenue";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入游戏税收配置列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "gamerevenue.html";
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
		return BASE_PATH + "gamerevenue_add.html";
	}
	
	@DoControllerLog(name="新增游戏税收配置")
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		Gamerevenue boxItem = mapping(PREFIX, Gamerevenue.class);
		Gamerevenue gr = Blade.create(Gamerevenue.class).findFirstBy("gameid="+boxItem.getGameid(), null);
		if(gr!=null) {
			return error("改游戏对应配置已经存在.");
		}
		boolean temp = Blade.create(Gamerevenue.class).save(boxItem);
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
		map.put("gameid", id);
		Map gamerevenue = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("gamerevenue", gamerevenue);
		mm.put("code", CODE);
		return BASE_PATH + "gamerevenue_edit.html";
	}
	
	@DoControllerLog(name="修改游戏税收配置")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		Gamerevenue boxItem = mapping(PREFIX, Gamerevenue.class);
		
		boolean temp = Blade.create(Gamerevenue.class).updateBy("wRevenue="+boxItem.getWrevenue(), "gameid="+boxItem.getGameid(), null);
		if (temp) {
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}
	
	@DoControllerLog(name="删除游戏税收配置")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove() {
		String ids = getParameter("ids");
		int temp = Blade.create(Gamerevenue.class).deleteBy("gameid in ("+ids+")", null);
		if (temp>0) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
}