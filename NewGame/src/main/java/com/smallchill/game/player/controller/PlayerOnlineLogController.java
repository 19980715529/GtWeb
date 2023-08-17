package com.smallchill.game.player.controller;

import java.util.Map;

import com.smallchill.common.vo.ShiroUser;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.support.Convert;
import com.smallchill.system.model.UserPack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.CharsetKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.core.toolbox.kit.URLKit;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/player")
public class PlayerOnlineLogController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/gameplayer/";
	private static String CODE = "player";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入在线玩家列表页面")
	@RequestMapping(KEY_PLAYER_ONLINE_LIST)
	public String onlinelist(ModelMap mm) {
		ShiroUser user = ShiroKit.getUser();
		Integer id =(Integer) user.getId();
		// 查询包id
		Blade blade = Blade.create(UserPack.class);
		UserPack pack = blade.findFirstBy("uid=#{uid}", CMap.init().set("uid", id));
		if (pack!=null){
			String clientType = pack.getClientType();
			Integer[] ids = Convert.toIntArray(clientType);
			mm.put("clientType", ids[0]);
		}else {
			mm.put("clientType", -9);
		}
		mm.put("code", CODE);
		return BASE_PATH + "player_online_list.html";
	}
	//	@SystemControllerLog(description = "在线玩家列表")
	@Json
	@RequestMapping("/online")
	public Object online() {
		Object gird = paginateBySelf("player_online_list.new_list");
		return gird;
	}
	
	@SuppressWarnings({ "rawtypes" })
	@Json
	@RequestMapping("/getOnlineTotal")
	public AjaxResult getOnlineTotal() {
		String parameter = getParameter("where");
		Map parseObject2 = null;
		if(StrKit.notBlank(parameter)) {
			if(parameter.contains("%")){
				parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
			}
			parseObject2 = JSON.parseObject(parameter, Map.class);
		}
		Map list = commonService.getInfoByOne("player_online_list.online_total", parseObject2);
		return json(list);
	}
}