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
import com.smallchill.game.newmodel.gameuserdb.AaBoxitemsconfig;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/box")
public class TreasureBoxConfigController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/box/";
	private static String CODE = "box";
	private static String LIST_SOURCE = "db_box.new_list";
	private static String PREFIX = "box";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入宝箱配置列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "box.html";
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
		return BASE_PATH + "box_add.html";
	}
	
	@DoControllerLog(name="新增宝箱")
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		AaBoxitemsconfig boxItem = mapping(PREFIX, AaBoxitemsconfig.class);
		boolean temp = Blade.create(AaBoxitemsconfig.class).save(boxItem);
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
		map.put("boxtype", id);
		Map box = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("box", box);
		mm.put("code", CODE);
		return BASE_PATH + "box_edit.html";
	}
	
	@DoControllerLog(name="修改宝箱")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		AaBoxitemsconfig boxItem = mapping(PREFIX, AaBoxitemsconfig.class);
		boolean temp = Blade.create(AaBoxitemsconfig.class).updateBy("usemoney="+boxItem.getUsemoney()+",itemnum1="+boxItem.getItemnum1()+",itemnum2="+boxItem.getItemnum2(), "boxtype="+boxItem.getBoxtype(), null);
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
		mm.put("box", bloodpondconfig);
		mm.put("code", CODE);
		return BASE_PATH + "box_view.html";
	}
	
	@DoControllerLog(name="删除宝箱")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove() {
		String ids = getParameter("ids");
		boolean temp = Blade.create(AaBoxitemsconfig.class).deleteByIds(ids) > 0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
}