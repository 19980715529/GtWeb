package com.smallchill.game.statistics.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.smallchill.common.base.BaseController;
import com.smallchill.core.annotation.DoControllerLog;
import com.smallchill.core.annotation.Json;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.toolbox.kit.CharsetKit;
import com.smallchill.core.toolbox.kit.URLKit;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/statistics")
public class RoomStatisticsController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/statistics/";
	private static String CODE = "statistics";
	
	@Autowired
	private CommonService commonService;
	
	@DoControllerLog(name="进入房间统计页面")
	@RequestMapping("/room/")
	public String index(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "platform_room_detail_log.html";
	}
	
	@SuppressWarnings({ "rawtypes" })
	@Json
	@RequestMapping("/roomlist")
	public Object roomlist() {
		String parameter = getParameter("where");
		if(parameter.contains("%")){
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		LOGGER.info("放进统计->获取表格数据查询参数：-------------------");
		LOGGER.info(parameter);
		Map paras = JSON.parseObject(parameter, Map.class);
		Object gird = commonService.getInfoList("room_statistics.list", paras);
		return gird;
	}
}
