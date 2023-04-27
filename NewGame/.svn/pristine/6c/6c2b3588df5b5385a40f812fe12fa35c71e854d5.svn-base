package com.smallchill.game.player.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.toolbox.kit.StrKit;

@Controller
@RequestMapping("/player")
public class PlayerCouponLogController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/gameplayer/";
	private static String CODE = "player";
	
	@Autowired
	private HttpServletRequest request;
	@DoControllerLog(name="进入奖券变动记录列表页面")
	@RequestMapping(KEY_PLAYER_COUPONCHANGE_LOG)
	@Permission({ ADMINISTRATOR, ADMIN })
	public String licouponchangelogst(@RequestParam(name="id", required=false) Integer id,ModelMap mm) {
		mm.put("code", CODE);
		mm.put("id", id);
		return BASE_PATH + "player_couponchange_log.html";
	}
	
	//	@SystemControllerLog(description = "奖券变动记录列表")
	@Json
	@RequestMapping("/cellist")
	public Object cellist() {
		Object gird = new Object();
		String parameter = request.getParameter("where");
		if(StrKit.isBlank(parameter)) {
			return gird;
		}
		gird = paginateBySelf("player_coupon_log.new_coupon_log");
		return gird;
	}
}