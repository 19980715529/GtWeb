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
import com.smallchill.game.newmodel.gameuserdb.AaPhonenumberProp;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/phonenumberprop")
public class AaPhonenumberPropController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/phonenumberprop/";
	private static String CODE = "phonenumberprop";
	private static String LIST_SOURCE = "db_phonenumberprop.list";
	private static String PREFIX = "phonenumberprop";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入绑定手机奖励配置列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "phonenumberprop.html";
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
		return BASE_PATH + "phonenumberprop_add.html";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@DoControllerLog(name="新增绑定手机奖励信息")
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		AaPhonenumberProp boxItem = mapping(PREFIX, AaPhonenumberProp.class);
		Map map = new HashMap();
		map.put("Prop_Id", boxItem.getProp_Id());
		Map phonenumberprop = commonService.getInfoByOne(LIST_SOURCE, map);
		if(phonenumberprop != null){
			return error("已存在该类型数据.");
		}
		boolean temp = Blade.create(AaPhonenumberProp.class).save(boxItem);
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
		map.put("Prop_Id", id);
		Map phonenumberprop = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("phonenumberprop", phonenumberprop);
		mm.put("code", CODE);
		return BASE_PATH + "phonenumberprop_edit.html";
	}
	
	@DoControllerLog(name="修改绑定手机奖励信息")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		AaPhonenumberProp boxItem = mapping(PREFIX, AaPhonenumberProp.class);
		boolean temp = Blade.create(AaPhonenumberProp.class).update(boxItem);
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
		map.put("Prop_Id", id);
		Map bloodpondconfig = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("phonenumberprop", bloodpondconfig);
		mm.put("code", CODE);
		return BASE_PATH + "phonenumberprop_view.html";
	}
	
	@DoControllerLog(name="删除绑定手机奖励信息")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove() {
		String ids = getParameter("ids");
		boolean temp = Blade.create(AaPhonenumberProp.class).deleteByIds(ids) > 0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
}