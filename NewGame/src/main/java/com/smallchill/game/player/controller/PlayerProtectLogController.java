package com.smallchill.game.player.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstShiro;

@Controller
@RequestMapping("/player")
public class PlayerProtectLogController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/gameplayer/";
	private static String CODE = "player";
	
	@DoControllerLog(name="进入玩家保护页面")
	@RequestMapping(KEY_PLAYER_PROTECT_LIST)
	public String protectlist(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "player_protect_list.html";
	}

	@DoControllerLog(name="进入玩家保护页面")
	@RequestMapping("/p/{id}")
	public String protectlist(@PathVariable Integer id,ModelMap mm) {
		mm.put("code", CODE);
		mm.put("type", id);
		return BASE_PATH + "player_protect_list_bak.html";
	}
	
	@Json
	@RequestMapping("/protectllist/{id}")
	public Object protectllist(@PathVariable Integer id) {
		Object gird = paginateBySelf("player_protect_log.new_list_"+id);
		return gird;
	}
}