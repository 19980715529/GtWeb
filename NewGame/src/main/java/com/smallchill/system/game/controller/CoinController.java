package com.smallchill.system.game.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstShiro;

@Controller
@RequestMapping("/coin")
public class CoinController extends BaseController implements ConstShiro{
	private static String BASE_PATH = "/system/gamesystem/coin/";
	private static String CODE = "coin";
	private static String LIST_SOURCE = "coin.list";
	@DoControllerLog(name="进入管理员添加金币记录列表页面")
	@RequestMapping("/")
	//@Permission({ ADMINISTRATOR, ADMIN,USER })
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "coin_log.html";
	}
	
	/**
	 * 分页aop
	 * 普通用法
	 */
	//	@SystemControllerLog(description = "获取添加金币日志列表")
	@Json
	@RequestMapping(KEY_LIST)
	//@Permission({ ADMINISTRATOR, ADMIN,USER })
	public Object list() {
		Object gird = paginate(LIST_SOURCE);
		return gird;
	}
}
