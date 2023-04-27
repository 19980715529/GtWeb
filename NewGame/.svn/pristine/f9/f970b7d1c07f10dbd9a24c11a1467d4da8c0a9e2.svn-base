package com.smallchill.game.player.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
public class PlayerTradeController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/gameplayer/";
	private static String CODE = "player";
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private HttpServletRequest request;

	@DoControllerLog(name="进入当日交易列表页面")
	@RequestMapping(KEY_PLAYER_DAYTRADE_LIST)
	public String daytradelist(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "player_daytrade_list.html";
	}
	
	// 当日交易列表
	@Json
	@RequestMapping("/trade")
	public Object trade() {
		Object gird = paginateBySelf("player_trade.old_trade_list");
		return gird;
	}
	
	@SuppressWarnings({ "rawtypes" })
	@Json
	@RequestMapping("/getTradeTotal")
	public AjaxResult getTradeTotal() {
		String parameter = getParameter("where");
		Map parseObject2 = null;
		if(StrKit.notBlank(parameter)) {
			if(parameter.contains("%")){
				parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
			}
			parseObject2 = JSON.parseObject(parameter, Map.class);
		}
		Map list = commonService.getInfoByOne("player_trade.trade_total", parseObject2);
		return json(list);
	}
}