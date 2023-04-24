package com.smallchill.game.player.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstShiro;

@Controller
@RequestMapping("/player")
public class PlayerOnlineStatisController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/gameplayer/";
	private static String CODE = "player";
	
	@DoControllerLog(name="进入玩家在线时长统计列表页面")
	@RequestMapping("/OnlineStatis")
	public String onlinelist(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "player_online_list2.html";
	}
	//	@SystemControllerLog(description = "在线玩家列表")
	@Json
	@RequestMapping("/oslist")
	public Object online() {
		Object gird = paginateBySelf("player_online_list.online_list");
		return gird;
	}
}