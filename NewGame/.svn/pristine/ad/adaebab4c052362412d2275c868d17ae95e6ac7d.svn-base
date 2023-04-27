package com.smallchill.db.config.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.utils.Version;
import com.smallchill.db.config.model.MobileKindItem;
import com.smallchill.db.config.model.request.MobileKindItemRequest;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/gamemobilekind")
public class MobileKindItemController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/game/mobile/";
	private static String CODE = "gamemobilekind";
	private static String LIST_SOURCE = "db_gamemobilekind.list";
	private static String PREFIX = "gamemobilekind";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入手游配置列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "game_mobilekinditem.html";
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
		return BASE_PATH + "game_mobilekinditem_add.html";
	}
	
	@SuppressWarnings("rawtypes")
	@DoControllerLog(name="新增手游")
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission({ ADMINISTRATOR})
	public AjaxResult save() {
		MobileKindItemRequest itemRequest = mapping(PREFIX, MobileKindItemRequest.class);
		MobileKindItem item = new MobileKindItem();
		
		Map infoByOne = commonService.getInfoByOne("db_gamemobilekind.id", null);
		Integer id = Integer.parseInt(JSON.toJSONString(infoByOne.get("KindID")));
		item.setKindid(id+1);
		item.setKindname(itemRequest.getKindname());
		item.setModulename(itemRequest.getModulename());
		item.setResversion(itemRequest.getResversion());
		item.setTypeid(itemRequest.getTypeid());
		item.setNullity(itemRequest.getNullity());
		item.setSortid(itemRequest.getSortid());
		int calVersion2 = Version.CalVersion2(itemRequest.getClientversion());
		item.setClientversion(calVersion2);
		int mark = 0;
		if(itemRequest.getKindmark() != null) {
			String[] marks = itemRequest.getKindmark().split(",");
			for (String kindmark : marks) {
				mark += Integer.parseInt(kindmark);
			}
			item.setKindmark(mark);
		} else {
			item.setKindmark(1);
		}
		
		boolean temp = Blade.create(MobileKindItem.class).save(item);
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
		Map gamemobilekind = commonService.getInfoByOne(LIST_SOURCE, map);
		gamemobilekind.put("ClientVersion", Version.CalVersion(Integer.parseInt(JSON.toJSONString(gamemobilekind.get("ClientVersion")))));
		mm.put("gamemobilekind", gamemobilekind);
		mm.put("code", CODE);
		return BASE_PATH + "game_mobilekinditem_edit.html";
	}
	
	@DoControllerLog(name="修改手游")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission({ ADMINISTRATOR})
	public AjaxResult update() {
		MobileKindItemRequest itemRequest = mapping(PREFIX, MobileKindItemRequest.class);
		MobileKindItem item = new MobileKindItem();
		item.setKindid(itemRequest.getKindid());
		item.setKindname(itemRequest.getKindname());
		item.setModulename(itemRequest.getModulename());
		item.setResversion(itemRequest.getResversion());
		item.setNullity(itemRequest.getNullity());
		item.setSortid(itemRequest.getSortid());
		int calVersion2 = Version.CalVersion2(itemRequest.getClientversion());
		item.setClientversion(calVersion2);
		int mark = 0;
		if(itemRequest.getKindmark() != null) {
			String[] marks = itemRequest.getKindmark().split(",");
			for (String kindmark : marks) {
				mark += Integer.parseInt(kindmark);
			}
			item.setKindmark(mark);
		}
		boolean temp = Blade.create(MobileKindItem.class).update(item);
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
		Map gamemobilekind = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("gamemobilekind", gamemobilekind);
		mm.put("code", CODE);
		return BASE_PATH + "game_mobilekinditem_view.html";
	}
	
	@DoControllerLog(name="删除手游")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission({ ADMINISTRATOR})
	public AjaxResult remove() {
		String ids = getParameter("ids");
		boolean temp = Blade.create(MobileKindItem.class).deleteByIds(ids) > 0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
}
