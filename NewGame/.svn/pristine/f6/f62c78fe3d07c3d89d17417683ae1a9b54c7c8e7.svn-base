package com.smallchill.db.config.gameuser.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.game.newmodel.gameuserdb.AaNewplayerProp;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/newplayerprop")
public class AANewPlayerPropController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/newplayerprop/";
	private static String CODE = "newplayerprop";
	private static String LIST_SOURCE = "db_newplayerprop.list";
	private static String PREFIX = "newplayerprop";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入新手奖励配置列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "newplayerprop.html";
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
		return BASE_PATH + "newplayerprop_add.html";
	}
	
	@DoControllerLog(name="新增新手奖励信息")
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		AaNewplayerProp boxItem = mapping(PREFIX, AaNewplayerProp.class);
		boolean temp = Blade.create(AaNewplayerProp.class).save(boxItem);
		if (temp) {
			return success(SAVE_SUCCESS_MSG);
		} else {
			return error(SAVE_FAIL_MSG);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/edit")
	@Permission(ADMINISTRATOR)
	public String edit(@RequestParam(name="Prop_Id", required=false) Integer Prop_Id,@RequestParam(name="platID", required=false) Integer platID, ModelMap mm) {
		Map map = new HashMap();
		map.put("Prop_Id", Prop_Id);
		map.put("platID", platID);
		Map newplayerprop = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("newplayerprop", newplayerprop);
		mm.put("code", CODE);
		return BASE_PATH + "newplayerprop_edit.html";
	}
	
	@DoControllerLog(name="修改新手奖励信息")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		AaNewplayerProp boxItem = mapping(PREFIX, AaNewplayerProp.class);
		boolean temp = Blade.create(AaNewplayerProp.class).updateBy("Amount="+boxItem.getAmount(), "Prop_Id="+boxItem.getProp_Id()+" and platID="+boxItem.getPlatid(), null);
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
		map.put("clientType", id);
		Map bloodpondconfig = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("newplayerprop", bloodpondconfig);
		mm.put("code", CODE);
		return BASE_PATH + "newplayerprop_view.html";
	}
	
	@DoControllerLog(name="删除新手奖励信息")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove() {
		String Prop_Id = getParameter("Prop_Id");
		String platID = getParameter("platID");
		boolean temp = Blade.create(AaNewplayerProp.class).deleteBy("Prop_Id="+Prop_Id+" and platID="+platID, null) > 0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
}