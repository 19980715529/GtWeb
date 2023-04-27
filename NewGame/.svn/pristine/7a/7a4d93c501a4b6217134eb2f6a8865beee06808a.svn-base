package com.smallchill.db.config.gameuser.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.CollectionKit;
import com.smallchill.db.config.meta.intercept.PhoneLimitValidator;
import com.smallchill.game.newmodel.gameuserdb.LPhonelimit;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/lphonelimit")
public class LPhonelimitController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/lphonelimit/";
	private static String CODE = "lphonelimit";
	private static String LIST_SOURCE = "db_lphonelimit.list";
	private static String PREFIX = "lphonelimit";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入手机号控制名单列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "lphonelimit.html";
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
		return BASE_PATH + "lphonelimit_add.html";
	}
	
	@DoControllerLog(name="新增手机号控制名单")
	@Json
	@Before(PhoneLimitValidator.class)
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		LPhonelimit boxItem = mapping(PREFIX, LPhonelimit.class);
		boolean temp = Blade.create(LPhonelimit.class).save(boxItem);
		if (temp) {
			return success(SAVE_SUCCESS_MSG);
		} else {
			return error(SAVE_FAIL_MSG);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(KEY_EDIT + "/{id}")
	@Before(PhoneLimitValidator.class)
	@Permission(ADMINISTRATOR)
	public String edit(@PathVariable String id, ModelMap mm) {
		Map map = new HashMap();
		map.put("account", id);
		Map lphonelimit = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("lphonelimit", lphonelimit);
		mm.put("code", CODE);
		return BASE_PATH + "lphonelimit_edit.html";
	}
	
	@DoControllerLog(name="修改手机号控制名单")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		LPhonelimit boxItem = mapping(PREFIX, LPhonelimit.class);
		boolean temp = Blade.create(LPhonelimit.class).updateBy("[limitRate]="+boxItem.getLimitrate()+",[limitValue]='"+boxItem.getLimitvalue()+"'", "account='"+boxItem.getAccount()+"'", null);
		if (temp) {
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(KEY_VIEW + "/{id}")
	@Permission(ADMINISTRATOR)
	public String view(@PathVariable String id, ModelMap mm) {
		Map map = new HashMap();
		map.put("account", id);
		Map bloodpondconfig = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("lphonelimit", bloodpondconfig);
		mm.put("code", CODE);
		return BASE_PATH + "lphonelimit_view.html";
	}
	
	@DoControllerLog(name="删除手机号控制名单")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove() {
		String ids = getParameter("ids");
		String[] idArr = ids.split(",");
		for (int i = 0; i < idArr.length; i++) {
			idArr[i] = "'"+idArr[i]+"'";
		}
		ids = CollectionKit.join(idArr, ",");
		boolean temp = Blade.create(LPhonelimit.class).deleteBy("account in ("+ids+")", null) > 0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
}