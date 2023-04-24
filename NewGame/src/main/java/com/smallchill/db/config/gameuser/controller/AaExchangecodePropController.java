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
import com.smallchill.game.newmodel.gameuserdb.AaExchangecodeProp;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/aaExchangecodeprop")
public class AaExchangecodePropController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/aaExchangecodeprop/";
	private static String CODE = "aaExchangecodeprop";
	private static String LIST_SOURCE = "db_aaExchangecodeprop.list";
	private static String PREFIX = "aaExchangecodeprop";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入兑换码金币配置列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "aaExchangecodeprop.html";
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
		return BASE_PATH + "aaExchangecodeprop_add.html";
	}
	
	@DoControllerLog(name="新增兑换码金币信息")
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		AaExchangecodeProp boxItem = mapping(PREFIX, AaExchangecodeProp.class);
		AaExchangecodeProp ap = Blade.create(AaExchangecodeProp.class).findFirstBy("Prop_Id="+boxItem.getPropId(), null);
		if(ap!=null) {
			return error("该类型配置已经存在,请前往更改.");
		}
		boolean temp = Blade.create(AaExchangecodeProp.class).save(boxItem);
		if (temp) {
			return success(SAVE_SUCCESS_MSG);
		} else {
			return error(SAVE_FAIL_MSG);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/edit/{id}")
	@Permission(ADMINISTRATOR)
	public String edit(@PathVariable Integer id,ModelMap mm) {
		Map map = new HashMap();
		map.put("Prop_Id", id);
		Map aaExchangecodeprop = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("aaExchangecodeprop", aaExchangecodeprop);
		mm.put("code", CODE);
		return BASE_PATH + "aaExchangecodeprop_edit.html";
	}
	
	@DoControllerLog(name="修改兑换码金币信息")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		AaExchangecodeProp boxItem = mapping(PREFIX, AaExchangecodeProp.class);
		boolean temp = Blade.create(AaExchangecodeProp.class).updateBy("Amount="+boxItem.getAmount(), "Prop_Id="+boxItem.getPropId(), null);
		if (temp) {
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}
	
	@DoControllerLog(name="删除兑换码金币信息")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove() {
		String Prop_Id = getParameter("Prop_Id");
		boolean temp = Blade.create(AaExchangecodeProp.class).deleteBy("Prop_Id="+Prop_Id, null) > 0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
}