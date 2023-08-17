package com.smallchill.game.statistics.controller;

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
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.constant.ConstShiro;
import com.smallchill.core.toolbox.kit.CharsetKit;
import com.smallchill.core.toolbox.kit.URLKit;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/statistics")
public class PayActivityUserStatisticsController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/statistics/";
	private static String CODE = "statistics";
	
	@Autowired
	private CommonService commonService;

	@DoControllerLog(name="进入付费活跃用户统计页面")
	@RequestMapping("/payactivity/")
	//@Permission({ ADMINISTRATOR, ADMIN })
	public String index(ModelMap mm) {

		mm.put("code", CODE);
		return BASE_PATH + "pay_activity_user_statistics.html";
	}

	@RequestMapping("/pay0activity/")
	//@Permission({ ADMINISTRATOR, ADMIN })
	public String pay0activity(ModelMap mm) {
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
		return BASE_PATH + "pay_0activity_user_statistics.html";
	}
	
	@RequestMapping("/pay1activity/")
	//@Permission({ ADMINISTRATOR, ADMIN })
	public String pay1activity(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "pay_1activity_user_statistics.html";
	}
	@RequestMapping("/pay3activity/")
	//@Permission({ ADMINISTRATOR, ADMIN })
	public String pay3activity(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "pay_3activity_user_statistics.html";
	}
	@RequestMapping("/pay7activity/")
	//@Permission({ ADMINISTRATOR, ADMIN })
	public String pay7activity(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "pay_7activity_user_statistics.html";
	}
	@RequestMapping("/pay30activity/")
	//@Permission({ ADMINISTRATOR, ADMIN })
	public String pay30activity(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "pay_30activity_user_statistics.html";
	}
	@RequestMapping("/paynactivity/")
	//@Permission({ ADMINISTRATOR, ADMIN })
	public String paynactivity(ModelMap mm) {
		mm.put("code", CODE);
		return BASE_PATH + "pay_nactivity_user_statistics.html";
	}
	
	//	@SystemControllerLog(description = "付费活跃用户统计")
	@SuppressWarnings("rawtypes")
	@Json
	@RequestMapping("/raulist")
	public Object raulist() {
		String parameter = getParameter("where");
		String type = getParameter("type");
		if(parameter.contains("%")){
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		LOGGER.info("付费活跃统计->获取表格数据查询参数：-------------------");
		LOGGER.info(parameter);
		Map paras = JSON.parseObject(parameter, Map.class);
		Object gird = commonService.getInfoList("charts_pay_activity_user_statistics.new_list"+type, paras);
		return gird;
	}
}
