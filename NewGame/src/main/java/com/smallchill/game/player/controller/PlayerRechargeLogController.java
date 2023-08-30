package com.smallchill.game.player.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;
import com.smallchill.common.task.GlobalDelayQueue;
import com.smallchill.common.vo.ShiroUser;
import com.smallchill.core.annotation.Before;
import com.smallchill.core.annotation.Permission;
import com.smallchill.core.aop.SystemControllerLog;
import com.smallchill.core.plugins.dao.Blade;
import com.smallchill.core.plugins.dao.Db;
import com.smallchill.core.shiro.ShiroKit;
import com.smallchill.core.toolbox.CMap;
import com.smallchill.core.toolbox.kit.HttpKit;
import com.smallchill.core.toolbox.support.Convert;
import com.smallchill.game.player.meta.intercept.OrderValidator;
import com.smallchill.system.model.UserPack;
import com.smallchill.system.service.RechargeRecordsService;
import com.smallchill.system.treasure.model.RechargeRecords;
import com.smallchill.system.treasure.utils.RechargeExchangeCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
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

import static com.smallchill.system.treasure.utils.CallBackUtils.successRecExecuted;

@Controller
@RequestMapping("/player")
public class PlayerRechargeLogController extends BaseController implements ConstShiro {
	private static String BASE_PATH = "/gameplayer/";
	private static String CODE = "player";

	
	@Autowired
	private CommonService commonService;

	@Autowired
	private RechargeRecordsService rechargeRecordsService;

	@DoControllerLog(name="进入充值记录列表页面")
	@RequestMapping(KEY_PLAYER_RECHARGE_LOG)
	public String rechargelog(@RequestParam(name="id", required=false) Integer id, ModelMap mm) {
		mm.put("code", CODE);
		ShiroUser user = ShiroKit.getUser();
		Integer uid =(Integer) user.getId();
		// 查询包id
		Blade blade = Blade.create(UserPack.class);
		UserPack pack = blade.findFirstBy("uid=#{uid}", CMap.init().set("uid", uid));
		if (pack!=null){
			String clientType = pack.getClientType();
			Integer[] ids = Convert.toIntArray(clientType);
			mm.put("clientType", ids[0]);
		}else {
			mm.put("clientType", -9);
		}
		mm.put("id", id);
		return BASE_PATH + "player_recharge_log.html";
	}
	@DoControllerLog(name="进入充值未完成记录列表页面")
	@RequestMapping("/rechargeUnstatistics")
	public String rechargeUnstatistics(@RequestParam(name="id", required=false) Integer id, ModelMap mm) {
		ShiroUser user = ShiroKit.getUser();
		Integer uid =(Integer) user.getId();
		// 查询包id
		Blade blade = Blade.create(UserPack.class);
		UserPack pack = blade.findFirstBy("uid=#{uid}", CMap.init().set("uid", uid));
		if (pack!=null){
			String clientType = pack.getClientType();
			Integer[] ids = Convert.toIntArray(clientType);
			mm.put("clientType", ids[0]);
		}else {
			mm.put("clientType", -9);
		}
		mm.put("code", CODE);
		mm.put("id", id);
		return BASE_PATH + "player_recharge_unstatistics.html";
	}
	
//	@SystemControllerLog(description = "充值未完成记录列表")
//	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Json
	@RequestMapping("unstatisticsList")
	public Object unstatisticsList() {
//		Object gird = null;
//		String parameter = HttpKit.getRequest().getParameter("where");
//		if(StrKit.isBlank(parameter)) {
//			return paginateBySelf("player_recharge_log1.recharge_unstatistics");
//		}
//		if(parameter.contains("%")){
//			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
//		}
//		// 解析查询条件
//		LOGGER.error(parameter);
//		Map paras = JSON.parseObject(parameter, Map.class);
		return paginateBySelf("player_recharge_log1.recharge_unstatistics");
	}

//		@SystemControllerLog(description = "充值记录列表")
//	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Json
	@RequestMapping(KEY_RCHARGE_LOG)
	public Object rllist() {
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
		Map<String, Object> stringObjectMap = RechargeExchangeCommon.OrderStatistics();
		return json(stringObjectMap);
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
//	@Json
//	@RequestMapping("/rechargechannel/list")
	public AjaxResult getChannel(){
		Object channel = paginateBySelf("recharge_channel.find_all_parent");
		return json(channel);
	}
	@Json
	@RequestMapping("/query/list")
	public AjaxResult query(){
		Object gird = new Object();
		String parameter = HttpKit.getRequest().getParameter("where");
		if(StrKit.isBlank(parameter)) {
			return json(gird);
		}
		if(parameter.contains("%")){
			parameter = URLKit.decode(parameter, CharsetKit.UTF_8);
		}
		// 根据条件=查询 总充值人数：, 新增充值人数：, 总充值额：, 新增充值总额：, 兑换总额：,    充提差：, ARPPU：;
		JSONObject jsonObject = JSON.parseObject(parameter);
		HashMap<String, Object> map = new HashMap<>();
		// 充值人数，充值金额
		Map o = commonService.getInfoByOne("player_recharge_log1.query_list",jsonObject);
		BigDecimal totalRecMoney = new BigDecimal(o.get("TotalRecMoney").toString());
		map.put("TotalRecNum",new BigDecimal(o.get("TotalRecNum").toString()));
		map.put("TotalRecMoney",totalRecMoney.longValue());
		BigDecimal totalRecUserNum = new BigDecimal(o.get("TotalRecUserNum").toString());
		map.put("TotalRecUserNum",totalRecUserNum.longValue());
		// arpu
		if (totalRecUserNum.longValue()!=0){
			map.put("ARPU",totalRecMoney.divide(totalRecUserNum,2,RoundingMode.UP));
		}else {
			map.put("ARPU",0);
		}
		// 新增充值人数，新增充值金额
		jsonObject.put("recType",1);
		// 获取满足条件的所有用户
		List<Map> userMaps = commonService.getInfoList("player_recharge_log1.query_new_user", jsonObject);
		ArrayList<Integer> users = new ArrayList<>();
		if (userMaps.size()>0){
			for (Map<String,Integer> m:userMaps){
				users.add(m.get("userId"));
			}
			jsonObject.put("users", users);
			Map a = commonService.getInfoByOne("player_recharge_log1.query_new_list",jsonObject);
			// 新增充值金额
			map.put("TotalNewRecMoney",a.get("TotalRecMoney"));
			// 新增充值人数
			map.put("TotalNewRecUserNum",a.get("TotalRecUserNum"));
		}else {
			// 新增充值金额
			map.put("TotalNewRecMoney",0);
			// 新增充值人数
			map.put("TotalNewRecUserNum",0);
		}
		// 兑换总额 recType
		Map e = commonService.getInfoByOne("player_recharge_log1.query_exc_list",jsonObject);
		BigDecimal totalExcMoney = new BigDecimal(e.get("TotalNewExcMoney").toString());
		map.put("TotalExcMoney",totalExcMoney.longValue());
		map.put("TotalExcUserNum",e.get("TotalNewExcUserNum").toString());
		map.put("RE",totalRecMoney.subtract(totalExcMoney));
		return json(map);
	}

	/**
	 * 手动设置订单完成
	 */
	@RequestMapping(KEY_EDIT+"/{id}")
	public String edit(@PathVariable Integer id,ModelMap mm){
		// 根据订单id查询订单号
		RechargeRecords records = rechargeRecordsService.findById(id);
		mm.put("player",records);
		mm.put("code",CODE);
		return BASE_PATH+"player_recharge_edit.html";
	}
	/**
	 * 手动设置订单完成
	 */
	@Json
	@Before(OrderValidator.class)
	@Permission({ADMINISTRATOR,ADMIN})
	@RequestMapping("pay/"+KEY_UPDATE)
	public AjaxResult update(){
		String id = getRequest().getParameter("player.id");
		String orderStatus = getRequest().getParameter("player.orderStatus");
		// 根据订单id查询订单号
		RechargeRecords recharge = rechargeRecordsService.findById(id);
		if (recharge.getOrderStatus()==2){
			return fail("订单已经完成不可以操作!");
		}
		successRecExecuted(recharge.getOrderNumber(),recharge);
		return json(UPDATE_SUCCESS_MSG);
	}
}