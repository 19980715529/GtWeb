package com.smallchill.game.player.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/playerlog")
public class PlayerOperateLogController extends BaseController implements
		ConstShiro {
	private static String BASE_PATH = "/gameplayer/";
	private static String CODE = "playerlog";

	@Autowired
	private CommonService commonService;
	
	@Json
	@RequestMapping("/lockedllist")
	public Object lockedllist() {
		Object gird = paginateBySelf("record_player_operate.list");
		return gird;
	}
	// 锁定记录
	@RequestMapping("/locked")
	public String locked(@RequestParam(name="id", required=false) Integer id, ModelMap mm) {
		mm.put("code", CODE);
		mm.put("id", id);
		return BASE_PATH + "player_locked_log.html";
	}

	@Json
	@RequestMapping("/lockspeakllist")
	public Object lockspeakllist() {
		Object gird = paginateBySelf("record_player_operate.list");
		return gird;
	}
	// 禁言记录
	@RequestMapping("/lockspeak")
	public String lockspeak(@RequestParam(name="id", required=false) Integer id, ModelMap mm) {
		mm.put("code", CODE);
		mm.put("id", id);
		return BASE_PATH + "player_lockspeak_log.html";
	}
	
	// 所有操作日志
	@Json
	@RequestMapping("/aollist")
	public Object aollist() {
		Object gird = paginateBySelf("record_player_operate.new_all_list");
		return gird;
	}
}