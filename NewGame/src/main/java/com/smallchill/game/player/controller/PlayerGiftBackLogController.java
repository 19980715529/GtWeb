package com.smallchill.game.player.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;

@Controller
@RequestMapping("/player")
public class PlayerGiftBackLogController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/gameplayer/";
	private static String CODE = "player";
	@DoControllerLog(name="进入礼物找回记录列表页面")
	@RequestMapping(KEY_PLAYER_GIFTBACK_LOG)
	//@Permission({ ADMINISTRATOR, ADMIN })
	public String giftbacklog(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "player_giftback_log.html";
	}
	//@SystemControllerLog(description = "礼物找回记录列表")
	@Json
	@RequestMapping("/gblist")
	public Object gblist() {
		Object gird = paginateBySelf("player_giftback_log.list");
		return gird;
	}
}