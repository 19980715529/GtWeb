package com.smallchill.game.player.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.toolbox.kit.CharsetKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.core.toolbox.kit.URLKit;

@Controller
@RequestMapping("/player")
public class PlayerController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/gameplayer/";
	private static String CODE = "player";
	
	@Resource
	private HttpServletRequest request;
	@DoControllerLog(name="进入玩家搜索页面")
	@RequestMapping("/")
	public String index(@RequestParam(name="id", required=false) Integer id, @RequestParam(name="where", required=false) String where, ModelMap mm) {
		mm.put("code", CODE);
		mm.put("id", id);
		mm.put("where", where);
		return BASE_PATH + "player_search.html";
	}
	
	//	@SystemControllerLog(description = "搜索玩家列表")
	@Json
	@RequestMapping(KEY_LIST)
	public Object list() {
		Object gird = new Object();
		String parameter = request.getParameter("where");
		if(StrKit.isBlank(parameter)) {
			return gird;
		}
//		gird = paginateBySelf("player_search.list");
		gird = paginateBySelf("player_search.new_list1");
		return gird;
	}
	
	@RequestMapping("/relationindex/")
	public String relationindex(@RequestParam(name="id", required=false) Integer id, @RequestParam(name="where", required=false) String where, ModelMap mm) {
		mm.put("code", CODE);
		mm.put("id", id);
		String t = JSON.toJSONString(where);
		t = t.replaceAll("\"", "");
		String w = URLKit.encode(where, CharsetKit.UTF_8);
		mm.put("where", w);
		return BASE_PATH + "player_relation_search.html";
	}

	@Json
	@RequestMapping("/relationlist")
	public Object relationlist() {
		Object gird = new Object();
		String parameter = request.getParameter("where");
		if(StrKit.isBlank(parameter)) {
			return gird;
		}
		gird = paginateBySelf("player_search.new_list");
		return gird;
	}
	
	@RequestMapping(KEY_PLAYER_XLB_STATISTICS)
	@Permission({ ADMINISTRATOR, ADMIN })
	public String xlbstatistics(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "player_xiaonaba_statistics.html";
	}
}