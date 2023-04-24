package com.smallchill.game.player.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.smallchill.core.toolbox.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.toolbox.kit.CharsetKit;
import com.smallchill.core.toolbox.kit.FileKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.core.toolbox.kit.URLKit;
import com.smallchill.core.utils.ip.IPSeeker;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/player")
public class PlayerLoginLogController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/gameplayer/";
	private static String CODE = "player";
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private HttpServletRequest request;
	@DoControllerLog(name="进入用户登录记录列表页面")
	@RequestMapping(KEY_PLAYER_LOGIN_LOG)
	public String loginlog(@RequestParam(name="id", required=false) Integer id, ModelMap mm) {
		mm.put("code", CODE);
		mm.put("id", id);
		return BASE_PATH + "player_login_log.html";
	}
	//	@SystemControllerLog(description = "搜索登录记录列表")
	@Json
	@RequestMapping(KEY_LOGIN_LOG)
	public Object lllist() {
		Object gird = new Object();
		String parameter = request.getParameter("where");
		if(StrKit.isBlank(parameter)) {
			return gird;
		}
		gird = paginateBySelf("player_login_log.new_login_log");
		return gird;
	}
	//	@SystemControllerLog(description = "统计登录记录列表->登录IP统计")
	@SuppressWarnings({"rawtypes", "unchecked" })
	@Json
	@RequestMapping("/slllist")
	public Object slllist() {
		Object gird = new Object();
		String parameter = request.getParameter("where");
		if(StrKit.isBlank(parameter)) {
			return gird;
		}
		if(parameter.contains("%")){
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		Map parseObject2 = JSON.parseObject(parameter.replaceAll("'|\"", ""), Map.class);
		List<Map> infoList = commonService.getInfoList("player_login_log.new_login_ip_log", parseObject2);
    	File f = FileKit.file("qqwry.dat");
		System.out.println(f);
    	if(!Func.isEmpty(f)) {
			System.out.println(f.getName());
    		IPSeeker ip=new IPSeeker(f.getName(),f.getParent()); 
    		for (Map map : infoList) {
    			map.put("Location", ip.getIPLocation(JSON.toJSONString(map.get("EnterClientIP")).replaceAll("\"", "")).getCountry());
    		}
    		
    		ip = null;
    		f = null;
    	}
		gird = JSON.toJSON(infoList);
		return gird;
	}
}