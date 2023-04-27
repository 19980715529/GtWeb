package com.smallchill.game.player.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.smallchill.core.aop.SystemControllerLog;
import com.smallchill.core.toolbox.kit.HttpKit;
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
import com.smallchill.core.toolbox.ajax.AjaxResult;
import com.smallchill.core.toolbox.kit.CharsetKit;
import com.smallchill.core.toolbox.kit.StrKit;
import com.smallchill.core.toolbox.kit.URLKit;
import com.smallchill.game.service.CommonService;

@Controller
@RequestMapping("/player")
public class PlayerRechargeLogController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/gameplayer/";
	private static String CODE = "player";
	
	@Autowired
	private CommonService commonService;

	@DoControllerLog(name="进入充值记录列表页面")
	@RequestMapping(KEY_PLAYER_RECHARGE_LOG)
	public String rechargelog(@RequestParam(name="id", required=false) Integer id, ModelMap mm) {
		mm.put("code", CODE);
		mm.put("id", id);
		return BASE_PATH + "player_recharge_log.html";
	}
	@DoControllerLog(name="进入充值未完成记录列表页面")
	@RequestMapping("/rechargeUnstatistics")
	public String rechargeUnstatistics(@RequestParam(name="id", required=false) Integer id, ModelMap mm) {
		mm.put("code", CODE);
		mm.put("id", id);
		return BASE_PATH + "player_recharge_unstatistics.html";
	}
	
//	@SystemControllerLog(description = "充值未完成记录列表")
//	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Json
	@RequestMapping("unstatisticsList")
	public Object unstatisticsList() {
		Object gird = null;
		String parameter = HttpKit.getRequest().getParameter("where");
		if(StrKit.isBlank(parameter)) {
			return paginateBySelf("player_recharge_log1.recharge_unstatistics");
		}
		if(parameter.contains("%")){
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		// 解析查询条件
		LOGGER.error(parameter);
		Map paras = JSON.parseObject(parameter, Map.class);
		gird = commonService.getInfoList("player_recharge_log1.recharge_unstatistics",paras);
		return gird;
	}

	//	@SystemControllerLog(description = "充值记录列表")
	//@SuppressWarnings({ "rawtypes", "unchecked" })
	@Json
	@RequestMapping(KEY_RCHARGE_LOG)
	public Object rllist() {
//		Object gird = paginateBySelf("player_recharge_log1.new_recharge_log");
//		Map parseObject = JSON.parseObject(JSON.toJSONString(gird), Map.class);
//		Object usercount = commonService.getInfoByOne("player_recharge_log.recharge_user_count",null);
//		parseObject.put("userdata", usercount);
//		gird = JSON.toJSON(parseObject);
		Object gird = new Object();
		String parameter = HttpKit.getRequest().getParameter("where");
		if(StrKit.isBlank(parameter)) {
			return gird;
		}
		if(parameter.contains("%")){
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		// 解析查询条件
		gird = paginateBySelf("player_recharge_log1.new_recharge_log");
		return gird;
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Json
	@RequestMapping("/getTotalRecharge")
	public AjaxResult getTotalRecharge() {
		// 获取当天所有充值成功的人数和总收益
		Map currentrecharge = commonService.getInfoByOne("player_recharge_log1.current_recharge",null);
		// 获取当天新增添加的人数
		Map newrecharge = commonService.getInfoByOne("player_recharge_log1.new_recharge",null);
		// 当天兑换成功的金额和人数
		Map current_girds = commonService.getInfoByOne("exchange_records.current_exchange_success_list", null);
		BigDecimal recharge_money = new BigDecimal(currentrecharge.get("money").toString());
		BigDecimal exchange_money = new BigDecimal(current_girds.get("current_moneys").toString());
		BigDecimal difference = recharge_money.subtract(exchange_money);
		currentrecharge.put("difference",difference.setScale(2));
		// 获取所有充值成功的
		Map recharge = commonService.getInfoByOne("player_recharge_log1.all_recharge",null);
		// 获取所有新增充值
		Map recharges = commonService.getInfoByOne("player_recharge_log1.all_recharges",null);
		// 所有兑换成功的金额和人数
		Map girds = commonService.getInfoByOne("exchange_records.exchange_success_list", null);
		BigDecimal recharge_moneys = new BigDecimal(recharge.get("money").toString());
		BigDecimal exchange_moneys = new BigDecimal(girds.get("moneys").toString());
		BigDecimal differences = recharge_moneys.subtract(exchange_moneys);
		recharge.put("difference",differences.setScale(2));

		Map result = new HashMap();
		result.put("currentrecharge", currentrecharge);
		result.put("newrecharge", newrecharge);
		result.put("recharges",recharges);
		result.put("recharge",recharge);
		return json(result);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Json
	@RequestMapping("/getUnstatisticsTotalRecharge")
	public AjaxResult getUnstatisticsTotalRecharge(@RequestParam(name="where", required=false) String parameter) {
		Map recharge = commonService.getInfoByOne("player_recharge_log.recharge_unstatistics_total",null);
		Map result = new HashMap();
		result.put("recharge", recharge);
		return json(result);
	}
	@Json
	@RequestMapping("/rechargechannel/list")
	public AjaxResult getChannel(){
		Object channel = paginateBySelf("recharge_channel.find_all_parent");
		return json(channel);
	}
}