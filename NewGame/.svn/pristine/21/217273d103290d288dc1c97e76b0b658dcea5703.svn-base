package com.smallchill.db.config.treasure.controller;

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
import com.smallchill.game.newmodel.treasuredb.LBloodserverconnect;

@Controller
@RequestMapping("/lBloodserverconnect")
public class LBloodserverconnectController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/lBloodserverconnect/";
	private static String CODE = "lBloodserverconnect";
	private static String LIST_SOURCE = "db_lBloodserverconnect.list";
	private static String PREFIX = "lBloodserverconnect";
	
	@DoControllerLog(name="进入血池合并配置列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "lBloodserverconnect.html";
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
		return BASE_PATH + "lBloodserverconnect_add.html";
	}
	
	@DoControllerLog(name="新增血池合并配置")
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		LBloodserverconnect boxItem = mapping(PREFIX, LBloodserverconnect.class);
		LBloodserverconnect lBloodpoolconfig = Blade.create(LBloodserverconnect.class).findFirstBy("curServerID="+boxItem.getCurserverid(), null);
		if(lBloodpoolconfig != null) {
			return error("当前房间已配置过.请前往修改.");
		}
		boolean temp = Blade.create(LBloodserverconnect.class).save(boxItem);
		if (temp) {
			return success(SAVE_SUCCESS_MSG);
		} else {
			return error(SAVE_FAIL_MSG);
		}
	}
	
	@RequestMapping(KEY_EDIT + "/{id}")
	@Permission(ADMINISTRATOR)
	public String edit(@PathVariable Integer id, ModelMap mm) {
		LBloodserverconnect lBloodserverconnect = Blade.create(LBloodserverconnect.class).findById(id);
		mm.put("lBloodserverconnect", lBloodserverconnect);
		mm.put("code", CODE);
		return BASE_PATH + "lBloodserverconnect_edit.html";
	}
	
	@DoControllerLog(name="修改血池合并配置")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		LBloodserverconnect boxItem = mapping(PREFIX, LBloodserverconnect.class);
		
		boolean temp = Blade.create(LBloodserverconnect.class).update(boxItem);
		if (temp) {
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}
	
	@RequestMapping(KEY_VIEW + "/{id}")
	@Permission(ADMINISTRATOR)
	public String view(@PathVariable Integer id, ModelMap mm) {
		LBloodserverconnect lBloodserverconnect = Blade.create(LBloodserverconnect.class).findById(id);
		mm.put("lBloodserverconnect", lBloodserverconnect);
		mm.put("code", CODE);
		return BASE_PATH + "lBloodserverconnect_view.html";
	}
	
	@DoControllerLog(name="删除血池合并配置")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove() {
		String ids = getParameter("ids");
		int temp = Blade.create(LBloodserverconnect.class).deleteByIds(ids);
		if (temp>0) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
}
