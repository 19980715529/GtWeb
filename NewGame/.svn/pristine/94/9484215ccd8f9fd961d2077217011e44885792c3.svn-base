package com.smallchill.db.config.gameuser.controller;

import java.util.HashMap;
import java.util.Map;
import com.smallchill.core.annotation.Before;
import com.smallchill.db.config.meta.intercept.SignEventValidator;
import com.smallchill.game.newmodel.gameuserdb.SignEvent;
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
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/signinconfig")
public class SigninConfigController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/signinconfig/";
	private static String CODE = "signinconfig";
	private static String LIST_SOURCE = "db_signinconfig.list";
	private static String PREFIX = "signinconfig";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入签到配置列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "signinconfig.html";
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
		return BASE_PATH + "signinconfig_add.html";
	}
	
	@DoControllerLog(name="新增签到配置信息")
	@Json
	@RequestMapping(KEY_SAVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult save() {
		SignEvent boxItem = mapping(PREFIX, SignEvent.class);
		boolean temp = Blade.create(SignEvent.class).save(boxItem);
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
		map.put("day", id);
		Map signinconfig = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("signinconfig", signinconfig);
		mm.put("code", CODE);
		return BASE_PATH + "signinconfig_edit.html";
	}
	
	@DoControllerLog(name="修改签到配置信息")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Before(SignEventValidator.class)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		SignEvent boxItem = mapping(PREFIX, SignEvent.class);
		boolean temp = Blade.create(SignEvent.class).updateBy("SignInDays=#{signInDays},RewardType=#{rewardType},Reward=#{reward},Reward1=#{reward1}", "id=#{id}",boxItem);
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
		mm.put("signinconfig", bloodpondconfig);
		mm.put("code", CODE);
		return BASE_PATH + "signinconfig_view.html";
	}
	
	@DoControllerLog(name="删除签到配置信息")
	@Json
	@RequestMapping(KEY_REMOVE)
	@Permission(ADMINISTRATOR)
	public AjaxResult remove() {
		String day = getParameter("ids");
		boolean temp = Blade.create(SignEvent.class).deleteByIds(day) > 0;
		if (temp) {
			return success("删除成功!");
		} else {
			return error("删除失败!");
		}
	}
}