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
import com.smallchill.game.newmodel.gameuserdb.AaSystemInt;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/aasystemint")
public class AASystemIntController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/aasystemint/";
	private static String CODE = "aasystemint";
	private static String LIST_SOURCE = "db_aasystemint.list";
	private static String PREFIX = "aasystemint";

	@Autowired
	private CommonService commonService;

	@DoControllerLog(name = "进入话费剩余数量配置列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "aasystemint.html";
	}

	@Json
	@RequestMapping(KEY_LIST)
	@Permission(ADMINISTRATOR)
	public Object list() {
		Object gird = paginateBySelf(LIST_SOURCE);
		return gird;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(KEY_EDIT + "/{id}")
	@Permission(ADMINISTRATOR)
	public String edit(@PathVariable Integer id, ModelMap mm) {
		Map map = new HashMap();
		map.put("ID", id);
		Map aasystemint = commonService.getInfoByOne(LIST_SOURCE, map);
		mm.put("aasystemint", aasystemint);
		mm.put("code", CODE);
		return BASE_PATH + "aasystemint_edit.html";
	}

	@DoControllerLog(name = "修改话费剩余数量配置信息")
	@Json
	@RequestMapping(KEY_UPDATE)
	@Permission(ADMINISTRATOR)
	public AjaxResult update() {
		AaSystemInt boxItem = mapping(PREFIX, AaSystemInt.class);
		StringBuilder builder = new StringBuilder();
		// builder.append("[Desc]='"+boxItem.getDesc()+"'");
		builder.append("[Param1]=" + boxItem.getParam1());
		// builder.append(",[Param2]="+boxItem.getParam2());
		// builder.append(",[Param3]="+boxItem.getParam3());
		// builder.append(",[Param1Desc]='"+boxItem.getParam1desc()+"'");
		// builder.append(",[Param2Desc]='"+boxItem.getParam2desc()+"'");
		// builder.append(",[Param3Desc]='"+boxItem.getParam3desc()+"'");

		boolean temp = Blade.create(AaSystemInt.class).updateBy(
				builder.toString(), "ID=" + boxItem.getId(), null);
		if (temp) {
			return success(UPDATE_SUCCESS_MSG);
		} else {
			return error(UPDATE_FAIL_MSG);
		}
	}
}