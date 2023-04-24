package com.smallchill.system.game.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;

@Controller
@RequestMapping("/coupon")
public class CouponController extends BaseController implements ConstShiro{
	private static String BASE_PATH = "/system/gamesystem/coupon/";
	private static String CODE = "coupon";
	private static String LIST_SOURCE = "coupon.list";
	@DoControllerLog(name="进入管理员添加奖券记录列表页面")
	@RequestMapping("/")
	//@Permission({ ADMINISTRATOR, ADMIN,USER })
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "coupon_log.html";
	}
	
	//	@SystemControllerLog(description = "获取添加奖券日志列表")
	@Json
	@RequestMapping(KEY_LIST)
	//@Permission({ ADMINISTRATOR, ADMIN,USER })
	public Object list() {
		Object gird = paginate(LIST_SOURCE);
		return gird;
	}
}
