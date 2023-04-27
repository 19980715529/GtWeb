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
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.db.config.model.GameKindItem;
import com.smallchill.game.service.CommonService;
/**
 * 用户类型：普通用户，内部员工，至尊VIP等
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/gamekind")
public class GameKindItemController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/game/kind/";
	private static String CODE = "gamekind";
	private static String LIST_SOURCE = "db_gamekind.list";
	private static String PREFIX = "gamekind";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入游戏配置列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "game_kinditem.html";
	}
	

	@Json
	@RequestMapping(KEY_LIST)
	@Permission({ ADMINISTRATOR})
	public Object list() {
		Object gird = paginateBySelf(LIST_SOURCE);
		return gird;
	}
	
	@RequestMapping(KEY_ADD)
	@Permission({ ADMINISTRATOR})
	public String add(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "game_kinditem_add.html";
	}
	
	@DoControllerLog(name="新增游戏")
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission({ ADMINISTRATOR})
	public AjaxResult save() {
		GameKindItem boxItem = mapping(PREFIX, GameKindItem.class);
		boxItem.setTypeid(0);boxItem.setJoinid(0);
		boxItem.setSortid(6666);boxItem.setProcessname("13");
		boxItem.setMaxversion(65542);boxItem.setAndroidversion(0);
		boxItem.setIosversion(0);boxItem.setDatabasename("QPTreasureDB");
		boxItem.setNullity(0);boxItem.setAreaindex(0);
		boxItem.setRecommend(0);boxItem.setPartfromid(0);
		boxItem.setIsused(1);boxItem.setCheatepercent(10000);
		
		boolean temp = Blade.create(GameKindItem.class).save(boxItem);
		if (temp) {
			return success(SAVE_SUCCESS_MSG);
		} else {
			return error(SAVE_FAIL_MSG);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(KEY_EDIT + "/{id}")
	@Permission({ ADMINISTRATOR})
	public String edit(@PathVariable Integer id, ModelMap mm) {
		Map map = new HashMap();
		map.put("KindID", id);
		Map gamekind = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("gamekind", gamekind);
		mm.put("code", CODE);
		return BASE_PATH + "game_kinditem_edit.html";
	}
	
	@DoControllerLog(name="修改游戏")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission({ ADMINISTRATOR})
	public AjaxResult update() {
		GameKindItem boxItem = mapping(PREFIX, GameKindItem.class);
		boolean temp = Blade.create(GameKindItem.class).update(boxItem);
		if (temp) {
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(KEY_VIEW + "/{id}")
	@Permission({ ADMINISTRATOR})
	public String view(@PathVariable Integer id, ModelMap mm) {
		Map map = new HashMap();
		map.put("KindID", id);
		Map gamekind = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("gamekind", gamekind);
		mm.put("code", CODE);
		return BASE_PATH + "game_kinditem_view.html";
	}
	
	@DoControllerLog(name="删除游戏")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission({ ADMINISTRATOR})
	public AjaxResult remove() {
		String ids = getParameter("ids");
		boolean temp = Blade.create(GameKindItem.class).deleteByIds(ids) > 0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Json
	@RequestMapping("/getGameKindItem")
	public AjaxResult getGameKindItem() {
		List<Map> list = commonService.getInfoList(LIST_SOURCE,null);
		return json(list);
	}
}
