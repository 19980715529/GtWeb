package com.smallchill.db.config.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;

@Controller
@RequestMapping("/game")
public class GameController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/db/game/";
	
	//@DoControllerLog(name="进入游戏配置列表")
	@RequestMapping("/")
	@Permission(ADMINISTRATOR)
	public String index(ModelMap mm) {
		return BASE_PATH + "game.html";
	}
}
