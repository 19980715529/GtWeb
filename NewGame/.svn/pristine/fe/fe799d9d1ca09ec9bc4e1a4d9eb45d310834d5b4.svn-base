package com.smallchill.db.config.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/database")
public class DataBaseInfoController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/database/";
	private static String CODE = "database";
	private static String LIST_SOURCE = "db_database.list";
	private static String PREFIX = "database";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入数据库配置列表页面")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "database.html";
	}
	
	@SuppressWarnings("rawtypes")
	@Json
	@RequestMapping("/getDataBaseInfo")
	@Permission(ADMINISTRATOR)
	public AjaxResult getDataBaseInfo() {
		List<Map> list = commonService.getInfoList(LIST_SOURCE,null);
		return json(list);
	}
}
